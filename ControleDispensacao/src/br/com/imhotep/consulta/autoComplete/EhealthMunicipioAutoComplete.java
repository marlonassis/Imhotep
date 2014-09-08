package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.EhealthMunicipio;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EhealthMunicipioAutoComplete extends ConsultaGeral<EhealthMunicipio> {
	
	public Collection<EhealthMunicipio> autoComplete(String string){
		string = string.toLowerCase();
		StringBuilder stringB = new StringBuilder("select o from EhealthMunicipio as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
