package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AtualizadorMovimento extends GerenciadorMecanico{
	
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public static void main(String[] args) {
		try {
			AtualizadorMovimento am = new AtualizadorMovimento();
			am.setNomeBanco(DB_BANCO_IMHOTEP);
			am.setIp("200.133.41.8");
			ResultSet rs = am.consultar("select id_estoque, cv_lote from tb_estoque order by id_estoque");
			while (rs.next()) { 
				int idEstoque = rs.getInt(1);
				String lote = rs.getString(2);
				ResultSet rs2 = am.consultar("select a.id_movimento_livro, b.tp_operacao, a.in_quantidade_movimentacao from tb_movimento_livro a "
						+ "inner join tb_tipo_movimento b on a.id_tipo_movimento = b.id_tipo_movimento "
						+ "where a.id_estoque = " + idEstoque + " order by a.dt_data_movimento");
				int valorAnterior = 0;
				while (rs2.next()) {
					String op = rs2.getString("tp_operacao");
					Integer qtdMov = rs2.getInt("in_quantidade_movimentacao");
					Integer id = rs2.getInt("id_movimento_livro");
					if(op.equalsIgnoreCase("E")){
						valorAnterior += qtdMov;
					}else{
						valorAnterior -= qtdMov;
					}
					String sqlUpd = "update tb_movimento_livro set in_quantidade_atual="+valorAnterior+" where id_movimento_livro = "+id;
					System.out.println(idEstoque + " - " + lote + " - " + id);
					if(!am.executarQuery(sqlUpd)){
						System.exit(1);
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
