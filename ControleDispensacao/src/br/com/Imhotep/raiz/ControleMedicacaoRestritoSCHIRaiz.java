package br.com.Imhotep.raiz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.ControleMedicacaoRestritoSCHI;
import br.com.Imhotep.entidade.Prescricao;
import br.com.Imhotep.entidade.PrescricaoItem;
import br.com.Imhotep.entidade.PrescricaoItemDose;
import br.com.Imhotep.entidade.extra.Dose;
import br.com.Imhotep.enums.TipoBooleanEnum;
import br.com.Imhotep.fluxo.FluxoPrescricaoMedicamento;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.imhotep.excecoes.ExcecaoControlePrescricaoItem;
import br.com.imhotep.excecoes.ExcecaoControlePrescricaoItemDose;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoFormularioNaoPreenchido;
import br.com.imhotep.excecoes.ExcecaoSaldoInsuficienteEstoque;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="controleMedicacaoRestritoSCHIRaiz")
@SessionScoped
public class ControleMedicacaoRestritoSCHIRaiz extends PadraoHome<ControleMedicacaoRestritoSCHI>{

	private TipoBooleanEnum prescricaoAdequada = TipoBooleanEnum.T;
	private List<PrescricaoItem> itensLiberacao = new ArrayList<PrescricaoItem>();
	private PrescricaoItem itemEdicao = new PrescricaoItem();
	private Dose dose = new Dose();
	private List<PrescricaoItemDose> prescricaoItemDoseList;
	
	
	public ControleMedicacaoRestritoSCHIRaiz(){
	}
	
	public void apagarItemPrescricao(){
		if(new PrescricaoItemRaiz(getItemEdicao(), false).apagar()){
			carregarMedicamentosPendentes(getInstancia());
		}
	}
	
	public void gravaItem(){
		Prescricao prescricao = getInstancia().getPrescricao();
		if(getDose().getPrescricaoItem().getIdPrescricaoItem() == 0){
			try {
				adicionaPrescricaoItem(getDose(), prescricao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				new PrescricaoRaiz().adicionarItemFarmacoPrescricaoDoseControleSCHI(prescricao, getDose());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		carregaDose(getDose().getPrescricaoItem());
		carregarMedicamentosPendentes(getInstancia());
	}
	
	public void adicionaPrescricaoItem(Dose dose, Prescricao prescricao) throws ExcecaoFormularioNaoPreenchido, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoControlePrescricaoItem, ExcecaoControlePrescricaoItemDose{
		dose.getPrescricaoItem().setControleMedicacaoRestritoSCHI(getInstancia());
		new PrescricaoRaiz().adicionarItemFarmacoPrescricaoControleSCHI(prescricao, dose);
		carregaDose(dose.getPrescricaoItem());
	}
	
	public ControleMedicacaoRestritoSCHIRaiz(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI) {
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
	
	public void apagarDose(PrescricaoItemDose dose){
		new PrescricaoRaiz().removePrescricaoItemDose(dose);
		carregaDose(dose.getPrescricaoItem());
	}
	
	public void novaPrescricaoItem(){
		setPrescricaoItemDoseList(new ArrayList<PrescricaoItemDose>());
		setDose(new Dose());
		setItemEdicao(new PrescricaoItem());
	}
	
	public void setCarregarPrescricaoItemEdicao(PrescricaoItem prescricaoItem){
		setDose(new Dose());
		setItemEdicao(prescricaoItem);
		getDose().setPrescricaoItem(prescricaoItem);
		carregaDose(getItemEdicao());
	}

	private void carregaDose(PrescricaoItem prescricaoItem) {
		FluxoPrescricaoMedicamento fpm = new FluxoPrescricaoMedicamento();
		setPrescricaoItemDoseList(fpm.getPrescricaoItemDoseSCHIList(prescricaoItem));
	}
	
	public void setCarregarInstancia(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI){
		setInstancia(controleMedicacaoRestritoSCHI);
		carregarMedicamentosPendentes(controleMedicacaoRestritoSCHI);
	}
	
	private void carregarMedicamentosPendentes(ControleMedicacaoRestritoSCHI cmr){
		ConsultaGeral<PrescricaoItem> cg = new ConsultaGeral<PrescricaoItem>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idControleMedicacaoRestritoSCHI", cmr.getIdControleMedicacaoRestritoSCHI());
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
