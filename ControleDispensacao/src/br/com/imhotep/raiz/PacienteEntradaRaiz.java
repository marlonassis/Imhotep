package br.com.imhotep.raiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.Cid;
import br.com.imhotep.entidade.Paciente;
import br.com.imhotep.entidade.PacienteEntrada;
import br.com.imhotep.entidade.PacienteEntradaCid;
import br.com.imhotep.entidade.PacienteEntradaProcedimentoSaude;
import br.com.imhotep.entidade.PacienteEntradaResponsavel;
import br.com.imhotep.entidade.ProcedimentoSaude;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class PacienteEntradaRaiz extends PadraoHome<PacienteEntrada>{
	
	private String valorPesquisa;
	private Cid cid;
	private ProcedimentoSaude procedimentoSaude;
	
	private Set<Cid> cids = new HashSet<Cid>();
	private Set<ProcedimentoSaude> procedimentos = new HashSet<ProcedimentoSaude>();
	
	public PacienteEntradaRaiz(){
		novaInstancia();
	}
	
	public void atualizaCid(){
		if(!getCids().add(getCid())){
			getCids().remove(getCid());
		}
		setCid(new Cid());
	}
	
	public void atualizaProcedimento(){
		if(!getProcedimentos().add(getProcedimentoSaude())){
			getProcedimentos().remove(getProcedimentoSaude());
		}
		setProcedimentoSaude(new ProcedimentoSaude());
	}
	
	public void removerItem(Object obj){
		if(obj instanceof Cid){
			getCids().remove(obj);
		}else{
			getProcedimentos().remove(obj);
		}
	}
	
	@Override
	public void novaInstancia() {
		Paciente paciente = getInstancia().getPaciente();
		super.novaInstancia();
		getInstancia().setPaciente(paciente);
		getInstancia().setPacienteEntradaResponsavel(new PacienteEntradaResponsavel());
		setCids(new HashSet<Cid>());
		setProcedimentos(new HashSet<ProcedimentoSaude>());
	}
	
	public static PacienteEntradaRaiz getInstanciaHome(){
		try {
			return (PacienteEntradaRaiz) new ControleInstancia().procuraInstancia(PacienteEntradaRaiz.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void gravarCidProcedimento() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Date data = new Date();
		Profissional profissionalAtual = Autenticador.getInstancia().getProfissionalAtual();
		for(Cid cid : getCids()){
			PacienteEntradaCidRaiz pecr = new PacienteEntradaCidRaiz(false);
			pecr.getInstancia().setCid(cid);
			pecr.getInstancia().setPacienteEntrada(getInstancia());
			pecr.getInstancia().setDataInsercao(data);
			pecr.getInstancia().setProfissionalInsercao(profissionalAtual);
			pecr.enviar();
		}
		
		for(ProcedimentoSaude ps : getProcedimentos()){
			PacienteEntradaProcedimentoSaudeRaiz perr = new PacienteEntradaProcedimentoSaudeRaiz();
			perr.getInstancia().setDataInsercao(data);
			perr.getInstancia().setPacienteEntrada(getInstancia());
			perr.getInstancia().setProcedimentoSaude(ps);
			perr.getInstancia().setProfissionalInsercao(profissionalAtual);
			perr.enviar();
		}
	}
	
	@Override
	protected void preEnvio() {
		Date dataAtual = new Date();
		getInstancia().setDataInclusao(dataAtual);
		if(getInstancia().getDataAtendimento() == null){
			getInstancia().setDataAtendimento(dataAtual);
		}
		try {
			getInstancia().setProfissionalInclusao(Autenticador.getInstancia().getProfissionalAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o profissional atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em PacienteEntradaHome");
		}
		super.preEnvio();
	}
	
	@Override
	protected void aposEnviar() {
		try {
			gravarCidProcedimento();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		super.aposEnviar();
	}
	
	public void carregaPaciente() throws IOException{
		String valorPesquisa = getValorPesquisa();
		ConsultaGeral<Paciente> cg = new ConsultaGeral<Paciente>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("valorPesquisa", valorPesquisa);
		Paciente paciente = (Paciente) cg.consultaUnica(new StringBuilder("select o from Paciente o where o.numeroSus = :valorPesquisa or o.prontuario = :valorPesquisa"), hm);
		if(paciente == null){
			setInstancia(new PacienteEntrada());
			FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_ENTRADA_PACIENTE);
			super.mensagem("O número do SUS ou prontuário informado não está cadastro.", "Verifique se você informou o número certo ou cadastre esse novo paciente.", FacesMessage.SEVERITY_ERROR);
		}else{
			getInstancia().setPaciente(paciente);
			carregarUltimoAntendimento();
		}
	}
	
	public void carregarUltimoAntendimento() {
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("paciente", getInstancia().getPaciente().getIdPaciente());
		String sql = "select o from PacienteEntrada o where o.paciente.idPaciente = :paciente and o.dataInclusao = (select max(a.dataInclusao) from PacienteEntrada a where a.paciente.idPaciente = :paciente)";
		ConsultaGeral<PacienteEntrada> cg = new ConsultaGeral<PacienteEntrada>();
		PacienteEntrada pe = (PacienteEntrada) cg.consultaUnica(new StringBuilder(sql), hm);
		if(pe != null){
			setInstancia(pe.clone());
		}
	}
	
	public List<PacienteEntrada> getEntradasPaciente(){
		if(getInstancia().getPaciente() != null){
			ConsultaGeral<PacienteEntrada> cg = new ConsultaGeral<PacienteEntrada>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPaciente", getInstancia().getPaciente().getIdPaciente());
			return new ArrayList<PacienteEntrada>(cg.consulta(new StringBuilder("select o from PacienteEntrada o where o.paciente.idPaciente = :idPaciente"), hm));
		}
		return null;
	}
	
	public String getValorPesquisa() {
		return valorPesquisa;
	}

	public void setValorPesquisa(String valorPesquisa) {
		this.valorPesquisa = valorPesquisa;
	}

	public int idPacienteAtual() {
		if(getInstancia().getPaciente() != null)
			return getInstancia().getPaciente().getIdPaciente();
		
		return 0;
	}
	
	public Set<Cid> getCids() {
		return cids;
	}
	public void setCids(Set<Cid> cids) {
		this.cids = cids;
	}
	
	public Set<ProcedimentoSaude> getProcedimentos() {
		return procedimentos;
	}
	public void setProcedimentos(Set<ProcedimentoSaude> procedimentos) {
		this.procedimentos = procedimentos;
	}

	public Cid getCid() {
		return cid;
	}

	public void setCid(Cid cid) {
		this.cid = cid;
	}

	public ProcedimentoSaude getProcedimentoSaude() {
		return procedimentoSaude;
	}

	public void setProcedimentoSaude(ProcedimentoSaude procedimentoSaude) {
		this.procedimentoSaude = procedimentoSaude;
	}
	
	public List<Cid> getCidsList(){
		return new ArrayList<Cid>(getCids());
	}
	
	public List<ProcedimentoSaude> getProcedimentosList(){
		return new ArrayList<ProcedimentoSaude>(getProcedimentos());
	}
	
	private void carregarListas(){
		if(getInstancia().getCids() != null){
			for(PacienteEntradaCid pecid : getInstancia().getCids()){
				getCids().add(pecid.getCid());
			}
		}
		
		if(getInstancia().getProcedimentos() != null){	
			for(PacienteEntradaProcedimentoSaude peps : getInstancia().getProcedimentos()){
				getProcedimentos().add(peps.getProcedimentoSaude());
			}
		}
	}
	
	@Override
	public void setInstancia(PacienteEntrada instancia) {
		novaInstancia();
		super.setInstancia(instancia);
		carregarListas();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
