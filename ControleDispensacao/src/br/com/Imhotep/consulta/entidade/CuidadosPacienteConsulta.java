package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.CuidadosPaciente;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="cuidadosPacienteConsulta")
@SessionScoped
public class CuidadosPacienteConsulta extends PadraoConsulta<CuidadosPaciente> {
	public CuidadosPacienteConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(lower(o.descricao))");
	}
	
	@Override
	public List<CuidadosPaciente> getList() {
		setConsultaGeral(new ConsultaGeral<CuidadosPaciente>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from CuidadosPaciente o where 1=1"));
		return super.getList();
	}
}
