package br.com.imhotep.linhaMecanica;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import br.com.imhotep.auxiliar.Constantes;

public class FinanceiroAlmoxarifado {
	
	private static RoundingMode rouding = RoundingMode.HALF_EVEN;
	private static int scale = 3;
	
	public static void main(String[] args) {
		try {
//			primeiroSqlParaTransporteFinanceiro();
			transporteJaneiro();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void transporteJaneiro() throws ParseException{
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp("200.133.41.8");
		
		String sqlFinanceiroJaneiroAl = "";
		
		if(false)
			sqlFinanceiroJaneiroAl = getSqlFinanceiroEntradaMedLynxJaneiro();
		else
			sqlFinanceiroJaneiroAl = getSqlFinanceiroJaneiroSemGrupoEspecial();
		
		
		
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlFinanceiroJaneiroAl));
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int idGrupo = rs.getInt("idGrupo");
				int idSubGrupo = rs.getInt("idSubGrupo");
				BigDecimal saldoAtual = new BigDecimal(rs.getInt("saldoAtual"));
				BigDecimal precoMedioAtual = new BigDecimal(rs.getDouble("precoMedioAtual"));
				BigDecimal saldoTransportado = new BigDecimal(rs.getInt("saldoTransportado"));
				BigDecimal precoMedioTransportado = new BigDecimal(rs.getDouble("precoMedioTransportado"));
				BigDecimal totalEntrada = new BigDecimal(rs.getInt("totalEntrada"));
				BigDecimal totalSaida = new BigDecimal(rs.getInt("totalSaida"));
				
				BigDecimal precoMedio = new BigDecimal(0);
				
				if(totalEntrada.add(saldoTransportado).intValue() != 0){
					BigDecimal totalTransportado = precoMedioTransportado.setScale(scale, rouding).multiply(saldoTransportado.setScale(scale, rouding)).setScale(scale, rouding) ;
					BigDecimal saldoEntrada = totalEntrada.setScale(scale, rouding).multiply(precoMedioAtual.setScale(scale, rouding)).setScale(scale, rouding);
					precoMedio = totalTransportado.setScale(scale, rouding).add(saldoEntrada.setScale(scale, rouding)).
							divide(
									totalEntrada.setScale(scale, rouding).add(saldoTransportado.setScale(scale, rouding)), MathContext.DECIMAL32);
				}
				
				BigDecimal totalTransportado = precoMedioTransportado.setScale(scale, rouding).multiply(saldoTransportado.setScale(scale, rouding)).setScale(scale, rouding);
				BigDecimal valorEntrada = precoMedioAtual.setScale(scale, rouding).multiply(totalEntrada.setScale(scale, rouding)).setScale(scale, rouding);
				BigDecimal valorSaida = totalSaida.setScale(scale, rouding).multiply(precoMedio.setScale(scale, rouding));
				BigDecimal totalFinal = totalTransportado.setScale(scale, rouding).add(valorEntrada.setScale(scale, rouding)).setScale(scale, rouding).subtract(valorSaida.setScale(scale, rouding)).setScale(scale, rouding);
				
				String sqlInsert = "INSERT INTO tb_financeiro_mensal_almoxarifado( "+
						            "id_material_almoxarifado,  "+
						            "db_preco_medio_transportado, db_preco_medio_atual, "+ 
						            "in_saldo_transportado, in_total_entrada, in_total_saida, cv_mes_referencia, "+ 
						            "dt_data_insercao, dt_data_atualizacao, in_saldo_atual, id_grupo_almoxarifado, db_preco_medio, id_sub_grupo_almoxarifado, db_valor_entrada, db_valor_saida, db_valor_transportado, db_valor_final) "+
								    "VALUES ("+id+", "
						            +precoMedioTransportado.setScale(scale, rouding)+", "
								    +precoMedioAtual.setScale(scale, rouding)+", "
						            +saldoTransportado+",  "
								    +totalEntrada+", "
						            +totalSaida+", '2014-01', now(), null, "
								    +saldoAtual+", "
						            +idGrupo+", "
								    +precoMedio+", "
						            +(idSubGrupo==0?null:idSubGrupo)+", "
						            +valorEntrada.setScale(scale, rouding)+", "
						            +valorSaida.setScale(scale, rouding)+", "
						            +totalTransportado.setScale(scale, rouding)+", "
								    +totalFinal.setScale(scale, rouding)+")";
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
	
	
	private static void primeiroSqlParaTransporteFinanceiro() throws ParseException{
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_LOCAL);
		
		String mesAtual = "2013-11-30"; 
		String mesPassado = "2013-11-30";
		
		String dataResumida =  new SimpleDateFormat("yyyy-MM").format(new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).parse(mesAtual));
		String dataCompleta =  new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).parse(mesAtual));
		String dataPassada =  new SimpleDateFormat("yyyy-MM").format(new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).parse(mesPassado));
		
		String sqlFinanceiroAlmoxarifado = "";
		
		if(dataResumida.equals("2013-11"))
			sqlFinanceiroAlmoxarifado = getSqlFinanceiroPrimeiraEntrada();
		else
			sqlFinanceiroAlmoxarifado = getSqlFinanceiroSegundoSql(dataResumida, dataCompleta, dataPassada);
		
		
		
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlFinanceiroAlmoxarifado));
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int idGrupo = rs.getInt("idGrupo");
				int idSubGrupo = rs.getInt("idSubGrupo");
				int saldoAtual = rs.getInt("saldoAtual");
				Double precoMedioAtual = rs.getDouble("precoMedioAtual");
				int saldoTransportado = rs.getInt("saldoTransportado");
				Double precoMedioTransportado = rs.getDouble("precoMedioTransportado");
				
				Double precoMedio = (precoMedioAtual != 0 && precoMedioTransportado != 0) ? (precoMedioAtual + precoMedioTransportado)/2 : (precoMedioAtual != 0 ? precoMedioAtual : (precoMedioTransportado != 0 ? precoMedioTransportado : 0d));
				int totalEntrada = rs.getInt("totalEntrada");
				int totalSaida = rs.getInt("totalSaida");
				
				String sqlInsert = "INSERT INTO tb_financeiro_mensal_almoxarifado( "+
						            "id_material_almoxarifado,  "+
						            "db_preco_medio_transportado, db_preco_medio_atual, "+ 
						            "in_saldo_transportado, in_total_entrada, in_total_saida, cv_mes_referencia, "+ 
						            "dt_data_insercao, dt_data_atualizacao, in_saldo_atual, id_grupo_almoxarifado, db_preco_medio, id_sub_grupo_almoxarifado) "+
								    "VALUES ("+id+", "+precoMedioTransportado+", "+
								    precoMedioAtual+", "+saldoTransportado+",  "+ totalEntrada+", "+
								    totalSaida+", '"+dataResumida+"', now(), null, "+saldoAtual+", "+idGrupo+", "+precoMedio+", "+(idSubGrupo==0?null:idSubGrupo)+")";
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

	private static String getSqlFinanceiroSegundoSql(String dataResumida, String dataCompleta, String dataPassada){
		String sql = "select "+
			    "      f.id_grupo_almoxarifado idGrupo, "+  
			    "      f.cv_descricao grupo, "+
			    "      k.id_sub_grupo_almoxarifado idSubGrupo, "+
			    "      k.cv_descricao subgrupo, "+
				"  b.id_material_almoxarifado id, "+
				"  b.cv_descricao as material, "+
				"  (select c.db_preco_medio from tb_financeiro_mensal_almoxarifado c where c.id_material_almoxarifado = b.id_material_almoxarifado and c.cv_mes_referencia = '"+dataPassada+"') precoMedioTransportado, "+
				"  (select c.in_saldo_atual from tb_financeiro_mensal_almoxarifado c where c.id_material_almoxarifado = b.id_material_almoxarifado and c.cv_mes_referencia = '"+dataPassada+"') saldoTransportado, "+
				"  ( "+
				"  coalesce(sum((select sum(g.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado g where cast(g.dt_data_movimento as date) <= cast('"+dataCompleta+"' as date) "+
				"        and g.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
				"	and g.id_tipo_movimento_almoxarifado in (select h.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado h where h.tp_operacao = 'E'))), 0) - "+
				"  coalesce(sum((select sum(i.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado i where cast(i.dt_data_movimento as date) <= cast('"+dataCompleta+"' as date) "+
				"        and i.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
				"	and i.id_tipo_movimento_almoxarifado in (select j.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado j where j.tp_operacao != 'E'))), 0) "+
				") as saldoAtual, "+
				"  coalesce(avg(d.db_valor_unitario), 0) precoMedioAtual, "+
				"  coalesce(sum((select sum(g.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado g where to_char(g.dt_data_movimento, 'YYYY-MM') = '"+dataResumida+"' "+
				"        and g.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
				"	and g.id_tipo_movimento_almoxarifado in (select h.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado h where h.tp_operacao = 'E'))), 0) totalEntrada, "+
				"  coalesce(sum((select sum(i.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado i where to_char(i.dt_data_movimento, 'YYYY-MM') = '"+dataResumida+"' "+
				"        and i.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
				"	and i.id_tipo_movimento_almoxarifado in (select p.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado p where p.tp_operacao != 'E'))), 0) totalSaida "+ 
				"from tb_material_almoxarifado b "+
				"inner join tb_grupo_almoxarifado f on f.id_grupo_almoxarifado = b.id_grupo_almoxarifado "+
				"left join tb_sub_grupo_almoxarifado k on  k.id_sub_grupo_almoxarifado = b.id_sub_grupo_almoxarifado "+
				"left join tb_estoque_almoxarifado a on a.id_material_almoxarifado = b.id_material_almoxarifado "+
				"left join tb_nota_fiscal_estoque_almoxarifado d on d.id_estoque_almoxarifado = a.id_estoque_almoxarifado and d.id_nota_fiscal_almoxarifado in ( "+
				"  select e.id_nota_fiscal_almoxarifado from tb_nota_fiscal_almoxarifado e where to_char(e.dt_data_contabil, 'YYYY-MM') = '"+dataResumida+"') "+
				"where f.id_grupo_almoxarifado not in (21, 13, 15) "+
				"group by idGrupo, grupo, idSubGrupo, subGrupo, id, material "+
				"order by b.id_material_almoxarifado";
		return sql;
	}
	
	private static String getSqlFinanceiroPrimeiraEntrada(){
		String sql = "select "+
		    "      f.id_grupo_almoxarifado idGrupo, "+  
		    "      f.cv_descricao grupo, "+
		    "      k.id_sub_grupo_almoxarifado idSubGrupo, "+
		    "      k.cv_descricao subgrupo, "+
			"  b.id_material_almoxarifado id, "+
			"  b.cv_descricao as material, "+
			"  coalesce(o.in_saldo_transportado, 0) saldoTransportado, "+
			"  coalesce(o.db_preco_medio_transportado, 0) precoMedioTransportado, "+
			"  ( "+
			"  coalesce(sum((select sum(g.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado g where cast(g.dt_data_movimento as date) <= cast('2013-11-30' as date) "+
			"        and g.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
			"	and g.id_tipo_movimento_almoxarifado in (select h.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado h where h.tp_operacao = 'E'))), 0) - "+
			"  coalesce(sum((select sum(i.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado i where cast(i.dt_data_movimento as date) <= cast('2013-11-30' as date) "+
			"        and i.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
			"	and i.id_tipo_movimento_almoxarifado in (select j.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado j where j.tp_operacao != 'E'))), 0) "+
			") as saldoAtual, "+
			"  coalesce(avg(d.db_valor_unitario), 0) precoMedioAtual, "+
			"  coalesce(sum((select sum(g.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado g where to_char(g.dt_data_movimento, 'YYYY-MM') = '2013-11' "+
			"        and g.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
			"	and g.id_tipo_movimento_almoxarifado in (select h.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado h where h.tp_operacao = 'E'))), 0) totalEntrada, "+
			"  coalesce(sum((select sum(i.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado i where to_char(i.dt_data_movimento, 'YYYY-MM') = '2013-11' "+
			"        and i.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
			"	and i.id_tipo_movimento_almoxarifado in (select p.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado p where p.tp_operacao != 'E'))), 0) totalSaida "+ 
			"from tb_material_almoxarifado b "+
			"inner join tb_grupo_almoxarifado f on f.id_grupo_almoxarifado = b.id_grupo_almoxarifado "+
			"left join tb_sub_grupo_almoxarifado k on  k.id_sub_grupo_almoxarifado = b.id_sub_grupo_almoxarifado "+
			"left join tb_estoque_almoxarifado a on a.id_material_almoxarifado = b.id_material_almoxarifado "+
			"left join tb_nota_fiscal_estoque_almoxarifado d on d.id_estoque_almoxarifado = a.id_estoque_almoxarifado and d.id_nota_fiscal_almoxarifado in ( "+
			"  select e.id_nota_fiscal_almoxarifado from tb_nota_fiscal_almoxarifado e where to_char(e.dt_data_contabil, 'YYYY-MM') = '2013-11') "+
			"left join tb_material_almoxarifado_preco_medio_transportado_medlynx o on o.id_material_almoxarifado = b.id_material_almoxarifado and o.id_material_almoxarifado not in (335,336,139,146,148,108,123,157,158,159, 160) " +
			"where f.id_grupo_almoxarifado not in (21, 13, 15) and b.id_material_almoxarifado not in (31,441,442,160,569,473,324,573) "+
			"group by idGrupo, grupo, idSubGrupo, subGrupo, id, material, o.in_saldo_transportado, o.db_preco_medio_transportado "+
			"order by b.id_material_almoxarifado";
		return sql;
	}
	
	private static String getSqlFinanceiroEntradaMedLynxJaneiro(){
		String sql = "select "+
			    "      f.id_grupo_almoxarifado idGrupo, "+  
			    "      f.cv_descricao grupo, "+
			    "      k.id_sub_grupo_almoxarifado idSubGrupo, "+
			    "      k.cv_descricao subgrupo, "+
				"  b.id_material_almoxarifado id, "+
				"  b.cv_descricao as material, "+
				"  coalesce(o.in_saldo_transportado, 0) saldoTransportado, "+
				"  coalesce(o.db_preco_medio_transportado, 0) precoMedioTransportado, "+
				"  ( "+
				"  coalesce(sum((select sum(g.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado g where cast(g.dt_data_movimento as date) <= cast('2014-01-31' as date) "+
				"        and g.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
				"	and g.id_tipo_movimento_almoxarifado in (select h.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado h where h.tp_operacao = 'E'))), 0) - "+
				"  coalesce(sum((select sum(i.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado i where cast(i.dt_data_movimento as date) <= cast('2014-01-31' as date) "+
				"        and i.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
				"	and i.id_tipo_movimento_almoxarifado in (select j.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado j where j.tp_operacao != 'E'))), 0) "+
				") as saldoAtual, "+
				"  coalesce(avg(d.db_valor_unitario), 0) precoMedioAtual, "+
				"  coalesce(sum((select sum(g.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado g where to_char(g.dt_data_movimento, 'YYYY-MM') = '2014-01' "+
				"        and g.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
				"	and g.id_tipo_movimento_almoxarifado in (select h.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado h where h.tp_operacao = 'E'))), 0) totalEntrada, "+
				"  coalesce(sum((select sum(i.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado i where to_char(i.dt_data_movimento, 'YYYY-MM') = '2014-01' "+
				"        and i.id_estoque_almoxarifado = a.id_estoque_almoxarifado "+
				"	and i.id_tipo_movimento_almoxarifado in (select p.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado p where p.tp_operacao != 'E'))), 0) totalSaida "+ 
				"from tb_material_almoxarifado b "+
				"inner join tb_grupo_almoxarifado f on f.id_grupo_almoxarifado = b.id_grupo_almoxarifado "+
				"left join tb_sub_grupo_almoxarifado k on  k.id_sub_grupo_almoxarifado = b.id_sub_grupo_almoxarifado "+
				"left join tb_estoque_almoxarifado a on a.id_material_almoxarifado = b.id_material_almoxarifado "+
				"left join tb_nota_fiscal_estoque_almoxarifado d on d.id_estoque_almoxarifado = a.id_estoque_almoxarifado and d.id_nota_fiscal_almoxarifado in ( "+
				"  select e.id_nota_fiscal_almoxarifado from tb_nota_fiscal_almoxarifado e where to_char(e.dt_data_contabil, 'YYYY-MM') = '2014-01') "+
				"left join tb_material_almoxarifado_preco_medio_transportado_medlynx o on o.id_material_almoxarifado = b.id_material_almoxarifado " +
				"where f.id_grupo_almoxarifado in (21, 13, 15) "+
				"group by idGrupo, grupo, idSubGrupo, subGrupo, id, material, o.in_saldo_transportado, o.db_preco_medio_transportado "+
				"order by b.id_material_almoxarifado";
		return sql;
	}
	
	private static String getSqlFinanceiroJaneiroSemGrupoEspecial(){
		String sql = "select" 
						+" f.id_grupo_almoxarifado idGrupo," 
						+" f.cv_descricao grupo, "
						+" k.id_sub_grupo_almoxarifado idSubGrupo," 
						+" k.cv_descricao subgrupo, "
						+" b.id_material_almoxarifado id," 
						+" b.cv_descricao as material, "
						+" coalesce((select r.in_saldo_atual from tb_financeiro_mensal_almoxarifado r where r.cv_mes_referencia = '2013-12' and r.id_material_almoxarifado = b.id_material_almoxarifado), 0) saldoTransportado, "
						+" coalesce((select q.db_preco_medio from tb_financeiro_mensal_almoxarifado q where q.cv_mes_referencia = '2013-12' and q.id_material_almoxarifado = b.id_material_almoxarifado), 0) precoMedioTransportado," 
						+" ( "
						+" coalesce(sum((select sum(g.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado g where cast(g.dt_data_movimento as date) <= cast('2014-01-31' as date)" 
						+" and g.id_estoque_almoxarifado = a.id_estoque_almoxarifado "
						+" and g.id_tipo_movimento_almoxarifado in (select h.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado h where h.tp_operacao = 'E'))), 0) - "
						+" coalesce(sum((select sum(i.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado i where cast(i.dt_data_movimento as date) <= cast('2014-01-31' as date)" 
						+" and i.id_estoque_almoxarifado = a.id_estoque_almoxarifado "
						+" and i.id_tipo_movimento_almoxarifado in (select j.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado j where j.tp_operacao != 'E'))), 0)" 
						+" ) as saldoAtual, "
						+" coalesce(avg(d.db_valor_unitario), 0) precoMedioAtual, "
						+" coalesce(sum((select sum(g.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado g where to_char(g.dt_data_movimento, 'YYYY-MM') = '2014-01'" 
						+" and g.id_estoque_almoxarifado = a.id_estoque_almoxarifado "
				        +" and g.id_tipo_movimento_almoxarifado in (select h.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado h where h.tp_operacao = 'E'))), 0) totalEntrada, "
				        +" coalesce(sum((select sum(i.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado i where to_char(i.dt_data_movimento, 'YYYY-MM') = '2014-01'" 
						+" and i.id_estoque_almoxarifado = a.id_estoque_almoxarifado "
				        +" 	and i.id_tipo_movimento_almoxarifado in (select p.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado p where p.tp_operacao != 'E'))), 0) totalSaida" 
				        +" from tb_material_almoxarifado b "
						+" inner join tb_grupo_almoxarifado f on f.id_grupo_almoxarifado = b.id_grupo_almoxarifado "
						+" left join tb_sub_grupo_almoxarifado k on  k.id_sub_grupo_almoxarifado = b.id_sub_grupo_almoxarifado" 
						+" left join tb_estoque_almoxarifado a on a.id_material_almoxarifado = b.id_material_almoxarifado "
						+" left join tb_nota_fiscal_estoque_almoxarifado d on d.id_estoque_almoxarifado = a.id_estoque_almoxarifado and d.id_nota_fiscal_almoxarifado in ( "
						+"   select e.id_nota_fiscal_almoxarifado from tb_nota_fiscal_almoxarifado e where to_char(e.dt_data_contabil, 'YYYY-MM') = '2014-01' and e.bl_doacao is false)" 
						+" where f.id_grupo_almoxarifado not in (21, 13, 15) "
						+" group by idGrupo, grupo, idSubGrupo, subGrupo, id, material" 
						+" order by b.id_material_almoxarifado";
		return sql;
	}
}

