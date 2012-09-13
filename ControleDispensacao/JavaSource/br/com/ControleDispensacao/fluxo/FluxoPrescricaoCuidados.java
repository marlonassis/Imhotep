package br.com.ControleDispensacao.fluxo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.ControleDispensacao.entidade.CuidadosPaciente;
import br.com.ControleDispensacao.entidade.CuidadosPrescricao;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.negocio.CuidadosPrescricaoHome;
import br.com.ControleDispensacao.negocio.PrescricaoHome;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoFluxo;

@ManagedBean(name="fluxoPrescricaoCuidados")
@RequestScoped
public class FluxoPrescricaoCuidados extends PadraoFluxo{
	
	private Prescricao prescricaoAtual = PrescricaoHome.getInstanciaHome().getPrescricaoAtual();
	private CuidadosPrescricao cuidadosPrescricao = new CuidadosPrescricao();
	
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
		String sql = "select a from CuidadosPrescricao a where a.prescricao.idPrescricao = "+prescricaoAtual.getIdPrescricao();
		return consultaCuidados(sql);
	}
	
	public List<CuidadosPaciente> listaPrescricaoCuidados(String tipoCuidado){
		return consultaTipoCuidado(tipoCuidado, prescricaoAtual.getIdPrescricao());
	}
	
	public void insereOutrosCuidados(){
		getCuidadosPrescricao().setPrescricao(prescricaoAtual);
		new CuidadosPrescricaoHome(getCuidadosPrescricao()).enviar();
		setCuidadosPrescricao(new CuidadosPrescricao());
	}
	
	public void apagarCuidadosPrescricao(CuidadosPrescricao cuidadosPrescricao){
		new CuidadosPrescricaoHome().apagar(cuidadosPrescricao);
	}
	
	public CuidadosPrescricao getCuidadosPrescricao() {
		return cuidadosPrescricao;
	}

	public void setCuidadosPrescricao(CuidadosPrescricao cuidadosPrescricao) {
		this.cuidadosPrescricao = cuidadosPrescricao;
	}
	
}
