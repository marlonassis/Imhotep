package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Paciente;
import br.com.imhotep.temp.PadraoConsulta;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@SessionScoped
public class PacienteConsulta extends PadraoConsulta<Paciente> {
	public PacienteConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.cpf", IGUAL);
		getCamposConsulta().put("o.numeroSus", IGUAL);
		getCamposConsulta().put("o.dataNascimento", IGUAL);
		getCamposConsulta().put("o.cidadeNaturalidade", IGUAL);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<Paciente> getList() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<Paciente>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Paciente o where 1=1"));
		return super.getList();
	}
}
