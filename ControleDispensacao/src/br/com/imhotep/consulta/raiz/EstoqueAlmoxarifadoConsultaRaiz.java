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
import br.com.imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EstoqueAlmoxarifadoConsultaRaiz  extends ConsultaGeral<EstoqueAlmoxarifado>{
	
	public List<EstoqueAlmoxarifado> consultarEstoquesMaterial(Material material) {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		String hql = "select o from EstoqueAlmoxarifado o where o.quantidadeAtual > 0 and o.bloqueado = false and to_char(o.dataValidade, 'yyyy-MM') >= '"+dataS+"' and o.material.idMaterial = "+material.getIdMaterial()+" order by o.dataValidade asc, to_ascii(lower(o.lote))";
		List<EstoqueAlmoxarifado> list = new ArrayList<EstoqueAlmoxarifado>(new ConsultaGeral<EstoqueAlmoxarifado>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public EstoqueAlmoxarifado consultarEstoqueLoteCodigoBarras(String codigo) {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		StringBuilder stringB = new StringBuilder("select o from EstoqueAlmoxarifado o where ");
		stringB.append("o.bloqueado = false and to_char(o.dataValidade, 'yyyy-MM') >= '");
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
					"order by to_ascii(o.material.descricao)";
		List<EstoqueAlmoxarifado> list = new ArrayList<EstoqueAlmoxarifado>(new ConsultaGeral<EstoqueAlmoxarifado>().consulta(new StringBuilder(hql), null));
		Collections.sort(list, new EstoqueAlmoxarifadoDataVencimentoComparador());
		return list;
	}
	
	public List<EstoqueAlmoxarifado> consultarEstoqueValido() {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		String hql = "select o from EstoqueAlmoxarifado o where o.bloqueado = false and to_char(o.dataValidade, 'yyyy-MM') >= '"+dataS+"'";
		List<EstoqueAlmoxarifado> list = new ArrayList<EstoqueAlmoxarifado>(new ConsultaGeral<EstoqueAlmoxarifado>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public EstoqueAlmoxarifado consultarEstoqueLivre(String lote) {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		String hql = "select o from EstoqueAlmoxarifado o where o.bloqueado = false and to_char(o.dataValidade, 'yyyy-MM') >= '"+dataS+"' and o.lote = '"+lote+"'";
		EstoqueAlmoxarifado estoque = (EstoqueAlmoxarifado) new ConsultaGeral<EstoqueAlmoxarifado>().consultaUnica(new StringBuilder(hql), null);
		return estoque;
	}
	
	public List<EstoqueAlmoxarifado> consultarEstoqueVencido() {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		String hql = "select o from EstoqueAlmoxarifado o where o.bloqueado = false and (to_char(o.dataValidade, 'yyyy-MM') < '"+dataS+"' or to_char(o.dataValidade, 'yyyy-MM') = '"+dataS+"') order by o.dataValidade, to_ascii(lower(o.material.descricao))";
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
