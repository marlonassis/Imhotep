package br.com.imhotep.linhaMecanica.migrador;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class CamelCaseEspecialidade {

	public static void main(String[] args) {
		LinhaMecanica lm = new LinhaMecanica();
		lm.setIp("200.133.41.8");
		lm.setNomeBanco("db_imhotep");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1("select id_especialidade, cv_descricao from tb_especialidade order by id_especialidade"));
		try {
			while (rs.next()) {
				Integer idEspecialidade = rs.getInt("id_especialidade");
				String especialidade = rs.getString("cv_descricao").toLowerCase();
				String[] n = especialidade.split(" ");
				especialidade = "";
				for(String item : n){
					if(item.equals("em")||item.equals("e")||item.equals("da")||item.equals("de")||item.equals("do")||item.equals("dos")){
						especialidade = especialidade.concat(item);
					}else{
						especialidade = especialidade.concat(item.substring(0, 1).toUpperCase().concat(item.substring(1, item.length())));
					}
					especialidade = especialidade.concat(" ");
				}
				String sql = "update tb_especialidade set cv_descricao = '"+especialidade.trim()+"' where id_especialidade = "+idEspecialidade;
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
