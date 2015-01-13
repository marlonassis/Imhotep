package br.com.imhotep.linhaMecanica.migrador;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.linhaMecanica.atualizador.AtualizadorEstoqueAlmoxarifado;
import br.com.imhotep.linhaMecanica.atualizador.AtualizadorMovimentoAlmoxarifado;

public class MigradorEstornoMovimentoAlmoxarifado {
	private static final String IP = Constantes.IP_LOCAL;
	private static final String BANCO = "db_imhotep";
	
	public static void main(String[] args) {
		try {
			String sqlLocal = "select dt_data_estorno, id_profissional_estorno, "
					+ "cv_justificativa, cv_movimento_completo, "
					+ "in_quantidade_estornada, id_estoque_almoxarifado, id_tipo_movimento_almoxarifado "
					+ "from tb_estorno_movimento_almoxarifado where id_estorno_movimento_almoxarifado > 3";
			
			LinhaMecanica lm = new LinhaMecanica(BANCO, IP);
			List<Object[]> listaResultado = lm.getListaResultado(sqlLocal);
			for(Object[] o : listaResultado){
					String dataEstorno = new SimpleDateFormat().format(o[0]);
					Integer idProfissionalEstorno = Integer.valueOf(String.valueOf(o[1]));
					String justificativa = String.valueOf(o[2]);
					String movimentoCompleto = String.valueOf(o[3]);
					Integer quantidadeEstornada = Integer.valueOf(String.valueOf(o[4]));
					Integer idEstoqueAlmoxarifado = Integer.valueOf(String.valueOf(o[5]));
					Integer idTipoMovimento = Integer.valueOf(String.valueOf(o[6]));
					
					String idMovimento = movimentoCompleto.split(";")[0];
					String sqlDeleteMovimento = "delete from tb_movimento_livro_almoxarifado where id_movimento_livro_almoxarifado = "+idMovimento;
					System.out.println(sqlDeleteMovimento);
					lm.setNomeBanco("db_imhotep");
					lm.setIp("200.133.41.8");
					if(lm.executarCUD(sqlDeleteMovimento)){
						new AtualizadorEstoqueAlmoxarifado().atualizarEstoque(lm, idEstoqueAlmoxarifado);
						new AtualizadorMovimentoAlmoxarifado().atualizarMovimentos(idEstoqueAlmoxarifado, lm);
						String sqlInsert = "INSERT INTO tb_estorno_movimento_almoxarifado("
					            +" dt_data_estorno, id_profissional_estorno,"
					            +" cv_justificativa, cv_movimento_completo, in_quantidade_estornada," 
					            +" id_estoque_almoxarifado, id_tipo_movimento_almoxarifado)"
					            +" VALUES ('"+dataEstorno+"', "+idProfissionalEstorno+", '"+justificativa+"',"
					            +" '"+movimentoCompleto+"', "+quantidadeEstornada+", "+idEstoqueAlmoxarifado+","
					            +" "+idTipoMovimento+");";
						lm.executarCUD(sqlInsert);
						System.out.println(sqlInsert);
					}else{
						System.out.println("erro!!!");
					}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
