package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.CuidadosPaciente;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="cuidadosPacienteConsulta")
@SessionScoped
public class CuidadosPacienteConsulta extends PadraoConsulta<CuidadosPaciente> {
	public CuidadosPacienteConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<CuidadosPaciente> getList() {
		setConsultaGeral(new ConsultaGeral<CuidadosPaciente>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from CuidadosPaciente o where 1=1"));
		return super.getList();
	}
}
