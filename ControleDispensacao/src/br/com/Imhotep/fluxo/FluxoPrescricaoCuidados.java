package br.com.Imhotep.fluxo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.CuidadosPaciente;
import br.com.Imhotep.entidade.CuidadosPrescricao;
import br.com.Imhotep.entidade.Prescricao;
import br.com.Imhotep.raiz.CuidadosPrescricaoRaiz;
import br.com.Imhotep.raiz.PrescricaoRaiz;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoFluxo;

@ManagedBean(name="fluxoPrescricaoCuidados")
@RequestScoped
public class FluxoPrescricaoCuidados extends PadraoFluxo{
	
	private Prescricao prescricaoAtual = PrescricaoRaiz.getInstanciaHome().getPrescricaoAtual();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List consultaCuidados(String sql){
		return new ArrayList(new ConsultaGeral().consulta(new StringBuilder(sql), null));
	}
	
	@SuppressWarnings("unchecked")
	private List<CuidadosPaciente> consultaTipoCuidado(String tipoCuidado, Integer idPrescricao) {
		String sql = "select a from CuidadosPaciente a"+ 
				" left join a.cuidadosPrescricaoList cpl with cpl.prescricao.idPrescricao = "+ idPrescricao+
				" where cpl.cuidadosPaciente.idCuidadosPaciente is null and a.tipoCuidadosPaciente = '"+tipoCuidado+"'";
		return consultaCuidados(sql);
	}

	@SuppressWarnings("unchecked")
	public List<CuidadosPrescricao> getCuidadosEscolhidos(){
		String sql = "select a from CuidadosPrescricao a where a.prescricao.idPrescricao = "+prescricaoAtual.getIdPrescricao();
		return consultaCuidados(sql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CuidadosPrescricao> getCuidadosEscolhidosVisualizacao(Prescricao prescricaoInformada){
		String sql = "select a from CuidadosPrescricao a where a.prescricao.idPrescricao = "+prescricaoInformada.getIdPrescricao();
		return consultaCuidados(sql);
	}
	
	public List<CuidadosPaciente> listaPrescricaoCuidados(String tipoCuidado){
		return consultaTipoCuidado(tipoCuidado, prescricaoAtual.getIdPrescricao());
	}
	
	public void insereOutrosCuidados(CuidadosPrescricao cuidadosPrescricao){
		cuidadosPrescricao.setPrescricao(prescricaoAtual);
		new CuidadosPrescricaoRaiz(cuidadosPrescricao).enviar();
	}
	
	public void apagarCuidadosPrescricao(CuidadosPrescricao cuidadosPrescricao){
		new CuidadosPrescricaoRaiz().apagar(cuidadosPrescricao);
	}
	
}
