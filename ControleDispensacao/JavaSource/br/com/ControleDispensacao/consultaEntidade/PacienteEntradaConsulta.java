package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.PacienteEntrada;
import br.com.ControleDispensacao.negocio.PacienteEntradaHome;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="pacienteEntradaConsulta")
@SessionScoped
public class PacienteEntradaConsulta extends PadraoConsulta<PacienteEntrada> {
	public PacienteEntradaConsulta(){
		getCamposConsulta().put("o.dataEntrada", IGUAL);
		getCamposConsulta().put("o.unidadeAlocacao", IGUAL);
		setOrderBy("o.dataEntrada desc");
	}
	
	@Override
	public List<PacienteEntrada> getList() {
		setConsultaGeral(new ConsultaGeral<PacienteEntrada>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from PacienteEntrada o where o.paciente.idPaciente = "+PacienteEntradaHome.getInstanciaHome().idPacienteAtual()));
		return super.getList();
	}
}
