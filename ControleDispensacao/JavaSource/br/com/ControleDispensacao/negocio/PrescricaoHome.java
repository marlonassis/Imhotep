package br.com.ControleDispensacao.negocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.FlowEvent;

import br.com.ControleDispensacao.auxiliar.Constantes;
import br.com.ControleDispensacao.auxiliar.ControlePrescricao;
import br.com.ControleDispensacao.auxiliar.ControlePrescricaoItem;
import br.com.ControleDispensacao.auxiliar.ControlePrescricaoItemDose;
import br.com.ControleDispensacao.auxiliar.Parametro;
import br.com.ControleDispensacao.comparador.CuidadosPacienteComparador;
import br.com.ControleDispensacao.comparador.DoseDataComparador;
import br.com.ControleDispensacao.entidade.ControleMedicacaoRestritoSCHI;
import br.com.ControleDispensacao.entidade.CuidadosPaciente;
import br.com.ControleDispensacao.entidade.ErroAplicacao;
import br.com.ControleDispensacao.entidade.Especialidade;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.Paciente;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.entidade.PrescricaoItemDose;
import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.entidade.Usuario;
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
	private Prescricao prescricao = new Prescricao();
	
	private List<CuidadosPaciente> cuidadosEscolhidos = new ArrayList<CuidadosPaciente>();
	private List<CuidadosPaciente> cuidadosDisponiveis = new ArrayList<CuidadosPaciente>();

	private String usuario;
	private String senha;
	
	private PrescricaoItem prescricaoItem = new PrescricaoItem();
	private Date dataInicio;
	private Integer quantidadeDoses;
	private Integer quantidadePorDose;
	private Integer intervaloEntreDoses;

	private Dose dose = new Dose();
	
	public List<PrescricaoItem> medicamentosPendentesLiberacao(){
		return prescricaoItemPendente(getPrescricao());
	}
	
	private boolean liberaDose(Material material, Dose dose) {
		EstoqueHome eh = new EstoqueHome();
		Object[] totais = eh.consultaEstoque(material);
		Integer estoqueAtual = (Integer) totais[0] - (Integer) totais[1];
		return !eh.estoqueVazio(estoqueAtual) && !eh.estoqueInsuficiente(estoqueAtual, dose.getQuantidadeDoses(), dose.getQuantidadePorDose());
	}
	
	private boolean liberaDose(Material material) {
		EstoqueHome eh = new EstoqueHome();
		Object[] totais = eh.consultaEstoque(material);
		Integer estoqueAtual = (Integer) totais[0] - (Integer) totais[1];
		return !eh.estoqueVazio(estoqueAtual) && !eh.estoqueInsuficiente(estoqueAtual, getQuantidadeDoses(), getQuantidadePorDose());
	}
	
	public List<PrescricaoItem> itensPrescricao(){
		if(prescricao != null){
			ConsultaGeral<PrescricaoItem> cg = new ConsultaGeral<PrescricaoItem>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			return (List<PrescricaoItem>) cg.consulta(new StringBuilder("select o from PrescricaoItem o where o.prescricao.idPrescricao = :idPrescricao"), hm);
		}
		return null;
	}
	
	public boolean isExisteAntibiotico(){
		for(PrescricaoItem item : prescricaoItemPendente(getPrescricao())){
			if(isMaterialAntibiotico(item.getMaterial())){
				return true;
			}
		}
		return false;
	}
	
	public void analiseLiberacao(){
		List<PrescricaoItem> medicamentosPendentesLiberacao = medicamentosPendentesLiberacao();
		Autenticador a = new Autenticador();
		Usuario usuarioLiberacao = a.procurarUsuario(usuario);
		if(usuarioLiberacao != null && a.verificaSenha(usuarioLiberacao, senha)){
			for(PrescricaoItem item : medicamentosPendentesLiberacao){
				if(isMaterialAntibiotico(item.getMaterial()) && Parametro.usuarioEnfermeiroMedico(usuarioLiberacao)){
					
				}
			}
		}
	}
	
	public boolean isMaterialAntibiotico(Material material){
		if(material != null){
			String grupoMaterial = material.getFamilia().getSubGrupo().getGrupo().getDescricao();
			return grupoMaterial.equalsIgnoreCase(Constantes.MATERIAL_ANTIBIOTICO);
		}
		return false;
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
			if(!new ControlePrescricao().gravaPrescricao(getPrescricao())){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu erro ao gravar a prescrição.", ""));
				return;
			}
			getDose().getPrescricaoItem().setPrescricao(getPrescricao());
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

	private void novaDose() {
		setDose(new Dose());
	}

	public List<PrescricaoItemDose> getPrescricaoItemDoseList2(){
		List<PrescricaoItemDose> pidList = getListaPrescricaoItemDose(getPrescricao());
		if(pidList != null){
			Collections.sort(pidList, new DoseDataComparador());
			return pidList;
		}else{
			return null;
		}
	}
	
	public List<PrescricaoItemDose> getPrescricaoItemDoseList(){
		List<PrescricaoItemDose> pidList = getListaPrescricaoItemDose(getPrescricao());
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
		StringBuilder sb = new StringBuilder("select o from CuidadosPaciente o ");
		if(!getCuidadosEscolhidos().isEmpty()){
			sb.append("where o.idCuidadosPaciente not in ("+ getIdCuidadosEscolhidos() +")");
		}
		
		sb.append(" order by o.descricao");
		
		setCuidadosDisponiveis(new ArrayList<CuidadosPaciente>(new ConsultaGeral<CuidadosPaciente>(sb).consulta()));
		
		return getCuidadosDisponiveis();
	}
	
	private String getIdCuidadosEscolhidos() {
		String ids = "";
		int cont = 0;
		for(CuidadosPaciente o : getCuidadosEscolhidos()){
			ids = ids.concat(String.valueOf(o.getIdCuidadosPaciente()));
			cont++;
			if(getCuidadosEscolhidos().size() > cont)
				ids = ids.concat(",");
		}
		return ids;
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

	public Prescricao getPrescricao() {
		return prescricao;
	}

	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}

	public List<CuidadosPaciente> getCuidadosEscolhidos() {
		Collections.sort(cuidadosEscolhidos, new CuidadosPacienteComparador());
		return cuidadosEscolhidos;
	}

	public void setCuidadosEscolhidos(List<CuidadosPaciente> cuidadosEscolhidos) {
		this.cuidadosEscolhidos = cuidadosEscolhidos;
	}

	public List<CuidadosPaciente> getCuidadosDisponiveis() {
		return cuidadosDisponiveis;
	}

	public void setCuidadosDisponiveis(List<CuidadosPaciente> cuidadosDisponiveis) {
		this.cuidadosDisponiveis = cuidadosDisponiveis;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////
	
	
	private boolean iniciaDosagem;
	private boolean mostraModalLiberacaoMedicamento;
	private Profissional profissionalLiberacao;
	
	private String mensagem;
	private String mensagemComplementar;
	private ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI = new ControleMedicacaoRestritoSCHI();
	
	private void limpaVariaveis(){
		setDataInicio(null);
		setQuantidadeDoses(null);
		setQuantidadePorDose(null);
		setIntervaloEntreDoses(null);
	}
	
	public List<Prescricao> getListaPrescricaoPendente(){
		return getBusca("select o from Prescricao o where o.dispensavel = 'N' and o.usuarioInclusao.idUsuario = "+Autenticador.getInstancia().getUsuarioAtual().getIdUsuario()+" order by o.dataInclusao");
	}
	
	public void iniciaDosagem(){
		limpaVariaveis();
		if(getInstancia().getIdPrescricao() != 0 || gravaPrescricao()){
			limpaVariaveis();
			setPrescricaoItem(new PrescricaoItem());
			getPrescricaoItem().setPrescricaoItemDoses(new HashSet<PrescricaoItemDose>());
			iniciaDosagem = true;
		}
	}
	
	public void removeDose(PrescricaoItemDose linha){
		PrescricaoItemDoseHome pidh = new PrescricaoItemDoseHome();
		pidh.setInstancia(linha);
		pidh.apagar();
	}
	
	public void removePrescricaoItem(PrescricaoItem linha){
		PrescricaoItemHome pih = new PrescricaoItemHome();
		pih.setInstancia(linha);
		pih.apagar();
//		setInstancia(getBusca("select o from Prescricao o where o.idPrescricao = " + getInstancia().getIdPrescricao()).get(0));
	}
	
	private boolean removePrescricaoItem(){
		boolean ret = false;
		try{
			iniciarTransacao();
			session.delete(getPrescricaoItem()); // Realiza persistência
			tx.commit(); // Finaliza transação
			ret = true;
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu ao apagar a prescrição.", "Utilize o material de apoio para precrever até o sistema voltar ao normal."));
			gravaErroAplicacao(new Date(), e.getMessage(), e.getStackTrace(), "removeDoses()");
			if(session != null){
				session.getTransaction().rollback();
			}
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		return ret;
	}
	
	public void primeiraLiberacaoDose(){
		adicionaDoses(Autenticador.getInstancia().getProfissionalAtual());
	}
	
	private Profissional carregaProfissional(Usuario usuario){
		ConsultaGeral<Profissional> cg = new ConsultaGeral<Profissional>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idUsuario", usuario.getIdUsuario());
		return cg.consultaUnica(new StringBuilder("select o from Profissional o where o.usuario.idUsuario = :idUsuario"), hm);
	}
	
	public List<ControleMedicacaoRestritoSCHI> getControlesValidos(){
		if(getPrescricaoItem() != null && getPrescricaoItem().getMaterial() != null){
			ConsultaGeral<ControleMedicacaoRestritoSCHI> cg = new ConsultaGeral<ControleMedicacaoRestritoSCHI>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("dataLimite", new Date());
			hm.put("idPaciente", getInstancia().getPaciente().getIdPaciente());
			return new ArrayList<ControleMedicacaoRestritoSCHI>(cg.consulta(new StringBuilder("select o from ControleMedicacaoRestritoSCHI o where o.dataLimite >= :dataLimite and o.tipoPrescricaoInadequada is null and o.profissionalInfectologista != null and o.idControleMedicacaoRestritoSCHI in (select a.controleMedicacaoRestritoSCHI.idControleMedicacaoRestritoSCHI from PrescricaoItem a where a.prescricao.paciente.idPaciente = :idPaciente)"), hm));
		}
		return new ArrayList<ControleMedicacaoRestritoSCHI>();
	}
	
	public void segundaLiberacaoDose(){
		Autenticador a = new Autenticador();
		Usuario usuarioLiberacao = a.procurarUsuario(usuario);
		if(usuarioLiberacao != null && a.verificaSenha(usuarioLiberacao, senha)){
			Profissional profissionalLiberacao = carregaProfissional(usuarioLiberacao);
			adicionaDoses(profissionalLiberacao);
			getControleMedicacaoRestritoSCHI().setProfissionalAssistente(profissionalLiberacao);
		}
	}
	
	private boolean geraLiberacaoAntibiotico(){
		if(isMaterialAntibiotico(getPrescricaoItem() != null ? getPrescricaoItem().getMaterial() : null)){
			if(new ControleMedicacaoRestritoSCHIHome(getControleMedicacaoRestritoSCHI()).enviar()){
				return true;
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao gravar a liberação para antibiótico.", "Tente novamente ou procure o CPD."));
			return false;
		}
		return true;
	}
	
	private void adicionaDoses(Profissional profissionalLiberacao){
		if(liberaDose(getPrescricaoItem().getMaterial())){
			if(liberaMedicamento(profissionalLiberacao)){
				inicializaPrescricaoItemDoses();
				if(getPrescricaoItem().getIdPrescricaoItem() != 0 || gravaPrescricaoItem()){
					gravaPrescricaoItemDose();
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagemComplementar));
			}
		}
	}

	private String getListaEspecialidadeString(){
		String especialidades = "";
		List<Especialidade> especialidadesMedicamento = getEspecialidadeMedicamento(getPrescricaoItem().getMaterial());
		for(int i = 0; i < especialidadesMedicamento.size(); i++){
			Especialidade e = especialidadesMedicamento.get(i);
			especialidades = especialidades.concat(e.getDescricao());
			if(i <= especialidadesMedicamento.size() - 3){
				especialidades = especialidades.concat(", ");
			}else{
				if(i == especialidadesMedicamento.size() - 2){
					especialidades = especialidades.concat(" ou ");
				}
			}
		}
		return especialidades;
	}
	
	private List<Especialidade> getEspecialidadeMedicamento(Material material){
		return new ArrayList<Especialidade>(new EspecialidadeHome().getListaEspecialidadeMaterial(material));
	}
	
	public void cancelaAutorizacaoMaterial(){
		usuario = null;
		senha = null;
		mostraModalLiberacaoMedicamento = false;
	}
	
	/**
	 * Verifica se o medicamento pode ser prescrito pelo profissional logado usando sua especialidade
	 */
	public boolean liberaMedicamento(Profissional profissionalVerificacao){
		List<Especialidade> especialidadeMedicamentos = getEspecialidadeMedicamento(getPrescricaoItem().getMaterial());
		if(especialidadeMedicamentos.contains(profissionalVerificacao.getEspecialidade()) || especialidadeMedicamentos.isEmpty()){
			if(isMaterialAntibiotico(getPrescricaoItem() != null ? getPrescricaoItem().getMaterial() : null) && getControleMedicacaoRestritoSCHI().getTipoIndicacao() == null){
				usuario = null;
				senha = null;
				mostraModalLiberacaoMedicamento = true;
				mensagem = "Esse material é um antibiótico.";
				mensagemComplementar = "Preencha o formulário para liberar o antibiótico.";
				return false;
			}else{
				profissionalLiberacao = profissionalVerificacao;
				mostraModalLiberacaoMedicamento = false;
				return true;
			}
		}else{
			usuario = null;
			senha = null;
			mostraModalLiberacaoMedicamento = true;
			mensagem = "O usuário não tem autorização para liberar o material.";
			mensagemComplementar = "Peça autorização para um " + getListaEspecialidadeString() + ".";
			return false;
		}
	}
	
	private void gravaPrescricaoItemDose() {
		try{
			iniciarTransacao();
			Calendar dataReferencia = Calendar.getInstance();
			dataReferencia.setTime(getDataInicio());
			for(int i = 0; i < getQuantidadeDoses(); i++){
				PrescricaoItemDose temp = new PrescricaoItemDose();
				temp.setDataDose(dataReferencia.getTime());
				temp.setPeriodo(getIntervaloEntreDoses());
				temp.setQuantidade(getQuantidadePorDose());
				dataReferencia.add(Calendar.HOUR, getIntervaloEntreDoses());
				temp.setPrescricaoItem(getPrescricaoItem());
				session.save(temp);
			}
			session.flush();  
			tx.commit();
			limpaVariaveis();
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu ao gravar a o item da prescrição.", "Utilize o material de apoio para precrever até o sistema voltar ao normal."));
			gravaErroAplicacao(new Date(), e.getMessage(), e.getStackTrace(), "gravaPrescricaoItemDose()");
			session.getTransaction().rollback();
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
	}

	private String geraReferenciaUnica() {
		//a string da referência tem o seguinte formato <idPaciente>-<idMaterial>-<idUnidade>-<idProfissional>-<idPrescricao>
		String numeroReceita; 
		numeroReceita = String.valueOf(getInstancia().getPaciente().getIdPaciente()).concat("-");
		numeroReceita = numeroReceita.concat(String.valueOf(prescricaoItem.getMaterial().getIdMaterial())).concat("-");
		numeroReceita = numeroReceita.concat(String.valueOf(Autenticador.getInstancia().getUnidadeAtual().getIdUnidade())).concat("-");
		numeroReceita = numeroReceita.concat(String.valueOf(Autenticador.getInstancia().getProfissionalAtual().getIdProfissional())).concat("-");
		numeroReceita = numeroReceita.concat(String.valueOf(getInstancia().getIdPrescricao()));
		return numeroReceita;
	}

	private void inicializaPrescricaoItemDoses() {
		if(getPrescricaoItem().getPrescricaoItemDoses() == null){
			getPrescricaoItem().setPrescricaoItemDoses(new HashSet<PrescricaoItemDose>());
		}
	}

	public void cancelaDosagem(){
		if(removePrescricaoItem()){
			limpaVariaveis();
			iniciaDosagem = false;
			setPrescricaoItem(new PrescricaoItem());
		}
	}
	
	public void finalizaDosagem(){
		if(getListaPrescricaoItemDose(getPrescricao()).size() > 0){
			try {
				boolean controleExistente = getControleMedicacaoRestritoSCHI().getIdControleMedicacaoRestritoSCHI() != 0;
				if(controleExistente ||  geraLiberacaoAntibiotico()){
					if(!isMaterialAntibiotico(getPrescricaoItem() != null ? getPrescricaoItem().getMaterial() : null) || anexaControleNaPrescricaoItem()){
						limpaVariaveis();
						setPrescricaoItem(new PrescricaoItem());
						setControleMedicacaoRestritoSCHI(new ControleMedicacaoRestritoSCHI());
						iniciaDosagem = false;
						setControleMedicacaoRestritoSCHI(new ControleMedicacaoRestritoSCHI());
						String paginaAtual = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
						FacesContext.getCurrentInstance().getExternalContext().redirect(paginaAtual);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe as doses da prescrição.", ""));
		}
	}
	
	public void finalizarPrescricao(){
		getInstancia().setDispensavel(TipoStatusEnum.S);
		getInstancia().setDataPrescricao(new Date());
		if(super.atualizar()){
			novaInstancia();
		}
	}
	
	private boolean anexaControleNaPrescricaoItem(){
		getPrescricaoItem().setControleMedicacaoRestritoSCHI(getControleMedicacaoRestritoSCHI());
		if(new PrescricaoItemHome(getPrescricaoItem(), false).atualizar()){
			return true;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao anexar a liberação à prescrição.", "Tente novamente ou procure o CPD."));
		return false;
	}
	
	private boolean gravaPrescricaoItem() {
		boolean ret = false;
		
		try{
			iniciarTransacao();
			getPrescricaoItem().setPrescricao(getInstancia());
			getPrescricaoItem().setReferenciaUnica(geraReferenciaUnica());
			getPrescricaoItem().setProfissionalLiberacao(profissionalLiberacao);
			getPrescricaoItem().setDispensado(TipoStatusEnum.N);
			session.save(getPrescricaoItem());
			session.flush();  
			tx.commit();
			ret = true;
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu ao gravar a o item da prescrição.", "Utilize o material de apoio para precrever até o sistema voltar ao normal."));
			gravaErroAplicacao(new Date(), e.getMessage(), e.getStackTrace(), "gravaPrescricaoItem()");
			session.getTransaction().rollback();
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		return ret;
	}

	private boolean gravaPrescricao() {
		boolean ret = false;
		try{
			iniciarTransacao();
			carregaPrescricao();
			getInstancia().setDispensavel(TipoStatusEnum.N);
			getInstancia().setDispensado(TipoStatusEnum.N);
			session.save(getInstancia());
			session.merge(getInstancia());
			session.flush();
			tx.commit();
			iniciaDosagem = true;
			ret = true;
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu ao iniciar a prescrição.", "Utilize o material de apoio para precrever até o sistema voltar ao normal."));
			iniciaDosagem = false;
			gravaErroAplicacao(new Date(), e.getMessage(), e.getStackTrace(), "gravaPrescricao()");
			session.getTransaction().rollback();
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		return ret;
	}
	
	protected void gravaErroAplicacao(Date date, String message, StackTraceElement[] stackTrace, String metodo) {
		ErroAplicacao ea = new ErroAplicacao();
		ea.setAtendido(TipoStatusEnum.N);
		ea.setDataOcorrencia(date);
		ea.setMessage(message);
		ea.setMetodo(metodo);
		String pagina = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
		ea.setPagina(pagina);
		ea.setStackTrace(stackTrace.toString());
		ea.setUsuario(Autenticador.getInstancia().getUsuarioAtual());
		new ErroAplicacaoHome(ea).enviar();
	}
	
	private void carregaPrescricao() {
		getInstancia().setAno(Calendar.getInstance().get(Calendar.YEAR));
		getInstancia().setDataInclusao(new Date());
		getInstancia().setProfissional(Autenticador.getInstancia().getProfissionalAtual());
		getInstancia().setUnidade(Autenticador.getInstancia().getUnidadeAtual());
		getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
	}

	public void cancelarPrescricao(){
		if(super.apagar()){
			novaInstancia();
		}
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

	public List<PrescricaoItem> getPrescricaoItensList(){
		ConsultaGeral<PrescricaoItem> cg = new ConsultaGeral<PrescricaoItem>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idPrecricao", getInstancia().getIdPrescricao());
		return (List<PrescricaoItem>) cg.consulta(new StringBuilder("select o from PrescricaoItem o where o.prescricao.idPrescricao = :idPrecricao"), hm);
	}
	
	public long quantidadeDosesPrescricaoItem(PrescricaoItem pi){
		ConsultaGeral<Object[]> cg = new ConsultaGeral<Object[]>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idPrecricaoItem", pi.getIdPrescricaoItem());
		Object total = cg.consultaUnica(new StringBuilder("select count(o) from PrescricaoItemDose o where o.prescricaoItem.idPrescricaoItem = :idPrecricaoItem"), hm);
		return (Long) total;
	}
	
	public boolean isIniciaDosagem() {
		return iniciaDosagem;
	}

	public void setIniciaDosagem(boolean iniciaDosagem) {
		this.iniciaDosagem = iniciaDosagem;
	}

	public boolean isMostraModalLiberacaoMedicamento() {
		return mostraModalLiberacaoMedicamento;
	}

	public void setMostraModalLiberacaoMedicamento(
			boolean mostraModalLiberacaoMedicamento) {
		this.mostraModalLiberacaoMedicamento = mostraModalLiberacaoMedicamento;
	}

	public ControleMedicacaoRestritoSCHI getControleMedicacaoRestritoSCHI() {
		return controleMedicacaoRestritoSCHI;
	}

	public void setControleMedicacaoRestritoSCHI(
			ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI) {
		this.controleMedicacaoRestritoSCHI = controleMedicacaoRestritoSCHI;
	}
}
