package br.com.imhotep.linhaMecanica.migrador;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class CamelCaseProfissional {

	public static void main(String[] args) {
		LinhaMecanica lm = new LinhaMecanica();
		lm.setIp("200.133.41.8");
		lm.setNomeBanco("db_imhotep");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1("select id_profissional, cv_nome from tb_profissional order by id_profissional"));
		try {
			while (rs.next()) {
				Integer idProfissional = rs.getInt("id_profissional");
				String nome = rs.getString("cv_nome").toLowerCase();
				String[] n = nome.split(" ");
				nome = "";
				for(String item : n){
					if(item.equals("da")||item.equals("de")||item.equals("do")||item.equals("dos")){
						nome = nome.concat(item);
					}else{
						nome = nome.concat(item.substring(0, 1).toUpperCase().concat(item.substring(1, item.length())));
					}
					nome = nome.concat(" ");
				}
				String sql = "update tb_profissional set cv_nome = '"+nome.trim()+"' where id_profissional = "+idProfissional;
				System.out.println(sql);
				if(!lm.executarCUD(sql)){
					System.exit(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
