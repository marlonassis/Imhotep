package br.com.imhotep.linhaMecanica.migrador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

/**
 * Classe para migrar as devoluções da classe tb_dispensacao_simples para tb_devolucao_medicamento_item_movimento
 * @author marlonassis
 *
 */
public class MigradorDispensacaoDevolucao {

	public static void main(String[] args) {
		String sqlPes = "select b.dt_data_movimento, a.id_devolucao_medicamento_item, "
				+ "a.id_movimento_livro from tb_dispensacao_simples a "
				+ "inner join tb_movimento_livro b on a.id_movimento_livro = b.id_movimento_livro "
				+ "where a.id_devolucao_medicamento_item != 0 order by id_dispensacao_simples ";
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		String ipImhotepLinhaMecanica = Constantes.IP_IMHOTEP_LINHA_MECANICA;
		lm.setIp(ipImhotepLinhaMecanica);
		try {
			ResultSet rs = lm.consultar(sqlPes);
			while (rs.next()) {
				int idDMI = rs.getInt("id_devolucao_medicamento_item");
				int idML = rs.getInt("id_movimento_livro");
				java.util.Date d = rs.getTimestamp("dt_data_movimento");
				String insert = "insert into tb_devolucao_medicamento_item_movimento (id_devolucao_medicamento_item, "
						+ "id_movimento_livro, dt_data_insercao) values("+idDMI+", "+idML+", '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d)+"');";
				System.out.println(insert);
				if(!lm.executarCUD(insert)){
					System.out.println("ERRO!");
					System.exit(1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
