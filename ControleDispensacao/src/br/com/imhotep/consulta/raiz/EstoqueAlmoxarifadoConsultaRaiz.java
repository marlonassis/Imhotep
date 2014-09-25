package br.com.imhotep.consulta.raiz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.comparador.EstoqueAlmoxarifadoDataVencimentoComparador;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalEstoqueAlmoxarifado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EstoqueAlmoxarifadoConsultaRaiz  extends ConsultaGeral<NotaFiscalEstoqueAlmoxarifado>{
	
	public NotaFiscalEstoqueAlmoxarifado itemEstoqueNotaFiscalAlmoxarifado(EstoqueAlmoxarifado estoque) {
		String hql = "select o from NotaFiscalEstoqueAlmoxarifado o where o.estoqueAlmoxarifado.idEstoqueAlmoxarifado = "+estoque.getIdEstoqueAlmoxarifado();
		NotaFiscalEstoqueAlmoxarifado item = new ConsultaGeral<NotaFiscalEstoqueAlmoxarifado>().consultaUnica(new StringBuilder(hql), null);
		return item;
	}
	
	public NotaFiscalEstoqueAlmoxarifado itemEstoqueNotaFiscalAlmoxarifado(MovimentoLivroAlmoxarifado mla) {
		String hql = "select o from NotaFiscalEstoqueAlmoxarifado o where o.movimentoLivroAlmoxarifado.idMovimentoLivroAlmoxarifado = "+mla.getIdMovimentoLivroAlmoxarifado();
		NotaFiscalEstoqueAlmoxarifado item = new ConsultaGeral<NotaFiscalEstoqueAlmoxarifado>().consultaUnica(new StringBuilder(hql), null);
		return item;
	}
	
	public Long totalEmEstoqueValido(MaterialAlmoxarifado material) {
		String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		String hql = "select sum(o.quantidadeAtual) from EstoqueAlmoxarifado o where o.quantidadeAtual > 0 and o.bloqueado = false and (o.dataValidade = null or cast(o.dataValidade as date) >= cast('"+dataS+"' as date)) and o.materialAlmoxarifado.idMaterialAlmoxarifado = "+material.getIdMaterialAlmoxarifado();
		Long total = new ConsultaGeral<Long>().consultaUnica(new StringBuilder(hql), null);
		return total;
	}
	
	public List<EstoqueAlmoxarifado> consultarEstoquesMaterial(MaterialAlmoxarifado materialAlmoxarifado) {
		String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		String hql = "select o from EstoqueAlmoxarifado o where o.quantidadeAtual > 0 and o.bloqueado = false and (o.dataValidade is null or cast(o.dataValidade as date) >= cast('"+dataS+"' as date)) and o.materialAlmoxarifado.idMaterialAlmoxarifado = "+materialAlmoxarifado.getIdMaterialAlmoxarifado()+" order by to_ascii(lower(o.lote)), o.dataValidade asc";
		List<EstoqueAlmoxarifado> list = new ArrayList<EstoqueAlmoxarifado>(new ConsultaGeral<EstoqueAlmoxarifado>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<EstoqueAlmoxarifado> consultarTodosEstoquesValidosMaterial(MaterialAlmoxarifado materialAlmoxarifado) {
		String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		String hql = "select o from EstoqueAlmoxarifado o where o.bloqueado = false and (o.dataValidade is null or cast(o.dataValidade as date) >= cast('"+dataS+"' as date)) and o.materialAlmoxarifado.idMaterialAlmoxarifado = "+materialAlmoxarifado.getIdMaterialAlmoxarifado()+" order by to_ascii(lower(o.lote)), o.dataValidade asc";
		List<EstoqueAlmoxarifado> list = new ArrayList<EstoqueAlmoxarifado>(new ConsultaGeral<EstoqueAlmoxarifado>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public EstoqueAlmoxarifado consultarEstoqueLoteCodigoBarras(String codigo) {
		String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		StringBuilder stringB = new StringBuilder("select o from EstoqueAlmoxarifado o where ");
		stringB.append("o.bloqueado = false and o.dataValidade >= '");
		stringB.append(dataS);
		stringB.append("' and (lower(o.lote) = lower('");
		stringB.append(codigo);
		stringB.append("') or codigoBarras = '");
		stringB.append(codigo);
		stringB.append("') order by o.dataValidade asc, to_ascii(lower(o.lote))");
		EstoqueAlmoxarifado estoque = new ConsultaGeral<EstoqueAlmoxarifado>().consultaUnica(stringB, null);
		return estoque;
	}
	
	public List<EstoqueAlmoxarifado> consultarEstoqueVencidoLimiteSeteDias() {
		Calendar dataMesAnterior = Calendar.getInstance();
		dataMesAnterior.add(Calendar.MONTH, -1);
		String dataS = new SimpleDateFormat("yyyy-MM").format(dataMesAnterior.getTime());
		String hql = "select o from EstoqueAlmoxarifado o where o.bloqueado = false and "+
					"((to_char(o.dataValidade, 'yyyy-MM') = '"+dataS+"' and to_char(now(), 'dd') < '07' ) ) or to_char(o.dataValidade, 'yyyy-MM') = to_char(now(), 'yyyy-MM')) " + 
					"order by to_ascii(o.materialAmoxarifado.descricao)";
		List<EstoqueAlmoxarifado> list = new ArrayList<EstoqueAlmoxarifado>(new ConsultaGeral<EstoqueAlmoxarifado>().consulta(new StringBuilder(hql), null));
		Collections.sort(list, new EstoqueAlmoxarifadoDataVencimentoComparador());
		return list;
	}
	
	//TODO TAF 5
	public List<EstoqueAlmoxarifado> consultarEstoqueVencidoEVenceraSeisMeses(){
		
		Calendar hoje = Calendar.getInstance();
		hoje.set(Calendar.MONTH, hoje.get(Calendar.MONTH) + 6);
		String seisMeses = new SimpleDateFormat("yyyy-MM-dd").format(hoje.getTime());
			
		String hql =  "SELECT o "
					+ "FROM EstoqueAlmoxarifado o "
					+ "WHERE o.bloqueado = false "
					+ 	"AND (date_trunc('month',o.dataValidade) BETWEEN date_trunc('day',CURRENT_DATE) AND date_trunc('day',cast('" +seisMeses+ "'as date)) "
						+   "OR date_trunc('day',o.dataValidade) < date_trunc('day',CURRENT_DATE)) "
					+   "AND o.quantidadeAtual>0 "
					+ "ORDER BY o.dataValidade asc ";
				
		List<EstoqueAlmoxarifado> list = new ArrayList<EstoqueAlmoxarifado>(new ConsultaGeral<EstoqueAlmoxarifado>().consulta(new StringBuilder(hql), null));
		
		return list;
	}
	
	public List<EstoqueAlmoxarifado> consultarEstoqueValido() {
		String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		String hql = "select o from EstoqueAlmoxarifado o where o.bloqueado = false and (cast(o.dataValidade as date) >= cast('"+dataS+"' as date) or o.dataValidade is null) order by lower(to_ascii(o.materialAlmoxarifado.descricao))";
		List<EstoqueAlmoxarifado> list = new ArrayList<EstoqueAlmoxarifado>(new ConsultaGeral<EstoqueAlmoxarifado>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public EstoqueAlmoxarifado consultarEstoqueLivre(String lote) {
		String dataS = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
		String hql = "select o from EstoqueAlmoxarifado o where o.bloqueado = false and (cast(o.dataValidade as date) >= cast('"+dataS+"' as date) or o.dataValidade is null) and o.lote = '"+lote+"'";
		EstoqueAlmoxarifado estoque = (EstoqueAlmoxarifado) new ConsultaGeral<EstoqueAlmoxarifado>().consultaUnica(new StringBuilder(hql), null);
		return estoque;
	}
	
	public List<EstoqueAlmoxarifado> consultarEstoqueVencido() {
		String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		String hql = "select o from EstoqueAlmoxarifado o where o.bloqueado = false and cast(o.dataValidade as date) <= cast('"+dataS+"' as date) order by o.dataValidade, to_ascii(lower(o.material.descricao))";
		List<EstoqueAlmoxarifado> list = new ArrayList<EstoqueAlmoxarifado>(new ConsultaGeral<EstoqueAlmoxarifado>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public int consultarQuantidadeEstoque(EstoqueAlmoxarifado estoque) {
		String hql = "select o.quantidadeAtual from EstoqueAlmoxarifado o where o.idEstoqueAlmoxarifado = "+estoque.getIdEstoqueAlmoxarifado();
		Integer quantidade = new ConsultaGeral<Integer>().consultaUnica(new StringBuilder(hql), null);
		return quantidade;
	}
	
	public EstoqueAlmoxarifado consultaEstoque(int id){
		return new ConsultaGeral<EstoqueAlmoxarifado>(new StringBuilder("select o from EstoqueAlmoxarifado o where o.idEstoqueAlmoxarifado = "+id)).consultaUnica();
	}
	
}
