package br.com.imhotep.linhaMecanica.gerador;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class GeradorFinanceiroAlmoxarifado {
	
	private static final String IP = "200.133.41.8";
	private static final String BANCO = "db_imhotep";
	private static RoundingMode ROUDING = RoundingMode.HALF_EVEN;
	private static int SCALE = 3;
	
	public static void main(String[] args) {
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(BANCO);
		lm.setIp(IP);
		
		String mesRelatorio = getMesRelatorio();
		String mesRelatorioTransporte = getMesTransporteRelatorio();
		
		apagarResquicios(mesRelatorio, lm);
		
		gerarRelatorioMesDetalhado(mesRelatorio, mesRelatorioTransporte, lm);
		gerarFinanceiroMesGrupo(mesRelatorio, lm);
	}

	private static void apagarResquicios(String mesRelatorio, LinhaMecanica lm) {
		String deleteFinanceiroMensal = "DELETE FROM tb_financeiro_mensal_almoxarifado WHERE cv_mes_referencia = '"+mesRelatorio+"'";
		lm.executarCUD(deleteFinanceiroMensal);
		String deleteFinanceiroGrupo = "DELETE FROM tb_financeiro_mensal_grupo_almoxarifado WHERE cv_mes_referencia = '"+mesRelatorio+"'";
		lm.executarCUD(deleteFinanceiroGrupo);
	}

	private static String getMesTransporteRelatorio() {
		Calendar mesAnterior2 = Calendar.getInstance();
		mesAnterior2.add(Calendar.MONTH, -2);
		String mesTransporteRelatorio = new SimpleDateFormat("yyyy-MM").format(mesAnterior2.getTime());
		return mesTransporteRelatorio;
	}
	
	private static String getMesRelatorio() {
		Calendar mesAnterior = Calendar.getInstance();
		mesAnterior.add(Calendar.MONTH, -1);
		String mesRelatorio = new SimpleDateFormat("yyyy-MM").format(mesAnterior.getTime());
		return mesRelatorio;
	}
	
	private static void gerarRelatorioMesDetalhado(String mesRelatorio, String mesAnterior, LinhaMecanica lm){
		String sqlFinanceiro = getSqlFinanceiro(mesRelatorio, mesAnterior);
		
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlFinanceiro));
		try {
			while (rs.next()) {
				int id = rs.getInt("idMaterialAlmoxarifado");
				int idGrupo = rs.getInt("idGrupo");
				int idSubGrupo = rs.getInt("idSubGrupo");
				BigDecimal saldoAtual = new BigDecimal(rs.getInt("saldoAtual"));
				BigDecimal precoMedioAtual = new BigDecimal(rs.getDouble("precoMedioAtual")).setScale(SCALE, ROUDING);
				BigDecimal saldoTransportado = new BigDecimal(rs.getInt("saldoTransportado"));
				BigDecimal precoMedioTransportado = new BigDecimal(rs.getDouble("precoMedioTransportado")).setScale(SCALE, ROUDING);
				BigDecimal totalEntrada = new BigDecimal(rs.getInt("totalEntrada"));
				BigDecimal totalSaida = new BigDecimal(rs.getInt("totalSaida"));
				BigDecimal totalTransportado  = new BigDecimal(rs.getDouble("valorFinalTransportado")).setScale(SCALE, ROUDING);
				
				
				BigDecimal precoMedio = new BigDecimal(0);
				
				if(totalEntrada.add(saldoTransportado).intValue() != 0){
					BigDecimal saldoEntrada = totalEntrada.setScale(SCALE, ROUDING).multiply(precoMedioAtual.setScale(SCALE, ROUDING)).setScale(SCALE, ROUDING);
					precoMedio = totalTransportado.setScale(SCALE, ROUDING).add(saldoEntrada.setScale(SCALE, ROUDING)).
							divide(
									totalEntrada.setScale(SCALE, ROUDING).add(saldoTransportado.setScale(SCALE, ROUDING)), MathContext.DECIMAL32);
				}
				
				BigDecimal valorEntrada = precoMedioAtual.setScale(SCALE, ROUDING).multiply(totalEntrada.setScale(SCALE, ROUDING)).setScale(SCALE, ROUDING);
				BigDecimal valorSaida = totalSaida.setScale(SCALE, ROUDING).multiply(precoMedio.setScale(SCALE, ROUDING));
				BigDecimal totalFinal = totalTransportado.setScale(SCALE, ROUDING).add(valorEntrada.setScale(SCALE, ROUDING)).setScale(SCALE, ROUDING).subtract(valorSaida.setScale(SCALE, ROUDING)).setScale(SCALE, ROUDING);
				
				String sqlInsert = "INSERT INTO tb_financeiro_mensal_almoxarifado( "+
						            "id_material_almoxarifado,  "+
						            "db_preco_medio_transportado, db_preco_medio_atual, "+ 
						            "in_saldo_transportado, in_total_entrada, in_total_saida, cv_mes_referencia, "+ 
						            "dt_data_insercao, dt_data_atualizacao, in_saldo_atual, id_grupo_almoxarifado, db_preco_medio, id_sub_grupo_almoxarifado, db_valor_entrada, db_valor_saida, db_valor_transportado, db_valor_final) "+
								    "VALUES ("+id+", "
						            +precoMedioTransportado.setScale(SCALE, ROUDING)+", "
								    +precoMedioAtual.setScale(SCALE, ROUDING)+", "
						            +saldoTransportado+",  "
								    +totalEntrada+", "
						            +totalSaida+", "
						            + "'"+mesRelatorio+"', now(), null, "
								    +saldoAtual+", "
						            +idGrupo+", "
								    +precoMedio+", "
						            +(idSubGrupo==0?null:idSubGrupo)+", "
						            +valorEntrada.setScale(SCALE, ROUDING)+", "
						            +valorSaida.setScale(SCALE, ROUDING)+", "
						            +totalTransportado.setScale(SCALE, ROUDING)+", "
								    +totalFinal.setScale(SCALE, ROUDING)+")";
				System.out.println(sqlInsert);
				if(!lm.executarCUD(sqlInsert)){
					System.out.println("ERRO!");
					System.exit(1);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static String getSqlFinanceiro(String mesRelatorio, String mesAnterior){
		String sql = " select y.id_material_almoxarifado idMaterialAlmoxarifado, y.id_grupo_almoxarifado idGrupo, y.id_sub_grupo_almoxarifado idSubGrupo, "+
						" (select coalesce(r.in_saldo_atual, 0) from tb_financeiro_mensal_almoxarifado r where r.cv_mes_referencia = '"+mesAnterior+"' and r.id_material_almoxarifado = y.id_material_almoxarifado) saldoTransportado, "+ 
						" (select coalesce(q.db_preco_medio, 0) from tb_financeiro_mensal_almoxarifado q where q.cv_mes_referencia = '"+mesAnterior+"' and q.id_material_almoxarifado = y.id_material_almoxarifado) precoMedioTransportado, "+ 
						" (select coalesce(t.db_valor_final, 0) from tb_financeiro_mensal_almoxarifado t where t.cv_mes_referencia = '"+mesAnterior+"' and t.id_material_almoxarifado = y.id_material_almoxarifado) valorFinalTransportado,  "+
						" coalesce(sum(( "+
						" 		select sum(a.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado a where to_char(a.dt_data_movimento, 'YYYY-MM') = '"+mesRelatorio+"' "+ 
						" 		and a.id_estoque_almoxarifado in (select c.id_estoque_almoxarifado from tb_estoque_almoxarifado c where c.id_material_almoxarifado = y.id_material_almoxarifado) "+
						" 		and a.id_tipo_movimento_almoxarifado in (select b.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado b where b.tp_operacao = 'E'))), 0) totalEntrada, "+
						" coalesce(sum(( "+
						" 		select sum(a.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado a where to_char(a.dt_data_movimento, 'YYYY-MM') = '"+mesRelatorio+"' "+ 
						" 		and a.id_estoque_almoxarifado in (select c.id_estoque_almoxarifado from tb_estoque_almoxarifado c where c.id_material_almoxarifado = y.id_material_almoxarifado) "+
						" 		and a.id_tipo_movimento_almoxarifado in (select b.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado b where b.tp_operacao != 'E'))), 0) totalSaida, "+
						" (select sum(a.in_quantidade_atual) from tb_estoque_almoxarifado a where a.id_material_almoxarifado = y.id_material_almoxarifado and a.bl_bloqueado is false) saldoAtual, "+
						" (select coalesce(avg(a.db_valor_unitario), 0) from tb_nota_fiscal_estoque_almoxarifado a  "+
						" 	inner join tb_estoque_almoxarifado b on a.id_estoque_almoxarifado = b.id_estoque_almoxarifado "+ 
						" 	where b.id_material_almoxarifado = y.id_material_almoxarifado and a.id_nota_fiscal_almoxarifado in "+ 
						" 	(select e.id_nota_fiscal_almoxarifado from tb_nota_fiscal_almoxarifado e where to_char(e.dt_data_contabil, 'YYYY-MM') = '"+mesRelatorio+"' and e.bl_doacao is false)) precoMedioAtual "+
						" from tb_material_almoxarifado y  "+
						" inner join tb_grupo_almoxarifado b on b.id_grupo_almoxarifado = y.id_grupo_almoxarifado and b.bl_sem_financeiro is false "+
						" group by idMaterialAlmoxarifado,  idGrupo, idSubGrupo "+
						" order by idMaterialAlmoxarifado ";
		System.out.println(sql);
		return sql;
	}
	
	private static void gerarFinanceiroMesGrupo(String mesReferencia, LinhaMecanica lm){
		String sql2 = "select distinct a.id_grupo_almoxarifado from tb_financeiro_mensal_almoxarifado a "+ 
				"where a.cv_mes_referencia = '"+mesReferencia+"' "+
				"order by a.id_grupo_almoxarifado";

		ResultSet rs2 = lm.consultar(sql2);
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
}
