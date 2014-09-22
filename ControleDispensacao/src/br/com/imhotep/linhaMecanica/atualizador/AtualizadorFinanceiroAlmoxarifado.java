package br.com.imhotep.linhaMecanica.atualizador;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.ResultSet;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class AtualizadorFinanceiroAlmoxarifado {
	
	private static RoundingMode rouding = RoundingMode.HALF_EVEN;
	private static int scale = 3;
	
	public static void main(String[] args) {
		System.out.println("start");
//		atualizarValorPrecoMedioNovembro();
//		atualizarValorDezembro();
//		atualizarValorJaneiroSemTresGrupos();
		atualizarValorJaneiroTransporteMedlynx(); 
		System.out.println("finish");
	}

	private static void atualizarValorJaneiroTransporteMedlynx() {
		String sql = "select a.id_material_almoxarifado, "
				+ "a.id_financeiro_mensal_almoxarifado, "
				+ "a.in_saldo_transportado, "
				+ "a.in_saldo_atual, "
				+ "a.in_total_saida, "
				+ "a.db_preco_medio_transportado, "
				+ "a.in_total_entrada, "
				+ "a.db_preco_medio_atual "
				+ "from tb_financeiro_mensal_almoxarifado a where a.id_grupo_almoxarifado in (21, 13, 15) and a.cv_mes_referencia = '2014-01' order by a.id_material_almoxarifado ";
		
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", "127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while (rs.next()) {
				BigDecimal id = new BigDecimal(rs.getInt("id_financeiro_mensal_almoxarifado"));
				BigDecimal saldoTransportado = new BigDecimal(rs.getInt("in_saldo_transportado"));
				BigDecimal totalEntrada = new BigDecimal(rs.getInt("in_total_entrada"));
				BigDecimal precoMedioTransportado = new BigDecimal(rs.getDouble("db_preco_medio_transportado"));
				BigDecimal precoMedioAtual = new BigDecimal(rs.getDouble("db_preco_medio_atual"));
//				BigDecimal saldoAtual = new BigDecimal(rs.getInt("in_saldo_atual"));
				BigDecimal totalSaida = new BigDecimal(rs.getInt("in_total_saida"));
				
				BigDecimal precoMedio = new BigDecimal(0);
				BigDecimal totalTransportado = precoMedioTransportado.setScale(scale, rouding).multiply(saldoTransportado.setScale(scale, rouding)).setScale(scale, rouding);
				BigDecimal valorEntrada = precoMedioAtual.setScale(scale, rouding).multiply(totalEntrada.setScale(scale, rouding)).setScale(scale, rouding);
				
				if(totalEntrada.add(saldoTransportado).intValue() != 0) {
					
//					System.out.print("("+totalTransportado.setScale(scale, rouding) + " + " 
//							+ saldoEntrada.setScale(scale, rouding) + ") / (" + 
//							saldoTransportado.setScale(scale, rouding) +" + "+
//							totalEntrada.setScale(scale, rouding) + ") = ");
					
					precoMedio = totalTransportado.setScale(scale, rouding).add(valorEntrada.setScale(scale, rouding)).setScale(scale, rouding)
							.divide(
									totalEntrada.setScale(scale, rouding).add(saldoTransportado.setScale(scale, rouding)),
									MathContext.DECIMAL32).setScale(scale, rouding);
					
//					System.out.println(precoMedio.setScale(scale, rouding));
				} 
				
				BigDecimal valorSaida = totalSaida.setScale(scale, rouding).multiply(precoMedio.setScale(scale, rouding));
				BigDecimal totalFinal = totalTransportado.setScale(scale, rouding).add(valorEntrada.setScale(scale, rouding)).setScale(scale, rouding).subtract(valorSaida.setScale(scale, rouding)).setScale(scale, rouding) ;//saldoAtual.setScale(scale, rouding).multiply(precoMedio.setScale(scale, rouding)).setScale(scale, rouding);
				
				String sql2 = "update tb_financeiro_mensal_almoxarifado set db_valor_final = "+totalFinal.setScale(scale, rouding)
								+", db_preco_medio = "+precoMedio.setScale(scale, rouding)
								+ ",db_valor_transportado = " + totalTransportado.setScale(scale, rouding)
								+ ",db_valor_entrada=" + valorEntrada.setScale(scale, rouding)
								+ ",db_valor_saida=" + valorSaida.setScale(scale, rouding)
								+" where id_financeiro_mensal_almoxarifado = "+id;
				System.out.println(sql2);
				lm.executarCUD(sql2);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void atualizarValorJaneiroSemTresGrupos() {
		String sql = "select a.id_material_almoxarifado, "
				+ "a.id_material_almoxarifado, "
				+ "a.id_financeiro_mensal_almoxarifado, "
				+ "a.in_saldo_transportado, "
				+ "a.in_saldo_atual, "
				+ "a.db_preco_medio_transportado, "
				+ "a.in_total_entrada, "
				+ "a.in_total_saida, "
				+ "a.db_preco_medio_atual, "
				+ "(select b.db_valor_final from tb_financeiro_mensal_almoxarifado b where b.cv_mes_referencia = '2013-12' "
				+ "and a.id_material_almoxarifado = b.id_material_almoxarifado) saldoFinalTransportado "
				+ "from tb_financeiro_mensal_almoxarifado a where a.id_grupo_almoxarifado not in (21, 13, 15) and a.cv_mes_referencia = '2014-01' order by a.id_material_almoxarifado";
		
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", "127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while (rs.next()) {
				BigDecimal id = new BigDecimal(rs.getInt("id_financeiro_mensal_almoxarifado"));
				BigDecimal saldoTransportado = new BigDecimal(rs.getInt("in_saldo_transportado"));
//				BigDecimal precoMedioTransportado = new BigDecimal(rs.getDouble("db_preco_medio_transportado"));
				BigDecimal totalEntrada = new BigDecimal(rs.getInt("in_total_entrada"));
				BigDecimal precoMedioAtual = new BigDecimal(rs.getDouble("db_preco_medio_atual"));
				BigDecimal totalSaida = new BigDecimal(rs.getInt("in_total_saida"));
//				BigDecimal saldoAtual = new BigDecimal(rs.getInt("in_saldo_atual"));
				
				BigDecimal precoMedio = new BigDecimal(0);
				BigDecimal totalTransportado = new BigDecimal(rs.getDouble("saldoFinalTransportado"));
				BigDecimal valorEntrada = totalEntrada.setScale(scale, rouding).multiply(precoMedioAtual.setScale(scale, rouding)).setScale(scale, rouding);
				
				if(totalEntrada.add(saldoTransportado).intValue() != 0){
					precoMedio = totalTransportado.setScale(scale, rouding).add(valorEntrada.setScale(scale, rouding)).
							divide(
									totalEntrada.setScale(scale, rouding).add(saldoTransportado.setScale(scale, rouding)), MathContext.DECIMAL32).setScale(scale, rouding);
				}
				
				BigDecimal valorSaida = totalSaida.setScale(scale, rouding).multiply(precoMedio.setScale(scale, rouding)).setScale(scale, rouding);
				BigDecimal totalFinal = totalTransportado.setScale(scale, rouding).add(valorEntrada.setScale(scale, rouding)).setScale(scale, rouding).subtract(valorSaida).setScale(scale, rouding) ;//precoMedio.setScale(scale, rouding).multiply(saldoAtual.setScale(scale, rouding)).setScale(scale, rouding);
				
				String sql2 = "update tb_financeiro_mensal_almoxarifado set db_valor_final = "+totalFinal.setScale(scale, rouding)
								+",db_preco_medio = "+precoMedio.setScale(scale, rouding)
								+ ",db_valor_transportado = " + totalTransportado.setScale(scale, rouding)
								+ ",db_valor_entrada=" + valorEntrada.setScale(scale, rouding)
								+ ",db_valor_saida=" + valorSaida.setScale(scale, rouding)
								+" where id_financeiro_mensal_almoxarifado = "+id;
				System.out.println(rs.getInt("id_material_almoxarifado")+" - " + sql2);
				lm.executarCUD(sql2);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void atualizarValorDezembro() {
		String sql = "select a.id_material_almoxarifado, "
				+ "a.id_material_almoxarifado, "
				+ "a.id_financeiro_mensal_almoxarifado, "
				+ "a.in_saldo_transportado, "
				+ "a.in_saldo_atual, "
				+ "a.db_preco_medio_transportado, "
				+ "a.in_total_entrada, "
				+ "a.in_total_saida, "
				+ "a.db_preco_medio_atual, "
				+ "(select b.db_valor_final from tb_financeiro_mensal_almoxarifado b where b.cv_mes_referencia = '2013-11' "
				+ "and a.id_material_almoxarifado = b.id_material_almoxarifado) saldoFinalTransportado "
				+ "from tb_financeiro_mensal_almoxarifado a where a.cv_mes_referencia = '2013-12' order by a.id_material_almoxarifado";
		
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", "127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while (rs.next()) {
				BigDecimal id = new BigDecimal(rs.getInt("id_financeiro_mensal_almoxarifado"));
				BigDecimal saldoTransportado = new BigDecimal(rs.getInt("in_saldo_transportado"));
//				BigDecimal precoMedioTransportado = new BigDecimal(rs.getDouble("db_preco_medio_transportado"));
				BigDecimal totalEntrada = new BigDecimal(rs.getInt("in_total_entrada"));
				BigDecimal precoMedioAtual = new BigDecimal(rs.getDouble("db_preco_medio_atual"));
				BigDecimal totalSaida = new BigDecimal(rs.getInt("in_total_saida"));
//				BigDecimal saldoAtual = new BigDecimal(rs.getInt("in_saldo_atual"));
				
				BigDecimal precoMedio = new BigDecimal(0);
				BigDecimal totalTransportado = new BigDecimal(rs.getDouble("saldoFinalTransportado"));
				BigDecimal valorEntrada = totalEntrada.setScale(scale, rouding).multiply(precoMedioAtual.setScale(scale, rouding)).setScale(scale, rouding);
				
				if(totalEntrada.add(saldoTransportado).intValue() != 0){
					precoMedio = totalTransportado.setScale(scale, rouding).add(valorEntrada.setScale(scale, rouding)).
							divide(
									totalEntrada.setScale(scale, rouding).add(saldoTransportado.setScale(scale, rouding)), MathContext.DECIMAL32).setScale(scale, rouding);
				}
				
				BigDecimal valorSaida = totalSaida.setScale(scale, rouding).multiply(precoMedio.setScale(scale, rouding)).setScale(scale, rouding);
				BigDecimal totalFinal = totalTransportado.setScale(scale, rouding).add(valorEntrada.setScale(scale, rouding)).setScale(scale, rouding).subtract(valorSaida).setScale(scale, rouding) ;//precoMedio.setScale(scale, rouding).multiply(saldoAtual.setScale(scale, rouding)).setScale(scale, rouding);
				
				String sql2 = "update tb_financeiro_mensal_almoxarifado set db_valor_final = "+totalFinal.setScale(scale, rouding)
								+",db_preco_medio = "+precoMedio.setScale(scale, rouding)
								+ ",db_valor_transportado = " + totalTransportado.setScale(scale, rouding)
								+ ",db_valor_entrada=" + valorEntrada.setScale(scale, rouding)
								+ ",db_valor_saida=" + valorSaida.setScale(scale, rouding)
								+" where id_financeiro_mensal_almoxarifado = "+id;
				System.out.println(rs.getInt("id_material_almoxarifado")+" - " + sql2);
				lm.executarCUD(sql2);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void atualizarValorPrecoMedioNovembro() {
		String sql = "select a.id_material_almoxarifado, "
				+ "a.id_financeiro_mensal_almoxarifado, "
				+ "a.in_saldo_transportado, "
				+ "a.in_saldo_atual, "
				+ "a.in_total_saida, "
				+ "a.db_preco_medio_transportado, "
				+ "a.in_total_entrada, "
				+ "a.db_preco_medio_atual "
				+ "from tb_financeiro_mensal_almoxarifado a where a.cv_mes_referencia = '2013-11' order by a.id_material_almoxarifado ";
		
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", "127.0.0.1");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while (rs.next()) {
				BigDecimal id = new BigDecimal(rs.getInt("id_financeiro_mensal_almoxarifado"));
				BigDecimal saldoTransportado = new BigDecimal(rs.getInt("in_saldo_transportado"));
				BigDecimal totalEntrada = new BigDecimal(rs.getInt("in_total_entrada"));
				BigDecimal precoMedioTransportado = new BigDecimal(rs.getDouble("db_preco_medio_transportado"));
				BigDecimal precoMedioAtual = new BigDecimal(rs.getDouble("db_preco_medio_atual"));
//				BigDecimal saldoAtual = new BigDecimal(rs.getInt("in_saldo_atual"));
				BigDecimal totalSaida = new BigDecimal(rs.getInt("in_total_saida"));
				
				BigDecimal precoMedio = new BigDecimal(0);
				BigDecimal totalTransportado = precoMedioTransportado.setScale(scale, rouding).multiply(saldoTransportado.setScale(scale, rouding)).setScale(scale, rouding);
				BigDecimal valorEntrada = precoMedioAtual.setScale(scale, rouding).multiply(totalEntrada.setScale(scale, rouding)).setScale(scale, rouding);
				
				if(totalEntrada.add(saldoTransportado).intValue() != 0) {
					
//					System.out.print("("+totalTransportado.setScale(scale, rouding) + " + " 
//							+ saldoEntrada.setScale(scale, rouding) + ") / (" + 
//							saldoTransportado.setScale(scale, rouding) +" + "+
//							totalEntrada.setScale(scale, rouding) + ") = ");
					
					precoMedio = totalTransportado.setScale(scale, rouding).add(valorEntrada.setScale(scale, rouding)).setScale(scale, rouding)
							.divide(
									totalEntrada.setScale(scale, rouding).add(saldoTransportado.setScale(scale, rouding)),
									MathContext.DECIMAL32).setScale(scale, rouding);
					
//					System.out.println(precoMedio.setScale(scale, rouding));
				} 
				
				BigDecimal valorSaida = totalSaida.setScale(scale, rouding).multiply(precoMedio.setScale(scale, rouding));
				BigDecimal totalFinal = totalTransportado.setScale(scale, rouding).add(valorEntrada.setScale(scale, rouding)).setScale(scale, rouding).subtract(valorSaida.setScale(scale, rouding)).setScale(scale, rouding) ;//saldoAtual.setScale(scale, rouding).multiply(precoMedio.setScale(scale, rouding)).setScale(scale, rouding);
				
				String sql2 = "update tb_financeiro_mensal_almoxarifado set db_valor_final = "+totalFinal.setScale(scale, rouding)
								+", db_preco_medio = "+precoMedio.setScale(scale, rouding)
								+ ",db_valor_transportado = " + totalTransportado.setScale(scale, rouding)
								+ ",db_valor_entrada=" + valorEntrada.setScale(scale, rouding)
								+ ",db_valor_saida=" + valorSaida.setScale(scale, rouding)
								+" where id_financeiro_mensal_almoxarifado = "+id;
				System.out.println(sql2);
				lm.executarCUD(sql2);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
