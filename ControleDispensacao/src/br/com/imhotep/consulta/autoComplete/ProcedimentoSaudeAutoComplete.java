package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.ProcedimentoSaude;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class ProcedimentoSaudeAutoComplete extends ConsultaGeral<ProcedimentoSaude> {
	
	public Collection<ProcedimentoSaude> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from ProcedimentoSaude as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%')) " +
				" or lower(to_ascii(o.codigoProcedimento)) like lower(to_ascii('%"+string+"%')) "+
				"order by to_ascii(lower(o.nome))");
		return super.consulta(stringB, null);
	}
	
}
