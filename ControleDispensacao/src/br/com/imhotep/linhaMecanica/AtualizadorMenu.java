package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AtualizadorMenu extends GerenciadorMecanico{
	
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public static void main(String[] args) {
		try {
			AtualizadorMenu am = new AtualizadorMenu();
			am.setNomeBanco(DB_BANCO_IMHOTEP);
			am.setIp("127.0.0.1");
			ResultSet rs = am.consultar("select * from tb_menu order by id_menu");
			while (rs.next()) { 
				int idMenuPai = rs.getInt("id_menu_pai");
				String sqlUpd = "update tb_menu set "+
								  "id_menu_pai =  "+ (idMenuPai == 0 ? null : idMenuPai) +
								  ", cv_descricao = "+ adicionaAspas(rs.getString("cv_descricao")) +
								  ", cv_url = "+ adicionaAspas(rs.getString("cv_url")) +
								  ", cv_url_ajuda = "+ adicionaAspas(rs.getString("cv_url_ajuda")) +
								  ", bl_bloqueado = "+ rs.getBoolean("bl_bloqueado") +
								  ", bl_interno = "+ rs.getBoolean("bl_interno") +
								  ", bl_construcao = " + rs.getBoolean("bl_construcao") +
								  " where id_menu = " + rs.getInt("id_menu");
				System.out.println(sqlUpd);
				am.setIp("200.133.41.8");
				if(!am.executarQuery(sqlUpd)){
					System.out.println("erro");
					System.exit(1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
