package br.com.imhotep.linhaMecanica.atualizador;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class AtualizadorDataValidadeEstoques {
	
	
	
	public static void main(String[] args) {
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco("db_imhotep");
		lm.setIp("200.133.41.8");
		atualizarDataEstoqueFarmacia(lm);
		atualizarDataEstoqueAlmoxarifado(lm);
	}
	
	private static void atualizarDataEstoqueAlmoxarifado(LinhaMecanica lm){
		ResultSet rs = lm.consultar(lm.utf8_to_latin1("select id_estoque_almoxarifado, dt_data_validade from "
				+ "tb_estoque_almoxarifado where dt_data_validade is not null order by id_estoque_almoxarifado"));
		try {
			while (rs.next()) {
				
				//add per’odo
				int id = rs.getInt("id_estoque_almoxarifado");
				Date dataValidade = rs.getDate("dt_data_validade");
				
				if(dataValidade == null)
					continue;
				
				String sqlUpdate = "update tb_estoque_almoxarifado set "+
						"dt_data_validade = '"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Utilitarios().ajustarUltimaHoraDia(dataValidade)) + "'"+
						" where id_estoque_almoxarifado = "+id;
				System.out.println(sqlUpdate);
				if(!lm.executarCUD(sqlUpdate)){
					System.out.println("erro!!!");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void atualizarDataEstoqueFarmacia(LinhaMecanica lm) {
		ResultSet rs = lm.consultar(lm.utf8_to_latin1("select id_estoque, dt_data_validade from tb_estoque order by id_estoque"));
		try {
			while (rs.next()) {
				
				//add per’odo
				int id = rs.getInt("id_estoque");
				Date dataValidade = rs.getDate("dt_data_validade");
				
				dataValidade = new Utilitarios().ajustarUltimaHoraDia(dataValidade);
				dataValidade = new Utilitarios().ajustarUltimoDiaMes(dataValidade);
				
				String sqlUpdate = "update tb_estoque set "+
						"dt_data_validade = '"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dataValidade) +
						"' where id_estoque = "+id;
				System.out.println(sqlUpdate);
				if(!lm.executarCUD(sqlUpdate)){
					System.out.println("erro!!!");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
