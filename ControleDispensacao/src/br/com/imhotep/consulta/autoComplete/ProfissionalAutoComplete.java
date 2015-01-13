package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Profissional;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class ProfissionalAutoComplete extends ConsultaGeral<Profissional> {
	
	public Collection<Profissional> autoCompleteNomeCPF(String string){
		StringBuilder stringB = new StringBuilder("select o from Profissional as o where "
													+ "lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%')) or "
													+ "lower(to_ascii(o.cpf)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
	public Collection<Profissional> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Profissional as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
