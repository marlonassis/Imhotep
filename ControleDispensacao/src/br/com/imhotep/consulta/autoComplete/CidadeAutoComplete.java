package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Cidade;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="cidadeAutoComplete")
@RequestScoped
public class CidadeAutoComplete extends ConsultaGeral<Cidade> {
	
	public Collection<Cidade> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Cidade o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
