package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Painel;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class PainelAutoComplete extends ConsultaGeral<Painel> {
	
	public Collection<Painel> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Painel as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
