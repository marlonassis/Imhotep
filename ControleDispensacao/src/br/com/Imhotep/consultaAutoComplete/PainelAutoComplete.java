package br.com.Imhotep.consultaAutoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Painel;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="painelAutoComplete")
@RequestScoped
public class PainelAutoComplete extends ConsultaGeral<Painel> {
	
	public Collection<Painel> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Painel as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
