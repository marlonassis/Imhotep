package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.SituacaoPaciente;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="situacaoPacienteConsulta")
@SessionScoped
public class SituacaoPacienteConsulta extends PadraoConsulta<SituacaoPaciente> {
	public SituacaoPacienteConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<SituacaoPaciente> getList() {
		setConsultaGeral(new ConsultaGeral<SituacaoPaciente>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SituacaoPaciente o where 1=1"));
		return super.getList();
	}
}
