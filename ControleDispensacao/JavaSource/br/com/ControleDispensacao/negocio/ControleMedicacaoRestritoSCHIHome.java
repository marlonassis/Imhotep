package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.ControleMedicacaoRestritoSCHI;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.entidade.PrescricaoItemDose;
import br.com.ControleDispensacao.entidadeExtra.Dose;
import br.com.ControleDispensacao.enums.TipoBooleanEnum;
import br.com.ControleDispensacao.fluxo.FluxoPrescricaoMedicamento;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="controleMedicacaoRestritoSCHIHome")
@SessionScoped
public class ControleMedicacaoRestritoSCHIHome extends PadraoHome<ControleMedicacaoRestritoSCHI>{

	private TipoBooleanEnum prescricaoAdequada = TipoBooleanEnum.T;
	private List<PrescricaoItem> itensLiberacao = new ArrayList<PrescricaoItem>();
	private PrescricaoItem itemEdicao = new PrescricaoItem();
	private Dose dose = new Dose();
	private List<PrescricaoItemDose> prescricaoItemDoseList;
	
	
	public ControleMedicacaoRestritoSCHIHome(){
	}
	
	public void apagarItemPrescricao(){
		if(new PrescricaoItemHome(getItemEdicao(), false).apagar()){
			carregarMedicamentosPendentes(getInstancia().getIdControleMedicacaoRestritoSCHI());
		}
	}
	
	public void adicionaPrescricaoItem(){
		Prescricao prescricao = getInstancia().getPrescricaoItem().getPrescricao();
		if(new PrescricaoHome().adicionarItemFarmacoPrescricaoControleSCHI(prescricao, getDose())){
			setDose(new Dose());
		}
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
		insereInformacoes();
		verificaPrescricaoAdequada();
		return super.atualizar();
	}

	public boolean anexar() {
		insereInformacoes();
		verificaPrescricaoAdequada();
		return super.atualizar();
	}
	
	private void verificaPrescricaoAdequada() {
		if(getPrescricaoAdequada().equals(TipoBooleanEnum.T)){
			getInstancia().setTipoPrescricaoInadequada(null);
		}
	}

	public Long quantidadeDosesPrescricaoItem(PrescricaoItem prescricaoItem){
		return new FluxoPrescricaoMedicamento().quantidadeDosesPrescricaoItem(prescricaoItem);
	}
	
	public void setCarregarPrescricaoItemEdicao(PrescricaoItem prescricaoItem){
		setItemEdicao(prescricaoItem);
		FluxoPrescricaoMedicamento fpm = new FluxoPrescricaoMedicamento();
		setPrescricaoItemDoseList(fpm.getPrescricaoItemDoseSCHIList(getItemEdicao()));
		getDose().setPrescricaoItem(prescricaoItem);
	}
	
	public void setCarregarInstancia(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI){
		setInstancia(controleMedicacaoRestritoSCHI);
		carregarMedicamentosPendentes(controleMedicacaoRestritoSCHI.getIdControleMedicacaoRestritoSCHI());
	}
	
	private void carregarMedicamentosPendentes(int idControleMedicacaoRestritoSCHI){
		ConsultaGeral<PrescricaoItem> cg = new ConsultaGeral<PrescricaoItem>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idControleMedicacaoRestritoSCHI", idControleMedicacaoRestritoSCHI);
		String hql = "select o from PrescricaoItem o where o.controleMedicacaoRestritoSCHI.idControleMedicacaoRestritoSCHI = :idControleMedicacaoRestritoSCHI";
		itensLiberacao = new ArrayList<PrescricaoItem>(cg.consulta(new StringBuilder(hql), hm));
	}
	
	private void insereInformacoes() {
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
		dataLimite.add(Calendar.DAY_OF_MONTH, getInstancia().getTempoUso());
		getInstancia().setDataLimite(dataLimite.getTime());
	}

	public TipoBooleanEnum getPrescricaoAdequada() {
		return prescricaoAdequada;
	}

	public void setPrescricaoAdequada(TipoBooleanEnum prescricaoAdequada) {
		this.prescricaoAdequada = prescricaoAdequada;
	}

	public List<PrescricaoItem> getItensLiberacao() {
		return itensLiberacao;
	}

	public void setItensLiberacao(List<PrescricaoItem> itensLiberacao) {
		this.itensLiberacao = itensLiberacao;
	}

	public PrescricaoItem getItemEdicao() {
		return itemEdicao;
	}

	public void setItemEdicao(PrescricaoItem itemEdicao) {
		this.itemEdicao = itemEdicao;
	}

	public Dose getDose() {
		return dose;
	}

	public void setDose(Dose dose) {
		this.dose = dose;
	}
	
	public void setPrescricaoItemDoseList(List<PrescricaoItemDose> prescricaoItemDoseList) {
		this.prescricaoItemDoseList = prescricaoItemDoseList;
	}
	
	public List<PrescricaoItemDose> getPrescricaoItemDoseList() {
		return prescricaoItemDoseList;
	}

}
