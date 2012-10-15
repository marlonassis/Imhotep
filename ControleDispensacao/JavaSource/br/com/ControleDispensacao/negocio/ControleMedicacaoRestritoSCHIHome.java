package br.com.ControleDispensacao.negocio;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.ControleMedicacaoRestritoSCHI;
import br.com.ControleDispensacao.enums.TipoBooleanEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="controleMedicacaoRestritoSCHIHome")
@SessionScoped
public class ControleMedicacaoRestritoSCHIHome extends PadraoHome<ControleMedicacaoRestritoSCHI>{

	private TipoBooleanEnum prescricaoAdequada = TipoBooleanEnum.T;
	
	public ControleMedicacaoRestritoSCHIHome(){
	}
	
	public ControleMedicacaoRestritoSCHIHome(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI) {
		setInstancia(controleMedicacaoRestritoSCHI);
	}
	
	@Override
	public void setInstancia(ControleMedicacaoRestritoSCHI instancia) {
		super.setInstancia(instancia);
	}

	@Override
	public boolean enviar() {
		getInstancia().setDataCriacaoAssistente(new Date());
		return super.enviar();
	}
	
	@Override
	public boolean atualizar() {
		insereInformações();
		verificaPrescricaoAdequada();
		return super.atualizar();
	}

	private void verificaPrescricaoAdequada() {
		if(getPrescricaoAdequada().equals(TipoBooleanEnum.T)){
			getInstancia().setTipoPrescricaoInadequada(null);
		}
	}

	private void insereInformações() {
		try {
			getInstancia().setProfissionalInfectologista(Autenticador.getInstancia().getProfissionalAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o profissional atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em ControleMedicacaoRestritoSCHIHome");
		}
		
		getInstancia().setDataLiberacaoInfectologista(new Date());
		Calendar dataLimite = Calendar.getInstance();
		dataLimite.setTime(getInstancia().getDataLiberacaoInfectologista());
		dataLimite.add(getInstancia().getTempoUso(), Calendar.DAY_OF_MONTH);
		getInstancia().setDataLimite(dataLimite.getTime());
	}

	public String getMaterialLiberacao(){
		return materialLiberacao(getInstancia().getIdControleMedicacaoRestritoSCHI());
	}
	
	public String materialLiberacao(int idControleMedicacaoRestritoSCHI){
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idControleMedicacaoRestritoSCHI", idControleMedicacaoRestritoSCHI);
		String material = new ConsultaGeral<String>(new StringBuilder("select o.material.descricao from PrescricaoItem o where o.controleMedicacaoRestritoSCHI.idControleMedicacaoRestritoSCHI = :idControleMedicacaoRestritoSCHI"), hm).consultaUnica();
		return material;
	}
	
	public TipoBooleanEnum getPrescricaoAdequada() {
		return prescricaoAdequada;
	}

	public void setPrescricaoAdequada(TipoBooleanEnum prescricaoAdequada) {
		this.prescricaoAdequada = prescricaoAdequada;
	}

}
