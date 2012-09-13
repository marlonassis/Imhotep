package br.com.ControleDispensacao.negocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.FlowEvent;

import br.com.ControleDispensacao.auxiliar.Constantes;
import br.com.ControleDispensacao.auxiliar.ControleInstancia;
import br.com.ControleDispensacao.auxiliar.ControleMedicacaoRestrito;
import br.com.ControleDispensacao.auxiliar.ControlePrescricao;
import br.com.ControleDispensacao.auxiliar.Parametro;
import br.com.ControleDispensacao.comparador.DoseDataComparador;
import br.com.ControleDispensacao.entidade.ControleMedicacaoRestritoSCHI;
import br.com.ControleDispensacao.entidade.CuidadosPaciente;
import br.com.ControleDispensacao.entidade.CuidadosPrescricao;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.Paciente;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.entidade.PrescricaoItemDose;
import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="prescricaoHome")
@SessionScoped
public class PrescricaoHome extends PadraoHome<Prescricao>{
	
	private boolean skip;
	private Paciente paciente;
	private Prescricao prescricaoAtual = new Prescricao();
	private Prescricao prescricaoBloqueio = new Prescricao();
	
	private PrescricaoItem prescricaoItem = new PrescricaoItem();
	private Date dataInicio;
	private Integer quantidadeDoses;
	private Integer quantidadePorDose;
	private Integer intervaloEntreDoses;
	
	private String fluxoAtualPrescricao = null;
	
	public static PrescricaoHome getInstanciaHome(){
		return new ControleInstancia<PrescricaoHome>().instancia("prescricaoHome");
	}
	
	public List<PrescricaoItem> getItensPrescricao(){
		return itensPrescricao(getPrescricaoAtual());
	}
	
	private List<PrescricaoItem> itensPrescricao(Prescricao prescricao){
		if(prescricao != null){
			ConsultaGeral<PrescricaoItem> cg = new ConsultaGeral<PrescricaoItem>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			return (List<PrescricaoItem>) cg.consulta(new StringBuilder("select o from PrescricaoItem o where o.prescricao.idPrescricao = :idPrescricao"), hm);
		}
		return null;
	}
	
	public void concluirPrescricao(){
		getInstancia().setDispensavel(TipoStatusEnum.S);
		getInstancia().setDataConclusao(new Date());
		if(super.atualizar()){
			novaInstancia();
		}
	}
	
	private List<CuidadosPrescricao> cuidadosEscolhidos(Prescricao prescricao){
		if(prescricao != null){
			ConsultaGeral<CuidadosPrescricao> cg = new ConsultaGeral<CuidadosPrescricao>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			return (List<CuidadosPrescricao>) cg.consulta(new StringBuilder("select o from CuidadosPrescricao o where o.prescricao.idPrescricao = :idPrescricao"), hm);
		}
		return null;
	}
	
	public List<CuidadosPrescricao> cuidadosPacienteEscolhidosPrescricao(){
		return cuidadosEscolhidos(getPrescricaoAtual());
	}
	
	private List<PrescricaoItem> itensLiberadosFimPrescricao(Prescricao prescricao){
		if(prescricao != null){
			ConsultaGeral<PrescricaoItem> cg = new ConsultaGeral<PrescricaoItem>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			
			String sql = "select o from PrescricaoItem o where " +
			"o.prescricao.idPrescricao = :idPrescricao and (( "+
			"exists (select a from LiberaMaterialEspecialidade a where a.material.idMaterial = o.material.idMaterial) " +
			" and profissionalLiberacao is not null) or (lower(o.material.familia.subGrupo.grupo.descricao) = lower('ANTIBIÓTICO') and " +
			"controleMedicacaoRestritoSCHI is not null)" +
			"or (not exists (select a from LiberaMaterialEspecialidade a where a.material.idMaterial = o.material.idMaterial)" +
			" and not lower(o.material.familia.subGrupo.grupo.descricao) = lower('ANTIBIÓTICO'))) ";
			
			return (List<PrescricaoItem>) cg.consulta(new StringBuilder(sql), hm);
		}
		return null;
	}
	
	public List<PrescricaoItem> itensLiberados(){
		return itensLiberadosFimPrescricao(getPrescricaoAtual());
	}
	
	public List<PrescricaoItemDose> getPrescricaoItemDoseList(){
		List<PrescricaoItemDose> pidList = getListaPrescricaoItemDose(getPrescricaoAtual());
		if(pidList != null){
			Collections.sort(pidList, new DoseDataComparador());
			return pidList;
		}else{
			return null;
		}
	}
	
	public void finalizarPrescricao() throws IOException{
		getPrescricaoAtual().setDispensavel(TipoStatusEnum.S);
		getPrescricaoAtual().setDataConclusao(new Date());
		getPrescricaoAtual().setProfissionalConclusao(Autenticador.getInstancia().getProfissionalAtual());
		if(atualizarGenerico(getPrescricaoAtual()) != null){
			setPrescricaoAtual(new Prescricao());
			String paginaAtual = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
			FacesContext.getCurrentInstance().getExternalContext().redirect(paginaAtual);
		}
	}

	private List<PrescricaoItemDose> getListaPrescricaoItemDose(Prescricao prescricao) {
		if(prescricao != null){
			ConsultaGeral<PrescricaoItemDose> cg = new ConsultaGeral<PrescricaoItemDose>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			return (List<PrescricaoItemDose>) cg.consulta(new StringBuilder("select o from PrescricaoItemDose o where o.prescricaoItem.prescricao.idPrescricao = :idPrescricao"), hm);
		}
		return null;
	}
	
	public List<CuidadosPaciente> carregaCuidados(){
		return carregaListaCuidadosLiberados(getPrescricaoAtual());
	}
	
	public List<ControleMedicacaoRestritoSCHI> getControlesValidos(){
		if(getPrescricaoItem() != null && getPrescricaoItem().getMaterial() != null){
			ConsultaGeral<ControleMedicacaoRestritoSCHI> cg = new ConsultaGeral<ControleMedicacaoRestritoSCHI>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("dataLimite", new Date());
			hm.put("idPaciente", getInstancia().getPaciente().getIdPaciente());
			String string = "select o from ControleMedicacaoRestritoSCHI o where o.dataLimite >= :dataLimite and " +
					"o.tipoPrescricaoInadequada is null " +
					"and o.profissionalInfectologista != null " +
					"and o.idControleMedicacaoRestritoSCHI in (select a.controleMedicacaoRestritoSCHI.idControleMedicacaoRestritoSCHI from PrescricaoItem a where a.prescricao.paciente.idPaciente = :idPaciente)";
			return new ArrayList<ControleMedicacaoRestritoSCHI>(cg.consulta(new StringBuilder(string), hm));
		}
		return new ArrayList<ControleMedicacaoRestritoSCHI>();
	}
	
	private List<CuidadosPaciente> carregaListaCuidadosLiberados(Prescricao prescricao){
		if(prescricao != null){
			ConsultaGeral<CuidadosPaciente> cg = new ConsultaGeral<CuidadosPaciente>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			return (List<CuidadosPaciente>) cg.consulta(new StringBuilder("select o from CuidadosPaciente o where o.idCuidadosPaciente not in (select a.cuidadosPaciente.idCuidadosPaciente from CuidadosPrescricao a where a.prescricao.idPrescricao = :idPrescricao)"), hm);
		}
		return null;
	}

	public void adicionarCuidado(CuidadosPaciente cuidadosPaciente){
		new CuidadosPrescricaoHome().enviar(cuidadosPaciente, getPrescricaoAtual());
	}
	
	public void save(ActionEvent actionEvent) {
		FacesMessage msg = new FacesMessage("Successful", "Welcome :" );
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void bloqueiarPrescricao(){
		getPrescricaoBloqueio().setDataBloqueio(new Date());
		getPrescricaoBloqueio().setProfissionalBloqueio(Autenticador.getInstancia().getProfissionalAtual());
		super.atualizarGenerico(getPrescricaoBloqueio());
	}
	
	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	
	public String onFlowProcess(FlowEvent event) {
		setFluxoAtualPrescricao(event.getNewStep());
		
		if(event.getOldStep().equals("pacienteTab")){
			if(!new ControlePrescricao().gravaPrescricao(getPrescricaoAtual())){
				FacesContext.getCurrentInstance().addMessage(null,  new FacesMessage("Erro ao gravar a prescrição", "" ));
			}
		}
		
		if(skip) {
			skip = false;	//reset in case user goes back
			return "confirm";
		}
		else {
			return event.getNewStep();
		}
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Prescricao getPrescricaoAtual() {
		return prescricaoAtual;
	}

	public void setPrescricaoAtual(Prescricao prescricaoAtual) {
		this.prescricaoAtual = prescricaoAtual;
	}
	
	public PrescricaoItem getPrescricaoItem() {
		return prescricaoItem;
	}

	public void setPrescricaoItem(PrescricaoItem prescricaoItem) {
		this.prescricaoItem = prescricaoItem;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Integer getQuantidadeDoses() {
		return quantidadeDoses;
	}

	public void setQuantidadeDoses(Integer quantidadeDoses) {
		this.quantidadeDoses = quantidadeDoses;
	}

	public Integer getQuantidadePorDose() {
		return quantidadePorDose;
	}

	public void setQuantidadePorDose(Integer quantidadePorDose) {
		this.quantidadePorDose = quantidadePorDose;
	}

	public Integer getIntervaloEntreDoses() {
		return intervaloEntreDoses;
	}

	public void setIntervaloEntreDoses(Integer intervaloEntreDoses) {
		this.intervaloEntreDoses = intervaloEntreDoses;
	}

	public String getFluxoAtualPrescricao() {
		return fluxoAtualPrescricao;
	}

	public void setFluxoAtualPrescricao(String fluxoAtualPrescricao) {
		this.fluxoAtualPrescricao = fluxoAtualPrescricao;
	}

	public Prescricao getPrescricaoBloqueio() {
		return prescricaoBloqueio;
	}

	public void setPrescricaoBloqueio(Prescricao prescricaoBloqueio) {
		this.prescricaoBloqueio = prescricaoBloqueio;
	}
	
}
