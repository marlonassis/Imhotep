package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Unidade;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class UnidadeAutoComplete extends ConsultaGeral<Unidade> {
	
	public Collection<Unidade> autoComplete(String string){
		if(string != null){
			string = string.trim();
		}
		StringBuilder stringB = new StringBuilder("select o from Unidade as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%')) or lower(o.sigla) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
