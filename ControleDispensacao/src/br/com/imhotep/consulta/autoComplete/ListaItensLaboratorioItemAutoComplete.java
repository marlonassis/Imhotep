package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class ListaItensLaboratorioItemAutoComplete extends ConsultaGeral<String> {
	
	public Collection<String> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select distinct o.listaItens from LaboratorioExameAnaliseItem o where "
				+ "lower(to_ascii(o.listaItens)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
