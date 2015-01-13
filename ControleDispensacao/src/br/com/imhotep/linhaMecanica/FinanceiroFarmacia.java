package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;

public class FinanceiroFarmacia {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_LOCAL);
		ResultSet rs = lm.consultar(lm.utf8_to_latin1("select id_estoque, dt_data_validade from tb_estoque_almoxarifado order by id_estoque"));
		try {
			while (rs.next()) {
				
				//add per�odo
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
				
				//add per�odo
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
		
//		String sql = sqlRelatorioCustoEstoque();
//		LinhaMecanica lm = new LinhaMecanica();
//		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
//		lm.setIp(Constantes.IP_LOCAL);
//		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
//		try {
//			while (rs.next()) {
//				
//				//add per�odo
//				int mes = rs.getInt("mes");
//				int ano = rs.getInt("ano");
//				int idMaterial = rs.getInt("idMaterial");
//				Double precoMedio = getPrecoMedio(mes, ano, idMaterial);
//				String data = mes+"/"+ ano;
//				if(mes < 10){
//					data = "0"+data;
//				}
//				int saldoInicial = getSaldoInicial(idMaterial, data);
//				int saldoFinal = getSaldoFinal(idMaterial, data);
//				String sqlInsert = "insert into tb_financeiro_mensal_farmacia "+
//						"( "+
//						"id_material, "+
//						"db_preco_medio, "+
//						"in_saldo_inicial, "+
//						"in_saldo_final, "+
//						"cv_mes_referencia, "+
//						"dt_data_insercao, "+
//						"dt_data_atualizacao) values ("+idMaterial+", "+precoMedio+", "+
//						saldoInicial+", "+saldoFinal+", "+data+", now(), null)";
//				System.out.println(sqlInsert);
////				if(!lm.executarCUD(sqlInsert)){
////					System.out.println("erro!!!");
////				}
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
	}

	private static int getSaldoFinal(int idMaterial, String data){
		String sql = "select  "+
						"sum(case when f.tp_operacao = 'E' then c.in_quantidade_atual + c.in_quantidade_movimentacao "+ 
						"	else c.in_quantidade_atual - c.in_quantidade_movimentacao end) as saldo "+
						"from tb_material a  "+
						"left join tb_estoque b on a.id_material = b.id_material "+
						"left join tb_movimento_livro c on c.id_estoque = b.id_estoque and c.dt_data_movimento = "+ 
						"	(select max(e.dt_data_movimento) from tb_movimento_livro e where e.id_estoque = b.id_estoque and to_char(e.dt_data_movimento, 'MM-yyyy') = to_char(c.dt_data_movimento, 'MM-yyyy')) "+
						"inner join tb_tipo_movimento f on f.id_tipo_movimento = c.id_tipo_movimento "+
						"where a.id_material = "+idMaterial+" and to_char(c.dt_data_movimento, 'MM-yyyy') = '"+data+"' ";
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_LOCAL);
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while(rs.next())
				return rs.getInt("saldo");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private static int getSaldoInicial(int idMaterial, String data){
		String sql = "select  "+
						"sum(case when c.in_quantidade_atual = 0 then c.in_quantidade_movimentacao else c.in_quantidade_atual end) as saldo "+
						"from tb_material a  "+
						"left join tb_estoque b on a.id_material = b.id_material "+
						"left join tb_movimento_livro c on c.id_estoque = b.id_estoque and c.dt_data_movimento = "+ 
						"	(select min(d.dt_data_movimento) from tb_movimento_livro d where d.id_estoque = b.id_estoque and to_char(d.dt_data_movimento, 'MM-yyyy') = to_char(c.dt_data_movimento, 'MM-yyyy')) "+
						"inner join tb_tipo_movimento f on f.id_tipo_movimento = c.id_tipo_movimento "+
						"where a.id_material = "+idMaterial+" and to_char(c.dt_data_movimento, 'MM-yyyy') = '"+data+"'";
				
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_LOCAL);
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while(rs.next())
				return rs.getInt("saldo");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private static Double getPrecoMedio(int mes, int ano, Integer idMaterial){
		if(mes == 0 || ano == 0){
			return 0d;
		}
		
		//ajustando a data para o �ltimo dia do m�s
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, mes-1);
		c.set(Calendar.YEAR, ano);
		String data = ano+"-"+mes+"-"+c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		String sql = "select "+
						"j.id_material as idMaterial, j.cv_descricao as material, "+ 
						"coalesce(avg(h.db_valor_unitario), 0) precoMedio, coalesce(l.db_preco_medio_transportado, 0) as precoMedioMedLynx, "+ 
						"(case  "+
						"   when avg(h.db_valor_unitario) != 0 and l.db_preco_medio_transportado != 0 then "+ 
						"	((avg(h.db_valor_unitario) + coalesce(l.db_preco_medio_transportado, 0)) / 2) "+
						"   when l.db_preco_medio_transportado != 0 then l.db_preco_medio_transportado  "+
						"   else coalesce(avg(h.db_valor_unitario), 0) end) as media "+
						"from tb_material j "+
						"left join tb_estoque i on j.id_material = i.id_material "+
						"left join tb_nota_fiscal_estoque h on h.id_estoque = i.id_estoque "+
						"left join tb_nota_fiscal k on h.id_nota_fiscal = k.id_nota_fiscal "+
						"left join tb_preco_medio_transportado_medlynx l on l.id_material = j.id_material "+
						"where j.id_material = "+idMaterial+" and  "+
						"((k.dt_data_contabil between cast('2013-06-01' as date) and cast((date '"+data+"' + integer '1') as date) and k.bl_doacao is false) or k.id_nota_fiscal is null) "+
						"group by idMaterial, material, l.db_preco_medio_transportado";
				
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_LOCAL);
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while(rs.next())
				return rs.getDouble("media");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0d;
	}
	
	private static String sqlRelatorioCustoEstoque() {
		Calendar dataI = Calendar.getInstance();
		Calendar dataF = Calendar.getInstance();
//		dataI.setTime(dataIni);
		dataI.set(Calendar.DAY_OF_MONTH, 01);
		
//		dataF.setTime(dataFim);
		dataF.set(Calendar.DAY_OF_MONTH, dataF.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return "select to_ascii(o.cv_descricao) as grupo, "+
				"a.id_material as idMaterial, "+
				"a.cv_codigo_material as codigoMaterial, "+
				"to_ascii(a.cv_descricao) as material, "+ 
				"a.id_material as idMaterial, "+
				"extract(month from c.dt_data_movimento) as mes, "+
				"extract(year from c.dt_data_movimento) as ano, "+
				"d.tp_operacao tipoOperacao, "+
				"to_ascii(d.cv_descricao) tipoMovimento, "+
				"sum(c.in_quantidade_movimentacao) as totalMovimentado "+
				"from tb_material a  "+
				"inner join tb_familia m on m.id_familia = a.id_familia "+
				"inner join tb_sub_grupo n on n.id_sub_grupo = m.id_sub_grupo "+
				"inner join tb_grupo o on o.id_grupo = n.id_grupo "+
				"left join tb_estoque b on b.id_material = a.id_material "+
				"left join tb_movimento_livro c on c.id_estoque = b.id_estoque and "
					+ "(c.dt_data_movimento is null or c.dt_data_movimento between cast('"+sdf.format(dataI.getTime())+"' as date) and cast((date '"+sdf.format(dataF.getTime())+"' + integer '1') as date)) "+
				"left join tb_tipo_movimento d on d.id_tipo_movimento = c.id_tipo_movimento "+
				"group by grupo, idMaterial, material, codigoMaterial, a.id_material, ano, mes, tipoOperacao, tipoMovimento "+
				"order by grupo, lower(to_ascii(a.cv_descricao)), ano, mes, d.tp_operacao, tipoMovimento";
	}
	
	
	
	
}
