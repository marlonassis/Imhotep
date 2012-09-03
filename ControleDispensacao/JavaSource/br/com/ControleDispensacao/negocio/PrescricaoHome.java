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
import br.com.ControleDispensacao.auxiliar.ControlePrescricaoItem;
import br.com.ControleDispensacao.auxiliar.ControlePrescricaoItemDose;
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
import br.com.ControleDispensacao.entidadeExtra.Dose;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="prescricaoHome")
@SessionScoped
public class PrescricaoHome extends PadraoHome<Prescricao>{
	
	private boolean skip;
	private Paciente paciente;
	private Prescricao prescricaoAtual = new Prescricao();
	
	private String usuario;
	private String senha;
	
	private PrescricaoItem prescricaoItem = new PrescricaoItem();
	private Date dataInicio;
	private Integer quantidadeDoses;
	private Integer quantidadePorDose;
	private Integer intervaloEntreDoses;

	private Dose dose = new Dose();
	
	private ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI = new ControleMedicacaoRestritoSCHI();

	public static PrescricaoHome getInstanciaHome(){
		return new ControleInstancia<PrescricaoHome>().instancia("prescricaoHome");
	}
	
	public List<PrescricaoItem> medicamentosPendentesLiberacao(){
		return prescricaoItemPendente(getPrescricaoAtual());
	}
	
	private boolean liberaDose(Material material, Dose dose) {
		EstoqueHome eh = new EstoqueHome();
		Object[] totais = eh.consultaEstoque(material);
		Integer estoqueAtual = (Integer) totais[0] - (Integer) totais[1];
		return !eh.estoqueVazio(estoqueAtual) && !eh.estoqueInsuficiente(estoqueAtual, dose.getQuantidadeDoses(), dose.getQuantidadePorDose());
	}
	
	public List<PrescricaoItem> getItensPrescricao(){
		return itensPrescricao(getPrescricaoAtual());
	}
	
	public String especialidadesLiberamMaterial(Material material){
		ConsultaGeral<String> cg = new ConsultaGeral<String>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idMaterial", material.getIdMaterial());
		List<String> especialidades = (List<String>) cg.consulta(new StringBuilder("select o.especialidade.descricao from LiberaMaterialEspecialidade o where o.material.idMaterial = :idMaterial"), hm);
		
		if(especialidades.isEmpty()){
			return "Todos";
		}
		
		String ret = "";
		int cont = 0;
		for(String especialidade : especialidades){
			ret = ret.concat(especialidade);
			cont++;
			if(especialidades.size() > cont)
				ret = ret.concat(",");
		}
		return ret;
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
	
	public boolean isExisteAntibiotico(){
		for(PrescricaoItem item : prescricaoItemPendente(getPrescricaoAtual())){
			if(isMaterialAntibiotico(item.getMaterial())){
				return true;
			}
		}
		return false;
	}
	
	public void analiseLiberacao(){
		Profissional profissionalAutorizador = Autenticador.getInstancia().profissionalPeloNomeUsuario(getUsuario(), getSenha());
		if(profissionalAutorizador != null){
			analiseIndividualItensPrescritos(profissionalAutorizador);
		}
	}

	private void analiseIndividualItensPrescritos(Profissional profissionalAutorizador) {
		for(PrescricaoItem item : medicamentosPendentesLiberacao()){
			analiseTipoMaterial(profissionalAutorizador, item);
		}
	}

	private void analiseTipoMaterial(Profissional profissionalAutorizador, PrescricaoItem item) {
		if(isMaterialAntibiotico(item.getMaterial()) && Parametro.profissionalEnfermeiroMedico(profissionalAutorizador)){
			anexaAutorizacaoAtualizaItem(profissionalAutorizador, item);
		}else{
			AdicionaAutorizacaoProfissionalAutorizado(profissionalAutorizador, item);
		}
	}

	private void AdicionaAutorizacaoProfissionalAutorizado(Profissional profissionalPeloUsuario, PrescricaoItem item) {
		if(verificaEspecialidadeValida(item.getMaterial(), profissionalPeloUsuario)){
			item.setProfissionalLiberacao(profissionalPeloUsuario);
			new PrescricaoItemHome(item, false).atualizar();
		}
	}

	private void anexaAutorizacaoAtualizaItem(Profissional profissionalPeloUsuario, PrescricaoItem item) {
		anexaFormularioAntibioticoItem(profissionalPeloUsuario, item);
		new PrescricaoItemHome(item, false).atualizar();
	}

	private void anexaFormularioAntibioticoItem(Profissional profissionalPeloUsuario, PrescricaoItem item) {
		getControleMedicacaoRestritoSCHI().setProfissionalAssistente(profissionalPeloUsuario);
		getControleMedicacaoRestritoSCHI().setDataCriacaoAssistente(new Date());
		if(getControleMedicacaoRestritoSCHI().getIdControleMedicacaoRestritoSCHI() == 0){
			new ControleMedicacaoRestrito().gravaRestricao(getControleMedicacaoRestritoSCHI());
		}
		item.setControleMedicacaoRestritoSCHI(getControleMedicacaoRestritoSCHI());
	}
	
	private boolean verificaEspecialidadeValida(Material material, Profissional profissional){
		ConsultaGeral<Integer> cg = new ConsultaGeral<Integer>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idMaterial", material.getIdMaterial());
		hm.put("idEspecialidade", profissional.getEspecialidade().getIdEspecialidade());
		Integer idEspecialidade = (Integer) cg.consultaUnica(new StringBuilder("select a.idLiberaMaterialEspecialidade from LiberaMaterialEspecialidade a where a.material.idMaterial = :idMaterial and a.especialidade.idEspecialidade = :idEspecialidade"), hm);
		return idEspecialidade != null && idEspecialidade != 0;
	}
	
	public boolean isMaterialAntibiotico(Material material){
		if(material != null){
			String grupoMaterial = material.getFamilia().getSubGrupo().getGrupo().getDescricao();
			return grupoMaterial.equalsIgnoreCase(Constantes.MATERIAL_ANTIBIOTICO);
		}
		return false;
	}
	
	private boolean formularioDoseVazio(Dose dose){
		if(dose.getPrescricaoItem().getMaterial() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe o material.", ""));
			return true;
		}
		if(dose.getQuantidadeDoses() == null || dose.getQuantidadeDoses() == 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe a quantidade de doses diárias.", ""));
			return true;
		}
		if(dose.getQuantidadePorDose() == null || dose.getQuantidadePorDose() == 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe a quantidade do medicamento por dose.", ""));
			return true;
		}
		if(dose.getIntervaloEntreDoses() == null || dose.getIntervaloEntreDoses() == 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe o intervalo de tempo entre as doses.", ""));
			return true;
		}
		if(dose.getDataInicio() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe a hora de início da dosagem.", ""));
			return true;
		}
		return false;
	}
	
	public void inserirItem(){
		if(!formularioDoseVazio(getDose()) && liberaDose(getDose().getPrescricaoItem().getMaterial(), getDose())){
			if(getPrescricaoAtual().getIdPrescricao() == 0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu erro ao gravar a prescrição.", ""));
				return;
			}
			getDose().getPrescricaoItem().setPrescricao(getPrescricaoAtual());
			if(!new ControlePrescricaoItem().gravaPrescricaoItem(getDose().getPrescricaoItem())){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu erro ao gravar a prescrição item.", ""));
				return;
			}
			if(!new ControlePrescricaoItemDose().gravaPrescricaoItemDose(getDose())){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu erro ao gravar a dose.", ""));
				return;
			}
			novaDose();
		}
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
	
	private void novaDose() {
		setDose(new Dose());
	}

	public List<PrescricaoItemDose> getPrescricaoItemDoseList2(){
		List<PrescricaoItemDose> pidList = getListaPrescricaoItemDose(getPrescricaoAtual());
		if(pidList != null){
			Collections.sort(pidList, new DoseDataComparador());
			return pidList;
		}else{
			return null;
		}
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
	
	private List<PrescricaoItem> prescricaoItemPendente(Prescricao prescricao){
		if(prescricao != null){
			ConsultaGeral<PrescricaoItem> cg = new ConsultaGeral<PrescricaoItem>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			String selectAutorizacaoEspecialidade = "exists (select a from LiberaMaterialEspecialidade a where a.material.idMaterial = o.material.idMaterial) ";
			return (List<PrescricaoItem>) cg.consulta(new StringBuilder("select o from PrescricaoItem o where o.prescricao.idPrescricao = :idPrescricao and (( "+selectAutorizacaoEspecialidade+" and profissionalLiberacao is null) or (lower(o.material.familia.subGrupo.grupo.descricao) = lower('ANTIBIÓTICO') and controleMedicacaoRestritoSCHI is null)) "), hm);
		}
		return null;
	}

	public void removeDose(PrescricaoItemDose linha){
		PrescricaoItemDoseHome pidh = new PrescricaoItemDoseHome();
		pidh.setInstancia(linha);
		pidh.apagar();
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
	
	public void removePrescricaoItem(PrescricaoItem linha){
		PrescricaoItemHome pih = new PrescricaoItemHome();
		pih.setInstancia(linha);
		pih.apagar();
//		setInstancia(getBusca("select o from Prescricao o where o.idPrescricao = " + getInstancia().getIdPrescricao()).get(0));
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
	
	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	
	public String onFlowProcess(FlowEvent event) {
		if(!new ControlePrescricao().gravaPrescricao(getPrescricaoAtual())){
			FacesContext.getCurrentInstance().addMessage(null,  new FacesMessage("Erro ao gravar a prescrição", "" ));
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Dose getDose(){
		return dose;
	}
	
	public void setDose(Dose dose){
		this.dose = dose;
	}
	
	public ControleMedicacaoRestritoSCHI getControleMedicacaoRestritoSCHI() {
		return controleMedicacaoRestritoSCHI;
	}

	public void setControleMedicacaoRestritoSCHI(
			ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI) {
		this.controleMedicacaoRestritoSCHI = controleMedicacaoRestritoSCHI;
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
	
	
	
	
	
	
	
	}
