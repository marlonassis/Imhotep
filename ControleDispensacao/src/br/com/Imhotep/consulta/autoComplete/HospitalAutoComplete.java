package br.com.Imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Hospital;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="hospitalAutoComplete")
@RequestScoped
public class HospitalAutoComplete extends ConsultaGeral<Hospital> {
	
	public Collection<Hospital> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Hospital as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
