package br.com.imhotep.consulta.raiz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Estoque;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EstoqueCompletoConsultaRaiz  extends ConsultaGeral<Estoque>{

	public List<Estoque> consultar() {
		String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		
		StringBuilder stringB = new StringBuilder("select o from Estoque o where o.quantidadeAtual > 0");
		stringB.append(" and o.dataValidade >= cast('"+dataS+"' as date)");
		stringB.append(" and o.bloqueado = false");
		stringB.append(" order by lower(to_ascii(o.material.descricao))");
		List<Estoque> list = new ArrayList<Estoque>(new ConsultaGeral<Estoque>().consulta(stringB, null));
		return list;
	}
	
	public List<Estoque> consultarInclusiveEstoqueZerado() {
		String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		
		StringBuilder stringB = new StringBuilder("select o from Estoque o where ");
		stringB.append(" o.dataValidade >= cast('"+dataS+"' as date)");
		stringB.append(" and o.bloqueado = false");
		stringB.append(" order by lower(to_ascii(o.material.descricao))");
		List<Estoque> list = new ArrayList<Estoque>(new ConsultaGeral<Estoque>().consulta(stringB, null));
		return list;
	}
		
}
