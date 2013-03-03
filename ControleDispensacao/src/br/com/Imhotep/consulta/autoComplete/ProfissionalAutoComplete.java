package br.com.Imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Profissional;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="profissionalAutoComplete")
@RequestScoped
public class ProfissionalAutoComplete extends ConsultaGeral<Profissional> {
	
	public Collection<Profissional> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Profissional as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}