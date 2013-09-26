package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Usuario;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class UsuarioAutoComplete extends ConsultaGeral<Usuario> {
	
	public Collection<Usuario> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Usuario as o where lower(to_ascii(o.login)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
