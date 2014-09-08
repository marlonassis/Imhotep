package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.EhealthPais;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EhealthPaisAutoComplete extends ConsultaGeral<EhealthPais> {
	
	public Collection<EhealthPais> autoComplete(String string){
		string = string.trim();
		StringBuilder stringB = new StringBuilder("select o from EhealthPais as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%')) or lower(to_ascii(o.sigla)) like lower(to_ascii('%"+string+"%')) ");
		return super.consulta(stringB, null);
	}
	
}
