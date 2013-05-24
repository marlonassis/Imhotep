package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Cid;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class CidAutoComplete extends ConsultaGeral<Cid> {
	
	public Collection<Cid> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Cid o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%')) " +
				"or lower(to_ascii(o.codigo)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
