package br.com.Imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class PacienteSusAutoComplete extends ConsultaGeral<Material> {
	
	public Collection<Material> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Paciente o where ");
		stringB.append("lower(to_ascii(o.nome)) like lower(to_ascii('%"+string+"%')) or ");
		stringB.append("o.numeroSus like '"+string+"%'");
		return super.consulta(stringB, null);
	}
	
}
