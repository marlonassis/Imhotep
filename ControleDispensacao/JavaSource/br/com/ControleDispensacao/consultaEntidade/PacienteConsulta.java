package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Paciente;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="pacienteConsulta")
@SessionScoped
public class PacienteConsulta extends PadraoConsulta<Paciente> {
	public PacienteConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("o.nome");
	}
	
	@Override
	public List<Paciente> getList() {
		setConsultaGeral(new ConsultaGeral<Paciente>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Paciente o where 1=1"));
		return super.getList();
	}
}
