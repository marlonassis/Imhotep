package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Hospital;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class HospitalAutoComplete extends ConsultaGeral<Hospital> {
	
	public Collection<Hospital> autoComplete(String string){
		string = string.trim();
		StringBuilder stringB = new StringBuilder("select o from Hospital as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%')) order by lower(to_ascii(o.nome))");
		return super.consulta(stringB, null);
	}
	
}
