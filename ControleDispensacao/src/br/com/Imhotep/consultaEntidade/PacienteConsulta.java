package br.com.Imhotep.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Paciente;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="pacienteConsulta")
@SessionScoped
public class PacienteConsulta extends PadraoConsulta<Paciente> {
	public PacienteConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.cpf", IGUAL);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<Paciente> getList() {
		setConsultaGeral(new ConsultaGeral<Paciente>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Paciente o where 1=1"));
		return super.getList();
	}
}
