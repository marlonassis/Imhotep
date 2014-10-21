package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Funcao;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class FuncaoAutoComplete extends ConsultaGeral<Funcao> {
	
	public Collection<Funcao> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Funcao as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
