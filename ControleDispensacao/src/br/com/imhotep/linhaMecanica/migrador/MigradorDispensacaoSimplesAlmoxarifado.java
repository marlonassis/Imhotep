package br.com.imhotep.linhaMecanica.migrador;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

/**
 * Classe para migrar as dispensacoes do almoxarifado da tabela tb_dispensacao_simples para tb_dispensacao_simples_almoxarifado
 * @author marlonassis
 *
 */
public class MigradorDispensacaoSimplesAlmoxarifado {

	public static void main(String[] args) {
		String sqlPes = "select id_unidade_dispensada, id_movimento_livro_almoxarifado, id_solicitacao_material_almoxarifado_unidade_item from "
				+ "tb_dispensacao_simples where id_solicitacao_material_almoxarifado_unidade_item != 0";
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		String ipImhotepLinhaMecanica = "200.133.41.8";// Constantes.IP_LOCAL;
		lm.setIp(ipImhotepLinhaMecanica);
		try {
			ResultSet rs = lm.consultar(sqlPes);
			while (rs.next()) {
				int idMLA = rs.getInt("id_movimento_livro_almoxarifado");
				int idSMAUI = rs.getInt("id_solicitacao_material_almoxarifado_unidade_item");
				int idUnidade = rs.getInt("id_unidade_dispensada");
				String insert = "insert into tb_dispensacao_simples_almoxarifado (id_unidade_dispensada, "
						+ "id_movimento_livro_almoxarifado, id_solicitacao_material_almoxarifado_unidade_item) "
						+ "values("+idUnidade+", "+idMLA+", "+idSMAUI+");";
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
