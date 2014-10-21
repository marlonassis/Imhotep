package br.com.imhotep.linhaMecanica.migrador;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class FinanceiroAlmoxarifadoTemp {
	
	private static RoundingMode ROUDING = RoundingMode.HALF_EVEN;
	private static int SCALE = 3;
	
	public static void main(String[] args) {
		try{
			System.out.println("start");
			LinhaMecanica lm = new LinhaMecanica();
			lm.setIp("200.133.41.8");
			lm.setNomeBanco("db_imhotep");
			String mesReferencia = "2014-01";
			lm.executarCUD("delete from tb_financeiro_mensal_grupo_almoxarifado where cv_mes_referencia = '"+mesReferencia+"'");
			
			
			String sql2 = "select distinct a.id_grupo_almoxarifado from tb_financeiro_mensal_almoxarifado a "+ 
							"where a.cv_mes_referencia = '"+mesReferencia+"' "+
							"order by a.id_grupo_almoxarifado";
		
			ResultSet rs2 = lm.consultar(sql2);
			while (rs2.next()) {
				BigDecimal valorTransportado = new BigDecimal(0);
				BigDecimal valorEntrada = new BigDecimal(0);
				BigDecimal valorSaida = new BigDecimal(0);
				BigDecimal valorFinal = new BigDecimal(0);
				int idGrupoAl = rs2.getInt("id_grupo_almoxarifado");
				String sql = "select a.id_grupo_almoxarifado, "+ 
							 "a.db_valor_final, " +
							 "a.db_valor_transportado, "+
							 "a.db_valor_entrada, "+
							 "a.db_valor_saida "+
							 "from tb_financeiro_mensal_almoxarifado a  "+
							 "where a.cv_mes_referencia = '" + mesReferencia + "' and a.id_grupo_almoxarifado = " + idGrupoAl +
							 " order by a.id_grupo_almoxarifado";
				
				ResultSet rs = lm.consultar(sql);
				while (rs.next()) {
					BigDecimal totalTransportado = new BigDecimal(rs.getDouble("db_valor_transportado")).setScale(SCALE, ROUDING);
					BigDecimal totalEntrada = new BigDecimal(rs.getDouble("db_valor_entrada")).setScale(SCALE, ROUDING);
					BigDecimal totalSaida = new BigDecimal(rs.getDouble("db_valor_saida")).setScale(SCALE, ROUDING);
					BigDecimal totalFinal = new BigDecimal(rs.getDouble("db_valor_final")).setScale(SCALE, ROUDING);
					
					valorEntrada = valorEntrada.setScale(SCALE, ROUDING).add(totalEntrada.setScale(SCALE, ROUDING)).setScale(SCALE, ROUDING);
					valorFinal = valorFinal.setScale(SCALE, ROUDING).add(totalFinal.setScale(SCALE, ROUDING)).setScale(SCALE, ROUDING);
					valorSaida = valorSaida.setScale(SCALE, ROUDING).add(totalSaida.setScale(SCALE, ROUDING)).setScale(SCALE, ROUDING);
					valorTransportado = valorTransportado.setScale(SCALE, ROUDING).add(totalTransportado.setScale(SCALE, ROUDING)).setScale(SCALE, ROUDING);
				}
				
				String insert = "INSERT INTO tb_financeiro_mensal_grupo_almoxarifado( "+
					          "  id_grupo_almoxarifado,  "+
					          "  db_saldo_transportado, db_valor_entrada, db_valor_saida, db_valor_final, "+ 
					          "  cv_mes_referencia) "+
							  "  VALUES ("+idGrupoAl+", "+
							  valorTransportado.setScale(SCALE, ROUDING) +", "+
							  valorEntrada.setScale(SCALE, ROUDING) +", "+
							  valorSaida.setScale(SCALE, ROUDING) +", "+
							  valorFinal.setScale(SCALE, ROUDING) +", '" + 
					          mesReferencia + "'); ";
				
				System.out.println(insert);
				lm.executarCUD(insert);
			}
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
