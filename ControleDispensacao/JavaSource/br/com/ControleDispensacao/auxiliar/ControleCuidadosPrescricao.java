package br.com.ControleDispensacao.auxiliar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.ControleDispensacao.entidade.CuidadosPaciente;
import br.com.ControleDispensacao.entidade.CuidadosPrescricao;
import br.com.ControleDispensacao.negocio.PrescricaoHome;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoControle;

@ManagedBean(name="controleCuidadosPrescricao")
@RequestScoped
public class ControleCuidadosPrescricao extends PadraoControle {
	
	private int getIdPrescricao() {
		return PrescricaoHome.getInstanciaHome().getPrescricaoAtual().getIdPrescricao();
	}
	
	private List consultaCuidados(String sql){
		return new ArrayList(new ConsultaGeral().consulta(new StringBuilder(sql), null));
	}
	
	private List<CuidadosPaciente> consultaTipoCuidado(String tipoCuidado, Integer idPrescricao) {
		String sql = "select a from CuidadosPaciente a"+ 
				" left join a.cuidadosPrescricaoList cpl with cpl.prescricao.idPrescricao = "+ idPrescricao+
				" where cpl.cuidadosPaciente.idCuidadosPaciente is null and a.tipoCuidadosPaciente = '"+tipoCuidado+"'";
		return consultaCuidados(sql);
	}

	public List<CuidadosPrescricao> getCuidadosEscolhidos(){
		String sql = "select a from CuidadosPrescricao a where a.prescricao.idPrescricao = "+getIdPrescricao();
		return consultaCuidados(sql);
	}
	
	public List<CuidadosPaciente> getPrescricaoCuidadosAerosol(){
		String tipoCuidado = "AER";
		return consultaTipoCuidado(tipoCuidado, getIdPrescricao());
	}

	public List<CuidadosPaciente> getPrescricaoCuidadosNutricao(){
		String tipoCuidado = "NUT";
		return consultaTipoCuidado(tipoCuidado, getIdPrescricao());
	}

	public List<CuidadosPaciente> getPrescricaoCuidadosSoroterapia(){
		String tipoCuidado = "SOR";
		return consultaTipoCuidado(tipoCuidado, getIdPrescricao());
	}

	public List<CuidadosPaciente> getPrescricaoCuidadosMedicacoes(){
		String tipoCuidado = "MED";
		return consultaTipoCuidado(tipoCuidado, getIdPrescricao());
	}

	public List<CuidadosPaciente> getPrescricaoCuidadosMedicacoesSC(){
		String tipoCuidado = "MSC";
		return consultaTipoCuidado(tipoCuidado, getIdPrescricao());
	}

	public List<CuidadosPaciente> getPrescricaoCuidadosMedicacoesOrais(){
		String tipoCuidado = "MOR";
		return consultaTipoCuidado(tipoCuidado, getIdPrescricao());
	}

	public List<CuidadosPaciente> getPrescricaoCuidadosMedicacoesTopicas(){
		String tipoCuidado = "MTO";
		return consultaTipoCuidado(tipoCuidado, getIdPrescricao());
	}

	public List<CuidadosPaciente> getPrescricaoCuidadosMedicacoesSOS(){
		String tipoCuidado = "MSO";
		return consultaTipoCuidado(tipoCuidado, getIdPrescricao());
	}

	public List<CuidadosPaciente> getPrescricaoCuidadosEnfermagem(){
		String tipoCuidado = "CEN";
		return consultaTipoCuidado(tipoCuidado, getIdPrescricao());
	}
}
