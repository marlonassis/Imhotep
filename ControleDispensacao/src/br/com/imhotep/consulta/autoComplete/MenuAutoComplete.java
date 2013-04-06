package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Menu;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="menuAutoComplete")
@RequestScoped
public class MenuAutoComplete extends ConsultaGeral<Menu> {
	
	public Collection<Menu> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Menu as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%')) order by to_ascii(o.descricao)");
		return super.consulta(stringB, null);
	}
	
}
