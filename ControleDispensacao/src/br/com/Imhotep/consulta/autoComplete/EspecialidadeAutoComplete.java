package br.com.Imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Especialidade;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EspecialidadeAutoComplete extends ConsultaGeral<Especialidade> {
	
	public Collection<Especialidade> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Especialidade as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%')) order by to_ascii(lower(o.descricao))");
		return super.consulta(stringB, null);
	}
	
}
