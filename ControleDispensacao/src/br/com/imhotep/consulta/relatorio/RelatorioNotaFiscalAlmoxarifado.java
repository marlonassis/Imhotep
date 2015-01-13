package br.com.imhotep.consulta.relatorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.extra.FinanceiroGrupoAlmoxarifado;
import br.com.imhotep.entidade.extra.FinanceiroGrupoNotaFiscalAlmoxarifado;
import br.com.imhotep.entidade.extra.FinanceiroNotaFiscalAlmoxarifadoDesconto;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class RelatorioNotaFiscalAlmoxarifado {
	
	public static void main(String[] args) {
		List<FinanceiroGrupoAlmoxarifado> lista = new RelatorioNotaFiscalAlmoxarifado().consultarResultados(new Date());
		System.out.println(lista);
	}
	
	public List<FinanceiroNotaFiscalAlmoxarifadoDesconto> listaNFDesconto(Date dataIni){
		String data = new SimpleDateFormat("yyyy-MM").format(dataIni);
		String sqlDescontoNFs = "select  a.cv_identificacao notaFiscal, coalesce(a.db_valor_desconto, 0) valorDesconto from tb_nota_fiscal_almoxarifado a "+ 
								"where to_char(a.dt_data_contabil, 'YYYY-MM') = '"+data+"' and a.bl_doacao is false and a.db_valor_desconto > 0 "+
								"order by a.cv_identificacao ";
		List<FinanceiroNotaFiscalAlmoxarifadoDesconto> lista = new ArrayList<FinanceiroNotaFiscalAlmoxarifadoDesconto>();
		LinhaMecanica lm = new LinhaMecanica();
		ResultSet rs = lm.consultar(sqlDescontoNFs);
		try {
			while(rs.next()){
				String notaFiscal = rs.getString("notaFiscal");
				double desconto = rs.getDouble("valorDesconto");
				lista.add(new FinanceiroNotaFiscalAlmoxarifadoDesconto(notaFiscal, desconto));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List<FinanceiroGrupoAlmoxarifado> consultarResultados(Date dataIni){
		return consultaMovimentoPeriodo(dataIni);
	}
	
	private List<FinanceiroGrupoAlmoxarifado> consultaMovimentoPeriodo(Date dataIni) {
		String data = new SimpleDateFormat("yyyy-MM").format(dataIni);
		String sql = "select  a.cv_descricao grupo, e.cv_identificacao notaFiscal, f.cv_razao_social fornecedor, "+
				 	"f.cv_cadastro cadastro, e.db_valor_total total from tb_grupo_almoxarifado a "+ 
					"inner join tb_material_almoxarifado b on b.id_grupo_almoxarifado = a.id_grupo_almoxarifado "+
					"inner join tb_estoque_almoxarifado c on c.id_material_almoxarifado = b.id_material_almoxarifado "+
					"inner join tb_nota_fiscal_estoque_almoxarifado d on d.id_estoque_almoxarifado = c.id_estoque_almoxarifado "+
					"inner join tb_nota_fiscal_almoxarifado e on e.id_nota_fiscal_almoxarifado = d.id_nota_fiscal_almoxarifado "+
					"inner join tb_fornecedor f on f.id_fornecedor = e.id_fornecedor "+
					"where to_char(e.dt_data_contabil, 'YYYY-MM') = '"+data+"' and a.bl_sem_financeiro is false "+
					"group by grupo, notaFiscal, fornecedor, cadastro, e.db_valor_total "+
					"order by grupo, e.cv_identificacao";
		
		List<FinanceiroGrupoAlmoxarifado> listGrupo = new ArrayList<FinanceiroGrupoAlmoxarifado>();
		List<FinanceiroGrupoNotaFiscalAlmoxarifado> listItens = new ArrayList<FinanceiroGrupoNotaFiscalAlmoxarifado>();
		String grupoTemp="";
		LinhaMecanica lm = new LinhaMecanica();
		for(Object[] obj : lm.getListaResultado(sql)){
			String grupo = String.valueOf(obj[0]);
			String notaFiscal = String.valueOf(obj[1]);
			String fornecedor = String.valueOf(obj[2]);
			String inscricao = String.valueOf(obj[3]);
			Double total = Double.valueOf(String.valueOf(obj[4]));
			
			if(grupoTemp.equals("")){
				grupoTemp = grupo;
			}else
				if(!grupoTemp.equals(grupo)){
					listGrupo.add(new FinanceiroGrupoAlmoxarifado(grupoTemp, listItens));
					listItens = new ArrayList<FinanceiroGrupoNotaFiscalAlmoxarifado>();
					grupoTemp = grupo;
				}
			
			listItens.add(new FinanceiroGrupoNotaFiscalAlmoxarifado(notaFiscal, fornecedor, inscricao, total));
		}
		if(!grupoTemp.equals(""))
			listGrupo.add(new FinanceiroGrupoAlmoxarifado(grupoTemp, listItens));
		return listGrupo;
	}
	
}
