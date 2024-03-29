package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FlowEvent;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.comparador.CuidadosPrescricaoComparador;
import br.com.imhotep.consulta.raiz.PrescricaoConsultaRaiz;
import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.controle.ControlePrescricao;
import br.com.imhotep.controle.ControlePrescricaoItem;
import br.com.imhotep.controle.ControlePrescricaoItemDose;
import br.com.imhotep.entidade.ControleMedicacaoRestritoSCHI;
import br.com.imhotep.entidade.CuidadosPaciente;
import br.com.imhotep.entidade.CuidadosPrescricao;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.Prescricao;
import br.com.imhotep.entidade.PrescricaoItem;
import br.com.imhotep.entidade.PrescricaoItemDose;
import br.com.imhotep.entidade.extra.Dose;
import br.com.imhotep.excecoes.ExcecaoControlePrescricaoItem;
import br.com.imhotep.excecoes.ExcecaoControlePrescricaoItemDose;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoFormularioNaoPreenchido;
import br.com.imhotep.excecoes.ExcecaoReservaVazia;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.fluxo.FluxoPrescricaoConfirmacao;
import br.com.imhotep.fluxo.FluxoPrescricaoCuidados;
import br.com.imhotep.fluxo.FluxoPrescricaoLiberacaoMedicamento;
import br.com.imhotep.fluxo.FluxoPrescricaoMedicamento;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class PrescricaoRaiz extends PadraoRaiz<Prescricao>{

	private Prescricao prescricaoAtual = new Prescricao();
	private Prescricao prescricaoBloqueio = new Prescricao();
	private Prescricao prescricaoVisualizacao = new Prescricao();
	
	private ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI = new ControleMedicacaoRestritoSCHI();
	private CuidadosPrescricao cuidadosPrescricao = new CuidadosPrescricao();
	
	private List<CuidadosPrescricao> cuidadosEscolhidosPrescricaoVisualizacao = new ArrayList<CuidadosPrescricao>();
	
	private PrescricaoItem prescricaoItem = new PrescricaoItem();
	
	private List<PrescricaoItemDose> prescricaoItemDoseList = new ArrayList<PrescricaoItemDose>();
	private List<PrescricaoItem> itensPrescricao = new ArrayList<PrescricaoItem>();
	private List<PrescricaoItem> medicamentosPendentesLiberacaoList = new ArrayList<PrescricaoItem>();
	private Map<String, List<CuidadosPaciente>> cuidadosPacienteMap = new HashMap<String, List<CuidadosPaciente>>();
	private List<CuidadosPrescricao> cuidadosEscolhidos = new ArrayList<CuidadosPrescricao>();
	private List<PrescricaoItem> itensLiberadosPrescricao = new ArrayList<PrescricaoItem>();
	private List<ControleMedicacaoRestritoSCHI> controlesAtivosParaLiberacaoList = new ArrayList<ControleMedicacaoRestritoSCHI>();
	
	private Dose dose = new Dose();
	
	private String usuario;
	private String senha;
	
	public void limparPrescricaoVizualizacao(){
		setPrescricaoVisualizacao(new Prescricao());
	}
	
	public void desanexarControle(){
		controleMedicacaoRestritoSCHI = new ControleMedicacaoRestritoSCHI();
	}
	
	public void removerCuidadosPrescricao(CuidadosPrescricao cuidadosPrescricao){
		FluxoPrescricaoCuidados fpc = new FluxoPrescricaoCuidados();
		fpc.apagarCuidadosPrescricao(cuidadosPrescricao);
		carregaCuidadosFluxo();
	}
	
	public void gravaOutrosCuidados(){
		FluxoPrescricaoCuidados fpc = new FluxoPrescricaoCuidados();
		fpc.insereOutrosCuidados(getCuidadosPrescricao());
		carregaCuidadosFluxo();
	}
	
	public boolean isExisteAntibiotico(){
		for(PrescricaoItem item : getMedicamentosPendentesLiberacaoList()){
			if(isMaterialAntibiotico(item.getMaterial())){
				return true;
			}
		}
		return false;
	}
	
	private boolean isMaterialAntibiotico(Material material){
		if(material != null){
			String grupoMaterial = material.getFamilia().getSubGrupo().getGrupo().getDescricao();
			return grupoMaterial.equalsIgnoreCase(Constantes.MATERIAL_ANTIBIOTICO);
		}
		return false;
	}
	
	public void iniciarAnaliseLiberacao(){
		if(formularioLiberacaoPreenchido()){
			FluxoPrescricaoLiberacaoMedicamento fplm = new FluxoPrescricaoLiberacaoMedicamento();
			getControleMedicacaoRestritoSCHI().setPrescricao(getPrescricaoAtual());
			getControleMedicacaoRestritoSCHI().setPaciente(getPrescricaoAtual().getPaciente());
			getControleMedicacaoRestritoSCHI().setMassa(getPrescricaoAtual().getMassa());
			getControleMedicacaoRestritoSCHI().setLeito(getPrescricaoAtual().getLeito());
			//TODO alterar o nome do m�todo - refatorar
			if(fplm.analisarLiberacao(getControleMedicacaoRestritoSCHI(), getUsuario(), getSenha())){
				carregaItensLiberacao();
				limpaFormularioLiberacao();
			}
		}
	}

	private void limpaFormularioLiberacao() {
		setUsuario(null);
		setSenha(null);
		setControleMedicacaoRestritoSCHI(new ControleMedicacaoRestritoSCHI());
	}
	
	private boolean formularioLiberacaoPreenchido() {
		if(getUsuario().isEmpty() || getSenha().isEmpty()){
			super.mensagem("Informe o usu�rio e senha.", null, FacesMessage.SEVERITY_INFO);
			return false;
		}
		return true;
	}

	public String especialidadesLiberarMedicamento(Material material){
		FluxoPrescricaoLiberacaoMedicamento fplm = new FluxoPrescricaoLiberacaoMedicamento();
		return fplm.especialidadesLiberamMaterial(material);
	}
	
	public static PrescricaoRaiz getInstanciaHome() {
		try {
			return (PrescricaoRaiz) new ControleInstancia().procuraInstancia(PrescricaoRaiz.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void removePrescricaoItem(PrescricaoItem tupla){
		ControlePrescricaoItem cpi = new ControlePrescricaoItem();
		cpi.removePrescricaoItem(tupla);
		carregaItensFarmacologicosFluxo();
		carregaDoseFluxo();
	}
	
	public void removePrescricaoItemDose(PrescricaoItemDose tupla){
		ControlePrescricaoItemDose cpi = new ControlePrescricaoItemDose();
		cpi.removeDose(tupla);
		carregaDoseFluxo();
	}
	
	public void cancelarPrescricao(){
		if(getPrescricaoAtual().getIdPrescricao() != 0){
			setInstancia(getPrescricaoAtual());
			if(super.apagar()){
				setPrescricaoAtual(new Prescricao());
				super.novaInstancia();
			}
		}else{
			setPrescricaoAtual(new Prescricao());
		}
	}
	
	public void novaPrescricao(){
		prescricaoAtual = new Prescricao();
		prescricaoBloqueio = new Prescricao();
		controleMedicacaoRestritoSCHI = new ControleMedicacaoRestritoSCHI();
		cuidadosPrescricao = new CuidadosPrescricao();
		prescricaoItem = new PrescricaoItem();
		prescricaoItemDoseList = new ArrayList<PrescricaoItemDose>();
		itensPrescricao = new ArrayList<PrescricaoItem>();
		medicamentosPendentesLiberacaoList = new ArrayList<PrescricaoItem>();
		cuidadosPacienteMap = new HashMap<String, List<CuidadosPaciente>>();
		cuidadosEscolhidos = new ArrayList<CuidadosPrescricao>();
		itensLiberadosPrescricao = new ArrayList<PrescricaoItem>();
		controlesAtivosParaLiberacaoList = new ArrayList<ControleMedicacaoRestritoSCHI>();
		dose = new Dose();
		usuario = null;
		senha = null;
	}
	
	public void concluirPrescricao(){
		getInstancia().setDispensavel(true);
		getInstancia().setDataConclusao(new Date());
		if(super.atualizar()){
			novaInstancia();
		}
	}
	
	public void finalizarPrescricao() {
		getPrescricaoAtual().setDispensavel(true);
		getPrescricaoAtual().setDataConclusao(new Date());
		try {
			getPrescricaoAtual().setProfissionalConclusao(Autenticador.getInstancia().getProfissionalAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o profissional atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em PrescricaoHome");
		}
		if(atualizarGenerico(getPrescricaoAtual()) != null){
			setPrescricaoVisualizacao(new PrescricaoConsultaRaiz().consultar(getPrescricaoAtual().getIdPrescricao()));
			setPrescricaoAtual(new Prescricao());
		}
	}
	
	public void adicionarCuidado(CuidadosPaciente cuidadosPaciente){
		new CuidadosPrescricaoRaiz().enviar(cuidadosPaciente, getPrescricaoAtual());
		carregaCuidadosFluxo();
	}
	
	public void bloqueiarPrescricao(){
		if(getPrescricaoBloqueio().getMotivoBloqueio() != null && !getPrescricaoBloqueio().getMotivoBloqueio().isEmpty()){
			getPrescricaoBloqueio().setDataBloqueio(new Date());
			try {
				getPrescricaoBloqueio().setProfissionalBloqueio(Autenticador.getInstancia().getProfissionalAtual());
			} catch (Exception e) {
				e.printStackTrace();
				super.mensagem("Erro ao pegar o profissional atual.", null, Constantes.ERROR);
				System.out.print("Erro em PrescricaoHome");
			}
			super.atualizarGenerico(getPrescricaoBloqueio());
		}else{
			super.mensagem("Informe o motivo do bloqueio.", null, Constantes.ERROR);
		}
	}
	
	public void adicionarItemFarmacoPrescricaoDoseControleSCHI(Prescricao prescricaoSCHI, Dose dose) throws ExcecaoFormularioNaoPreenchido, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoControlePrescricaoItemDose, ExcecaoReservaVazia{
		getDose().getPrescricaoItem().setPrescricao(prescricaoSCHI);
		gravarDose(dose);
	}
	
	public void adicionarItemFarmacoPrescricaoControleSCHI(Prescricao prescricaoSCHI, Dose dose) throws ExcecaoFormularioNaoPreenchido, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoControlePrescricaoItem, ExcecaoControlePrescricaoItemDose, ExcecaoReservaVazia{
		gravarPrescricaoMedicamento(dose, prescricaoSCHI);
	}
	
	private void gravarDose(Dose dose) throws ExcecaoFormularioNaoPreenchido, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoControlePrescricaoItemDose, ExcecaoReservaVazia {
		FluxoPrescricaoMedicamento fpm = new FluxoPrescricaoMedicamento();
		fpm.inserirDose(dose);
	}
	
	public void adicionarItemFarmacoPrescricao(){
		try {
			gravarPrescricaoMedicamento(getDose(), getPrescricaoAtual());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void gravarPrescricaoMedicamento(Dose doseFluxo, Prescricao prescricao) throws ExcecaoFormularioNaoPreenchido, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoControlePrescricaoItem, ExcecaoControlePrescricaoItemDose, ExcecaoReservaVazia {
		FluxoPrescricaoMedicamento fpm = new FluxoPrescricaoMedicamento();
		fpm.inserirItem(doseFluxo, prescricao);
		carregaDoseFluxo();
		carregaItensFarmacologicosFluxo();
		setDose(new Dose());
	}
	
	public List<Material> itensContidosLiberacao(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI){
		ConsultaGeral<Material> cg = new ConsultaGeral<Material>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idControleMedicacaoRestritoSCHI", controleMedicacaoRestritoSCHI.getIdControleMedicacaoRestritoSCHI());
		return (List<Material>) cg.consulta(new StringBuilder("select a.material from PrescricaoItem a where a.controleMedicacaoRestritoSCHI.idControleMedicacaoRestritoSCHI = :idControleMedicacaoRestritoSCHI"), hm);
	}
	
	public String onFlowProcess(FlowEvent event) {
		
		verificaFluxoPrescricaoPaciente(event.getOldStep());
		verificaFluxoPrescricaoFarmacologica(event.getNewStep());
		verificaFluxoPrescricaoLiberacao(event.getNewStep());
		verificaFluxoPrescricaoCuidadosPrescricao(event.getNewStep());
		verificaFluxoPrescricaoItensLiberados(event.getNewStep());
		
		return event.getNewStep();
	}

	private void verificaFluxoPrescricaoItensLiberados(String fluxoAtual) {
		if(fluxoAtual.equals(Constantes.PRESCRICAO_CONFIRMACAO_TAB)){
			carregaItensLiberadosPrescricao();
		}
	}
	
	private void carregaItensLiberadosPrescricao(){
		FluxoPrescricaoConfirmacao fpc = new FluxoPrescricaoConfirmacao();
		setItensLiberadosPrescricao(fpc.getItensLiberados());
	}
	
	public List<PrescricaoItem> getItensLiberadosPrescricaoVisualizacao(){
		FluxoPrescricaoConfirmacao fpc = new FluxoPrescricaoConfirmacao();
		return fpc.getItensLiberados(getPrescricaoVisualizacao());
	}
	
	public void carregarCuidadosEcolhidosPrescricaoVizualizacao(){
		FluxoPrescricaoCuidados fpc = new FluxoPrescricaoCuidados();
		setCuidadosEscolhidosPrescricaoVisualizacao(fpc.getCuidadosEscolhidosVisualizacao(getPrescricaoVisualizacao()));
	}
	
	public List<CuidadosPrescricao> getCuidadosEscolhidosPrescricaoVisualizacao(){
		return cuidadosEscolhidosPrescricaoVisualizacao;
	}
	
	public void setCuidadosEscolhidosPrescricaoVisualizacao(List<CuidadosPrescricao> cuidadosEscolhidosPrescricaoVisualizacao){
		this.cuidadosEscolhidosPrescricaoVisualizacao = cuidadosEscolhidosPrescricaoVisualizacao;
	}
	
	private void verificaFluxoPrescricaoCuidadosPrescricao(String fluxoAtual) {
		if(fluxoAtual.equals(Constantes.PRESCRICAO_CUIDADOS_TAB)){
			carregaCuidadosFluxo();
		}
	}
	
	private void carregaCuidadosFluxo(){
		FluxoPrescricaoCuidados fpc = new FluxoPrescricaoCuidados();
		getCuidadosPacienteMap().put("aer", fpc.listaPrescricaoCuidados("AER"));
		getCuidadosPacienteMap().put("cen", fpc.listaPrescricaoCuidados("CEN"));
		getCuidadosPacienteMap().put("med", fpc.listaPrescricaoCuidados("MED"));
		getCuidadosPacienteMap().put("mor", fpc.listaPrescricaoCuidados("MOR"));
		getCuidadosPacienteMap().put("msc", fpc.listaPrescricaoCuidados("MSC"));
		getCuidadosPacienteMap().put("mso", fpc.listaPrescricaoCuidados("MSO"));
		getCuidadosPacienteMap().put("mto", fpc.listaPrescricaoCuidados("MTO"));
		getCuidadosPacienteMap().put("nut", fpc.listaPrescricaoCuidados("NUT"));
		getCuidadosPacienteMap().put("sor", fpc.listaPrescricaoCuidados("SOR"));
		setCuidadosEscolhidos(fpc.getCuidadosEscolhidos());
		Collections.sort(getCuidadosEscolhidos(), new CuidadosPrescricaoComparador());
	}
	
	private void verificaFluxoPrescricaoPaciente(String fluxoAntigo) {
		if(fluxoAntigo.equals(Constantes.PRESCRICAO_PACIENTE_TAB)){
			if(getPrescricaoAtual().getIdPrescricao() == 0){
				if(!new ControlePrescricao().gravaPrescricao(getPrescricaoAtual())){
					super.mensagem("Erro ao gravar a prescri��o", null, FacesMessage.SEVERITY_ERROR);
				}
			}else{
				if(!new ControlePrescricao().atualizaPrescricao(getPrescricaoAtual())){
					super.mensagem("Erro ao atualizar a prescri��o", null, FacesMessage.SEVERITY_ERROR);
				}
			}
		}
	}
	
	private void verificaFluxoPrescricaoFarmacologica(String fluxoAtual) {
		if(fluxoAtual.equals(Constantes.PRESCRICAO_FARMACOLOGICA_TAB)){
			carregaItensFarmacologicosFluxo();
			carregaDoseFluxo();
		}
	}
	
	private void verificaFluxoPrescricaoLiberacao(String fluxoAtual) {
		if(fluxoAtual.equals(Constantes.PRESCRICAO_LIBERACAO_TAB)){
			carregaItensLiberacao();
			carregaLiberacaoAutorizadas();
		}
	}

	private void carregaLiberacaoAutorizadas(){
		FluxoPrescricaoLiberacaoMedicamento fplm = new FluxoPrescricaoLiberacaoMedicamento();
		setControlesAtivosParaLiberacaoList(fplm.controlesAtivos(getPrescricaoAtual().getPaciente()));
	}
	
	private void carregaItensLiberacao() {
		FluxoPrescricaoLiberacaoMedicamento fpm = new FluxoPrescricaoLiberacaoMedicamento();
		setMedicamentosPendentesLiberacaoList(fpm.getMedicamentosPendentesLiberacao());
	}
	
	private void carregaItensFarmacologicosFluxo() {
		FluxoPrescricaoMedicamento fpm = new FluxoPrescricaoMedicamento();
		setItensPrescricao(fpm.getItensPrescricao());
	}
	
	private void carregaDoseFluxo() {
		FluxoPrescricaoMedicamento fpm = new FluxoPrescricaoMedicamento();
		setPrescricaoItemDoseList(fpm.getPrescricaoItemDoseList());
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
	
	public Prescricao getPrescricaoBloqueio() {
		return prescricaoBloqueio;
	}

	public void setPrescricaoBloqueio(Prescricao prescricaoBloqueio) {
		this.prescricaoBloqueio = prescricaoBloqueio;
	}

	public List<PrescricaoItem> getItensPrescricao() {
		return itensPrescricao;
	}

	public void setItensPrescricao(List<PrescricaoItem> itensPrescricao) {
		this.itensPrescricao = itensPrescricao;
	}

	public void setPrescricaoItemDoseList(List<PrescricaoItemDose> prescricaoItemDoseList) {
		this.prescricaoItemDoseList = prescricaoItemDoseList;
	}
	
	public List<PrescricaoItemDose> getPrescricaoItemDoseList() {
		return prescricaoItemDoseList;
	}

	public Dose getDose() {
		return dose;
	}

	public void setDose(Dose dose) {
		this.dose = dose;
	}

	public List<PrescricaoItem> getMedicamentosPendentesLiberacaoList() {
		return medicamentosPendentesLiberacaoList;
	}

	public void setMedicamentosPendentesLiberacaoList(List<PrescricaoItem> medicamentosPendentesLiberacaoList) {
		this.medicamentosPendentesLiberacaoList = medicamentosPendentesLiberacaoList;
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

	public ControleMedicacaoRestritoSCHI getControleMedicacaoRestritoSCHI() {
		return controleMedicacaoRestritoSCHI;
	}

	public void setControleMedicacaoRestritoSCHI(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI) {
		this.controleMedicacaoRestritoSCHI = controleMedicacaoRestritoSCHI;
	}

	public void setControleMedicacaoRestritoSCHIComMensagem(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI) {
		this.controleMedicacaoRestritoSCHI = controleMedicacaoRestritoSCHI;
		if(this.controleMedicacaoRestritoSCHI != null){
			super.mensagem("Anexado com sucesso", null, Constantes.INFO);
		}
	}
	
	public Map<String, List<CuidadosPaciente>> getCuidadosPacienteMap() {
		return cuidadosPacienteMap;
	}

	public void setCuidadosPacienteMap(Map<String, List<CuidadosPaciente>> cuidadosPacienteMap) {
		this.cuidadosPacienteMap = cuidadosPacienteMap;
	}

	public CuidadosPrescricao getCuidadosPrescricao() {
		return cuidadosPrescricao;
	}

	public void setCuidadosPrescricao(CuidadosPrescricao cuidadosPrescricao) {
		this.cuidadosPrescricao = cuidadosPrescricao;
	}

	public List<CuidadosPrescricao> getCuidadosEscolhidos() {
		return cuidadosEscolhidos;
	}

	public void setCuidadosEscolhidos(List<CuidadosPrescricao> cuidadosEscolhidos) {
		this.cuidadosEscolhidos = cuidadosEscolhidos;
	}

	public List<PrescricaoItem> getItensLiberadosPrescricao() {
		return itensLiberadosPrescricao;
	}

	public void setItensLiberadosPrescricao(List<PrescricaoItem> itensLiberadosPrescricao) {
		this.itensLiberadosPrescricao = itensLiberadosPrescricao;
	}

	public List<ControleMedicacaoRestritoSCHI> getControlesAtivosParaLiberacaoList() {
		carregaLiberacaoAutorizadas();
		return controlesAtivosParaLiberacaoList;
	}

	public void setControlesAtivosParaLiberacaoList(
			List<ControleMedicacaoRestritoSCHI> controlesAtivosParaLiberacaoList) {
		this.controlesAtivosParaLiberacaoList = controlesAtivosParaLiberacaoList;
	}

	public Prescricao getPrescricaoVisualizacao() {
		return prescricaoVisualizacao;
	}

	public void setPrescricaoVisualizacao(Prescricao prescricaoVisualizacao) {
		this.prescricaoVisualizacao = prescricaoVisualizacao;
	}

	
	
}
