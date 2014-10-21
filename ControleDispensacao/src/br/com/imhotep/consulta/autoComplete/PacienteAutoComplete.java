package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Paciente;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class PacienteAutoComplete extends ConsultaGeral<Paciente> {
	
	public Collection<Paciente> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Paciente as o where ");
		stringB.append("lower(to_ascii(o.cpf)) like lower(to_ascii('%"+string+"%')) or ");
		stringB.append("lower(to_ascii(o.numeroSus)) like lower(to_ascii('%"+string+"%')) or ");
		stringB.append("lower(to_ascii(o.prontuario)) like lower(to_ascii('%"+string+"%')) ");
		stringB.append("order by lower(to_ascii(o.nome))");
		return super.consulta(stringB, null);
	}
	
}
