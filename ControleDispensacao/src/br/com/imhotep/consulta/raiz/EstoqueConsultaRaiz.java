package br.com.imhotep.consulta.raiz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.comparador.EstoqueDataVencimentoComparador;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EstoqueConsultaRaiz  extends ConsultaGeral<Estoque>{
	
	public List<Estoque> consultarEstoquesMaterial(Material material) {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		String hql = "select o from Estoque o where o.quantidadeAtual > 0 and o.bloqueado = false and to_char(o.dataValidade, 'yyyy-MM') >= '"+dataS+"' and o.material.idMaterial = "+material.getIdMaterial()+" order by o.dataValidade asc, to_ascii(lower(o.lote))";
		List<Estoque> list = new ArrayList<Estoque>(new ConsultaGeral<Estoque>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public Estoque consultarEstoqueLoteCodigoBarras(String codigo) {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		StringBuilder stringB = new StringBuilder("select o from Estoque o where ");
		stringB.append("o.bloqueado = false and to_char(o.dataValidade, 'yyyy-MM') >= '");
		stringB.append(dataS);
		stringB.append("' and (lower(o.lote) = lower('");
		stringB.append(codigo);
		stringB.append("') or codigoBarras = '");
		stringB.append(codigo);
		stringB.append("') order by o.dataValidade asc, to_ascii(lower(o.lote))");
		Estoque estoque = new ConsultaGeral<Estoque>().consultaUnica(stringB, null);
		return estoque;
	}
	
	public List<Estoque> consultarEstoqueVencidoLimiteSeteDias() {
		Calendar dataMesAnterior = Calendar.getInstance();
		dataMesAnterior.add(Calendar.MONTH, -1);
		String dataS = new SimpleDateFormat("yyyy-MM").format(dataMesAnterior.getTime());
		String hql = "select o from Estoque o where o.bloqueado = false and "+
					"((to_char(o.dataValidade, 'yyyy-MM') = '"+dataS+"' and to_char(now(), 'dd') < '07' ) ) or to_char(o.dataValidade, 'yyyy-MM') = to_char(now(), 'yyyy-MM')) " + 
					"order by to_ascii(o.material.descricao)";
		List<Estoque> list = new ArrayList<Estoque>(new ConsultaGeral<Estoque>().consulta(new StringBuilder(hql), null));
		Collections.sort(list, new EstoqueDataVencimentoComparador());
		return list;
	}
	
	public List<Estoque> consultarEstoqueValido() {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		String hql = "select o from Estoque o where o.bloqueado = false and to_char(o.dataValidade, 'yyyy-MM') >= '"+dataS+"'";
		List<Estoque> list = new ArrayList<Estoque>(new ConsultaGeral<Estoque>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public Estoque consultarEstoqueLivre(String lote) {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		String hql = "select o from Estoque o where o.bloqueado = false and to_char(o.dataValidade, 'yyyy-MM') >= '"+dataS+"' and o.lote = '"+lote+"'";
		Estoque estoque = (Estoque) new ConsultaGeral<Estoque>().consultaUnica(new StringBuilder(hql), null);
		return estoque;
	}
	
	public List<Estoque> consultarEstoqueVencido() {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		String hql = "select o from Estoque o where o.bloqueado = false and (to_char(o.dataValidade, 'yyyy-MM') < '"+dataS+"' or to_char(o.dataValidade, 'yyyy-MM') = '"+dataS+"') order by o.dataValidade, to_ascii(lower(o.material.descricao))";
		List<Estoque> list = new ArrayList<Estoque>(new ConsultaGeral<Estoque>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public int consultarQuantidadeEstoque(Estoque estoque) {
		String hql = "select o.quantidadeAtual from Estoque o where o.idEstoque = "+estoque.getIdEstoque();
		Integer quantidade = new ConsultaGeral<Integer>().consultaUnica(new StringBuilder(hql), null);
		return quantidade;
	}
	
	public Estoque consultaEstoque(int id){
		return new ConsultaGeral<Estoque>(new StringBuilder("select o from Estoque o where o.idEstoque = "+id)).consultaUnica();
	}
}
