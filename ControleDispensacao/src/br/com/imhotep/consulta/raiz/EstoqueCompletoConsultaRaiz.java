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
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		
		StringBuilder stringB = new StringBuilder("select o from Estoque o where o.quantidadeAtual > 0");
		stringB.append(" and to_char(o.dataValidade, 'yyyy-MM') >= '"+dataS+"'");
		stringB.append(" and o.bloqueado = false");
		stringB.append(" order by lower(to_ascii(o.material.descricao))");
		List<Estoque> list = new ArrayList<Estoque>(new ConsultaGeral<Estoque>().consulta(stringB, null));
		return list;
	}
		
}
