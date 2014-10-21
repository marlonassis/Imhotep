package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Recursos;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class RecursoAutoComplete extends ConsultaGeral<Recursos> {
	
	public Collection<Recursos> autoComplete(String string){
		StringBuilder stringB = new StringBuilder(
				"select o from Recursos o where " +
				"lower(to_ascii(o.codigoIdentificacao)) like lower(to_ascii('%"+string+"%')) or " +
				"lower(to_ascii(o.codigoInventario)) like lower(to_ascii('%"+string+"%')) or " +
				"lower(to_ascii(o.numeroSerie)) like lower(to_ascii('%"+string+"%')) ");
		return super.consulta(stringB, null);
	}
	
}
