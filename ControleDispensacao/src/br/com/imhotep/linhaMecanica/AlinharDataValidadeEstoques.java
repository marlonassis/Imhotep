package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.imhotep.auxiliar.Utilitarios;

public class AlinharDataValidadeEstoques {

	private static final String IP = "200.133.41.8";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp(IP);
		ResultSet rs = lm.consultar(lm.utf8_to_latin1("select id_estoque, dt_data_validade from tb_estoque order by id_estoque"));
		try {
			while (rs.next()) {
				
				int id = rs.getInt("id_estoque");
				Date dataValidade = rs.getDate("dt_data_validade");
				
				String sqlUpdate = "update tb_estoque set "+
						"dt_data_validade = '"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Utilitarios().ajustarUltimoDiaMesHoraMaximo(dataValidade)) + "'"+
						" where id_estoque = "+id;
				System.out.println(sqlUpdate);
				if(!lm.executarCUD(sqlUpdate)){
					System.out.println("erro!!!");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		rs = lm.consultar(lm.utf8_to_latin1("select id_estoque_almoxarifado, dt_data_validade from tb_estoque_almoxarifado order by id_estoque_almoxarifado"));
		try {
			while (rs.next()) {
				
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

}
