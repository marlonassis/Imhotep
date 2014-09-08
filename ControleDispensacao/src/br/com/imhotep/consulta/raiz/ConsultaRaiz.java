package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.remendo.ConsultaGeral;
import br.com.remendo.utilidades.Utilitarios;

@ManagedBean
@RequestScoped
public class ConsultaRaiz{
	
	private String montarOrderBy(String... attrs){
		String res = "";
		for(String att : attrs){
			res = att + ", ";
		}
		res = res.substring(0, res.lastIndexOf(","));
		return res;
	}
	
	public List<Object> listaGeral(String idComponente, String attrs) {
		String orderBy = construirOB(attrs);
		String nomeClasse = new Utilitarios().nomeClasse(idComponente);
		StringBuilder sb = new StringBuilder("select o from "+nomeClasse+" o" + (orderBy.isEmpty() ? "" : " order by lower("+orderBy+")"));
		return new ArrayList<Object>(new ConsultaGeral<Object>().consulta(sb, null));
	}

	private String construirOB(String attrs) {
		String orderBy = "";
		if(attrs != null)
			orderBy = montarOrderBy(attrs.split(" "));
		return orderBy;
	} 
	
}
