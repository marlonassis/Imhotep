package br.com.Imhotep.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Unidade;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="unidadeAutoComplete")
@RequestScoped
public class UnidadeAutoComplete extends ConsultaGeral<Unidade> {
	
	public Collection<Unidade> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Unidade as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%')) or lower(o.sigla) like lower('%"+string+"%')");
		return super.consulta(stringB, null);
	}
	
}
