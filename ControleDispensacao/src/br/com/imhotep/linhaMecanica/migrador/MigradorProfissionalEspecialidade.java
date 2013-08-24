package br.com.imhotep.linhaMecanica.migrador;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class MigradorProfissionalEspecialidade {

	public static void main(String[] args) {
		LinhaMecanica lm = new LinhaMecanica();
		lm.setIp("200.133.41.8");
		lm.setNomeBanco("db_imhotep");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1("select id_profissional, id_especialidade from tb_profissional where id_especialidade != 1 order by id_profissional"));
		try {
			while (rs.next()) {
				Integer idProfissional = rs.getInt("id_profissional");
				Integer idEspecialidade = rs.getInt("id_especialidade");
				String sql = "insert into tb_profissional_especialidade (id_profissional, id_especialidade) values ("+idProfissional+","+idEspecialidade+");";
				System.out.println(sql);
				lm.executarCUD(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
