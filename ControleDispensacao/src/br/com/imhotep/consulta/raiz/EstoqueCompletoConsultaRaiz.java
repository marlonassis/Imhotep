package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Estoque;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EstoqueCompletoConsultaRaiz  extends ConsultaGeral<Estoque>{

	public List<Estoque> consultar() {
		List<Estoque> list = new ArrayList<Estoque>(new ConsultaGeral<Estoque>().consulta(new StringBuilder("select o from Estoque o where o.quantidadeAtual > 0 order by o.material.descricao"), null));
		return list;
	}
	
	
}
