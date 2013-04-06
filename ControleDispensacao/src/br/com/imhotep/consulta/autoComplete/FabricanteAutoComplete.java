package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Fabricante;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="fabricanteAutoComplete")
@RequestScoped
public class FabricanteAutoComplete extends ConsultaGeral<Fabricante> {
	
	public Collection<Fabricante> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Fabricante o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%')) ");
		return super.consulta(stringB, null);
	}
	
}
