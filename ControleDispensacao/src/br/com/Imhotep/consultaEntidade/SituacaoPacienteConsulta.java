package br.com.Imhotep.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.SituacaoPaciente;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="situacaoPacienteConsulta")
@SessionScoped
public class SituacaoPacienteConsulta extends PadraoConsulta<SituacaoPaciente> {
	public SituacaoPacienteConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<SituacaoPaciente> getList() {
		setConsultaGeral(new ConsultaGeral<SituacaoPaciente>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SituacaoPaciente o where 1=1"));
		return super.getList();
	}
}
