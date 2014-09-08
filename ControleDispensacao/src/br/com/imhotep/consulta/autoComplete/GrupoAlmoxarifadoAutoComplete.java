package br.com.imhotep.consulta.autoComplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.GrupoAlmoxarifado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class GrupoAlmoxarifadoAutoComplete extends ConsultaGeral<GrupoAlmoxarifado> {
	
	public Collection<GrupoAlmoxarifado> autoComplete(String string){
		if(string != null){
			string = string.trim();
			String hql = "select o from GrupoAlmoxarifado o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%'))";
			List<GrupoAlmoxarifado> list = new ArrayList<GrupoAlmoxarifado>(new ConsultaGeral<GrupoAlmoxarifado>().consulta(new StringBuilder(hql), null));
			return list;
		}
		return new ArrayList<GrupoAlmoxarifado>();
	}
	
}
