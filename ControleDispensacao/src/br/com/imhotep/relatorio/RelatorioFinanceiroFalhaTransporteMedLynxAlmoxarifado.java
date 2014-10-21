package br.com.imhotep.relatorio;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.relatorio.FinanceiroAlmoxarifadoMaterialFalha;
import br.com.imhotep.linhaMecanica.LinhaMecanica;


@ManagedBean
@ViewScoped
public class RelatorioFinanceiroFalhaTransporteMedLynxAlmoxarifado extends PadraoRelatorio {
	private static final String DB_BANCO_IMHOTEP = LinhaMecanica.DB_BANCO_IMHOTEP;

	private static final long serialVersionUID = 1L;
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException{
		String mesReferencia = "11-2013";
		String caminho = Constantes.DIR_RELATORIO + "RelatorioFinanceiroFalhaTransporteMedLynxAlmoxarifado.jasper";
		String nomeRelatorio = "RelatorioFinanceiroAlmoxarifado"+mesReferencia.replaceFirst("/", "-")+".pdf";
		List<FinanceiroAlmoxarifadoMaterialFalha> lista = getResultadoPesquisaFalha();
		HashMap<String, Object> map = new HashMap<String, Object>();
		super.geraRelatorio(caminho, nomeRelatorio, lista, map);
		
	}
	
	private List<FinanceiroAlmoxarifadoMaterialFalha> getResultadoPesquisaFalha(){
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(DB_BANCO_IMHOTEP);
		String sqlFinanceiroAlmoxarifado = "select "+
											"  c.cv_descricao grupo, "+
											"  b.id_material_almoxarifado, "+
											"  b.cv_descricao material, "+
											"  a.db_preco_medio_transportado::money::numeric::float8, "+
											"  a.db_preco_medio_atual::money::numeric::float8, "+
											"  a.in_saldo_transportado, "+
											"  a.in_total_entrada, "+
											"  a.in_total_saida, "+
											"  a.in_saldo_atual, "+
											"  a.db_preco_medio::money::numeric::float8, "+ 
											"(   "+
											"coalesce(sum((select sum(g.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado g "+ 
											"inner join tb_estoque_almoxarifado u on u.id_estoque_almoxarifado = g.id_estoque_almoxarifado "+
											"where cast(g.dt_data_movimento as date) <= cast('2013-10-31' as date)  "+
											"and u.id_material_almoxarifado = a.id_material_almoxarifado  "+
											"and g.id_tipo_movimento_almoxarifado in (select h.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado h where h.tp_operacao = 'E'))), 0) - "+ 
											"coalesce(sum((select sum(i.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado i  "+
											"inner join tb_estoque_almoxarifado y on y.id_estoque_almoxarifado = i.id_estoque_almoxarifado "+
											"where cast(i.dt_data_movimento as date) <= cast('2013-10-31' as date)  "+
											"and y.id_material_almoxarifado = a.id_material_almoxarifado  "+
											"and i.id_tipo_movimento_almoxarifado in (select j.id_tipo_movimento_almoxarifado from tb_tipo_movimento_almoxarifado j where j.tp_operacao != 'E'))), 0) "+ 
											") as saldoInicial "+
											"from tb_financeiro_mensal_almoxarifado a "+ 
											"inner join tb_material_almoxarifado b on a.id_material_almoxarifado = b.id_material_almoxarifado "+ 
											"inner join tb_grupo_almoxarifado c on b.id_grupo_almoxarifado = c.id_grupo_almoxarifado  "+
											"where a.cv_mes_referencia = '2013-11' and (a.in_saldo_transportado + a.in_total_entrada - a.in_total_saida != in_saldo_atual) "+
											"group by grupo, b.id_material_almoxarifado, a.db_preco_medio_transportado, "+
											"  a.db_preco_medio_atual, "+
											"  a.in_saldo_transportado, "+
											"  a.in_total_entrada, "+
											"  a.in_total_saida, "+
											"  a.in_saldo_atual, "+
											"  a.db_preco_medio "+
											"order by lower(to_ascii(c.cv_descricao)), lower(to_ascii(b.cv_descricao))";

		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlFinanceiroAlmoxarifado));
		List<FinanceiroAlmoxarifadoMaterialFalha> lista = new ArrayList<FinanceiroAlmoxarifadoMaterialFalha>();
		try {
			while (rs.next()) {
				String grupo = rs.getString("grupo");
				String material = rs.getString("material");
				int idMaterialAlmoxarifado = rs.getInt("id_material_almoxarifado");
				int saldoTransportado = rs.getInt("in_saldo_transportado");
				int saldoAtual = rs.getInt("in_saldo_atual");
				int saldoInicial = rs.getInt("saldoInicial");
				int totalEntrada = rs.getInt("in_total_entrada");
				int totalSaida = rs.getInt("in_total_saida");
				double precoMedioTransportado = rs.getDouble("db_preco_medio_transportado");
				double precoMedioAtual = rs.getInt("db_preco_medio_atual");
				double precoMedio = rs.getInt("db_preco_medio");
				
				FinanceiroAlmoxarifadoMaterialFalha obj = 
						new FinanceiroAlmoxarifadoMaterialFalha(saldoInicial, grupo, idMaterialAlmoxarifado, idMaterialAlmoxarifado+" - "+material, precoMedioTransportado, precoMedioAtual, 
								saldoTransportado, saldoAtual, totalEntrada, totalSaida, precoMedio);
				
				lista.add(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}
	
	
}
