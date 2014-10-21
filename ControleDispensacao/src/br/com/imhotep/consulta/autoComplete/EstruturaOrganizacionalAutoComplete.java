package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EstruturaOrganizacionalAutoComplete extends ConsultaGeral<EstruturaOrganizacional> {
	
	public Collection<EstruturaOrganizacional> autoComplete(String string){
		String hql = "select o from EstruturaOrganizacional o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%'))";
		StringBuilder stringB = new StringBuilder(hql);
		return super.consulta(stringB, null);
	}
	
}
