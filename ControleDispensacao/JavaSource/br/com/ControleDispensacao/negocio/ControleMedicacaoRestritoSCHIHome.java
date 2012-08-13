package br.com.ControleDispensacao.negocio;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.ControleMedicacaoRestritoSCHI;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.enums.TipoBooleanEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="controleMedicacaoRestritoSCHIHome")
@SessionScoped
public class ControleMedicacaoRestritoSCHIHome extends PadraoHome<ControleMedicacaoRestritoSCHI>{

	private TipoBooleanEnum prescricaoAdequada = TipoBooleanEnum.T;
	private Prescricao prescricao = new Prescricao();
	
	public ControleMedicacaoRestritoSCHIHome(){
	}
	
	public ControleMedicacaoRestritoSCHIHome(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI) {
		setInstancia(controleMedicacaoRestritoSCHI);
	}
	
	@Override
	public void setInstancia(ControleMedicacaoRestritoSCHI instancia) {
		carregaPrescricao(instancia.getIdControleMedicacaoRestritoSCHI());
		super.setInstancia(instancia);
	}

	private void carregaPrescricao(int idControle) {
		String sql = "select o.prescricao from PrescricaoItem o where o.controleMedicacaoRestritoSCHI.idControleMedicacaoRestritoSCHI = :idControle order by o.prescricao.dataPrescricao desc";
		ConsultaGeral<Prescricao> cg = new ConsultaGeral<Prescricao>();
		cg.getAddValorConsulta().put("idControle", idControle);
		setPrescricao(cg.consultaUnica(new StringBuilder(sql)));
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
		getInstancia().setProfissionalInfectologista(Autenticador.getInstancia().getProfissionalAtual());
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

	public Prescricao getPrescricao() {
		return prescricao;
	}

	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}
}
