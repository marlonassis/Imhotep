package br.com.imhotep.linhaMecanica.atualizador;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class AtualizadorSaldoTransportadoMedlynx{
	
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public static void main(String[] args) {
		try {
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(DB_BANCO_IMHOTEP);
			lm.setIp("127.0.0.1");
			String sql = "select b.id_material, b.in_codigo_material as codigoMaterial, b.cv_descricao as descricao, a.in_saldo_transportado as saldoTransportado, "+
							"( "+
							"	select  "+
							"	coalesce(sum((select sum(m.in_quantidade_movimentacao) from tb_movimento_livro m "+
							"	left join tb_estoque n on n.id_estoque = m.id_estoque "+ 
							"	where  "+
							"	  m.dt_data_movimento <= cast('2013-05-31 23:59:59' as timestamp) and "+
							"	  m.id_tipo_movimento in (select o.id_tipo_movimento from tb_tipo_movimento o where o.tp_operacao = 'E') "+
							"	  and n.dt_data_validade >= cast('2013-05-31 23:59:59' as timestamp) and "+
							"	  n.dt_data_inclusao <= cast('2013-05-31 23:59:59' as timestamp) and n.id_material = a.id_material "+
							"	)), 0) -   "+
							"	coalesce(sum((select sum(p.in_quantidade_movimentacao) from tb_movimento_livro p "+
							"	left join tb_estoque q on q.id_estoque = p.id_estoque "+ 
							"	where  "+
							"	  p.dt_data_movimento <= cast('2013-05-31 23:59:59' as timestamp) and "+
							"	  p.id_tipo_movimento in (select r.id_tipo_movimento from tb_tipo_movimento r where r.tp_operacao != 'E') "+
							"	  and q.dt_data_validade >= cast('2013-05-31 23:59:59' as timestamp) and "+
							"	  q.dt_data_inclusao <= cast('2013-05-31 23:59:59' as timestamp) and q.id_material = a.id_material "+
							"	)), 0)	 "+				
							") as saldoImhotep, "+
							"case when a.in_saldo_transportado = 0 then ( "+
							"	select  "+
							"	coalesce(sum((select sum(t.in_quantidade_movimentacao) from tb_movimento_livro t "+
							"	left join tb_estoque u on u.id_estoque = t.id_estoque "+ 
							"	where  "+
							"	  t.dt_data_movimento <= cast('2013-05-31 23:59:59' as timestamp) and "+
							"	  t.id_tipo_movimento in (select v.id_tipo_movimento from tb_tipo_movimento v where v.tp_operacao = 'E') "+
							"	  and u.dt_data_validade >= cast('2013-05-31 23:59:59' as timestamp) and "+
							"	  u.dt_data_inclusao <= cast('2013-05-31 23:59:59' as timestamp) and u.id_material = a.id_material "+
							"	)), 0) -   "+
							"	coalesce(sum((select sum(x.in_quantidade_movimentacao) from tb_movimento_livro x "+
							"	left join tb_estoque w on w.id_estoque = x.id_estoque "+ 
							"	where  "+
							"	  x.dt_data_movimento <= cast('2013-05-31 23:59:59' as timestamp) and "+
							"	  x.id_tipo_movimento in (select y.id_tipo_movimento from tb_tipo_movimento y where y.tp_operacao != 'E') "+
							"	  and w.dt_data_validade >= cast('2013-05-31 23:59:59' as timestamp) and "+
							"	  w.dt_data_inclusao <= cast('2013-05-31 23:59:59' as timestamp) and w.id_material = a.id_material "+
							"	)), 0) "+
							") "+
							"else a.in_saldo_transportado end as saldoFinal,  "+
							"a.db_preco_medio_transportado precoMedioTransportado, "+
							"coalesce((	select avg(d.db_valor_unitario) from tb_nota_fiscal_estoque d "+
							"	inner join tb_estoque e on d.id_estoque = e.id_estoque  "+
							"	inner join tb_material f on e.id_material = f.id_material  "+
							"	inner join tb_nota_fiscal g on d.id_nota_fiscal = g.id_nota_fiscal "+
							"	where g.dt_data_contabil < cast('2013-06-01' as date) and g.bl_doacao is false and   "+
							"	d.dt_data_insercao = (select max(z.dt_data_insercao) from tb_nota_fiscal_estoque z where z.id_estoque = e.id_estoque) "+ 
							"	and e.id_material = b.id_material "+ 
							"), 0) as precoMedioImhotep, "+
							"case when a.db_preco_medio_transportado = 0 then coalesce((  "+
							"	select avg(h.db_valor_unitario) from tb_nota_fiscal_estoque h "+ 
							"	inner join tb_estoque i on h.id_estoque = i.id_estoque  "+
							"	inner join tb_material j on i.id_material = j.id_material  "+
							"	inner join tb_nota_fiscal k on h.id_nota_fiscal = k.id_nota_fiscal "+
							"	where k.dt_data_contabil < cast('2013-06-01' as date) and k.bl_doacao is false and   "+
							"	h.dt_data_insercao = (select max(l.dt_data_insercao) from tb_nota_fiscal_estoque l where l.id_estoque = i.id_estoque) "+ 
							"	and i.id_material = b.id_material  "+
							"), 0) else  a.db_preco_medio_transportado end as precoMedioFinal "+
							"							from tb_preco_medio_transportado_medlynx a  "+
							"							inner join tb_material b on a.id_material = b.id_material "+
							"							group by  b.in_codigo_material, b.cv_descricao, a.in_saldo_transportado, a.db_preco_medio_transportado, b.id_material "+ 
							"							order by to_ascii(b.cv_descricao)";
			ResultSet rs = lm.consultar(sql);
			while (rs.next()) { 
				int idMaterial = rs.getInt("id_material");
//				int codigoMaterial = rs.getInt("codigoMaterial");
//				String descricao = rs.getString("descricao");
//				int saldoTransportado = rs.getInt("saldoTransportado");
				int saldoImhotep =  rs.getInt("saldoImhotep");
//				double precoMedioTransportado =  rs.getDouble("precoMedioTransportado");
				double precoMedioImhotep =  rs.getDouble("precoMedioImhotep");
				
				String updateSql = "UPDATE tb_preco_medio_transportado_medlynx "+
									 "SET in_saldo_imhotep="+saldoImhotep+", db_preco_medio_imhotep="+precoMedioImhotep+" "+
									 "WHERE id_material = "+idMaterial;
				System.out.println(updateSql);
				if(!lm.executarCUD(updateSql)){
					System.exit(1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
