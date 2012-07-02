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
import javax.servlet.http.HttpServletRequest;

import br.com.ControleDispensacao.auxiliar.Constantes;
import br.com.ControleDispensacao.comparador.DoseDataComparador;
import br.com.ControleDispensacao.entidade.ControleMedicacaoRestritoSCHI;
import br.com.ControleDispensacao.entidade.ErroAplicacao;
import br.com.ControleDispensacao.entidade.Especialidade;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.entidade.PrescricaoItemDose;
import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.entidade.Usuario;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="prescricaoHome")
@SessionScoped
public class PrescricaoHome extends PadraoHome<Prescricao>{    	
	
	private PrescricaoItem prescricaoItem = new PrescricaoItem();
	private Date dataInicio;
	private Integer quantidadeDoses;
	private Integer quantidadePorDose;
	private Integer intervaloEntreDoses;
	private boolean iniciaDosagem;
	private boolean mostraModalLiberacaoMedicamento;
	private Profissional profissionalLiberacao;
	private String usuario;
	private String senha;
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
		setInstancia(getBusca("select o from Prescricao o where o.idPrescricao = " + getInstancia().getIdPrescricao()).get(0));
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
		if(isMaterialAntibiotico()){
			if(new ControleMedicacaoRestritoSCHIHome(getControleMedicacaoRestritoSCHI()).enviar()){
				return true;
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao gravar a liberação para antibiótico.", "Tente novamente ou procure o CPD."));
			return false;
		}
		return true;
	}
	
	private void adicionaDoses(Profissional profissionalLiberacao){
		if(liberaDose()){
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
	
	public boolean isMaterialAntibiotico(){
		if(getPrescricaoItem() != null && getPrescricaoItem().getMaterial() != null){
			String grupoMaterial = getPrescricaoItem().getMaterial().getFamilia().getSubGrupo().getGrupo().getDescricao();
			return grupoMaterial.equalsIgnoreCase(Constantes.MATERIAL_ANTIBIOTICO);
		}
		return false;
	}
	
	/**
	 * Verifica se o medicamento pode ser prescrito pelo profissional logado usando sua especialidade
	 */
	public boolean liberaMedicamento(Profissional profissionalVerificacao){
		List<Especialidade> especialidadeMedicamentos = getEspecialidadeMedicamento(getPrescricaoItem().getMaterial());
		if(especialidadeMedicamentos.contains(profissionalVerificacao.getEspecialidade()) || especialidadeMedicamentos.isEmpty()){
			if(isMaterialAntibiotico() && getControleMedicacaoRestritoSCHI().getTipoIndicacao() == null){
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

	private boolean liberaDose() {
		EstoqueHome eh = new EstoqueHome();
		Object[] totais = eh.consultaEstoque(getPrescricaoItem().getMaterial());
		Integer estoqueAtual = (Integer) totais[0] - (Integer) totais[1];
		return !eh.estoqueVazio(estoqueAtual) && !eh.estoqueInsuficiente(estoqueAtual, getQuantidadeDoses(), getQuantidadePorDose());
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
		if(getListaPrescricaoItemDose().size() > 0){
			try {
				boolean controleExistente = getControleMedicacaoRestritoSCHI().getIdControleMedicacaoRestritoSCHI() != 0;
				if(controleExistente ||  geraLiberacaoAntibiotico()){
					if(!isMaterialAntibiotico() || anexaControleNaPrescricaoItem()){
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
			getInstancia().setNumero(String.valueOf(getInstancia().getIdPrescricao()));
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
		getInstancia().setNumero("1");
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
	
	/**
	 * busca a lista das doses da prescrição atual
	 * @return lista de prescricaoItemDose
	 */
	private List<PrescricaoItemDose> getListaPrescricaoItemDose(){
		if(getPrescricaoItem() != null){
			ConsultaGeral<PrescricaoItemDose> cg = new ConsultaGeral<PrescricaoItemDose>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrecricaoItem", getPrescricaoItem().getIdPrescricaoItem());
			return (List<PrescricaoItemDose>) cg.consulta(new StringBuilder("select o from PrescricaoItemDose o where o.prescricaoItem.idPrescricaoItem = :idPrecricaoItem"), hm);
		}
		return null;
	}
	
	public long quantidadeDosesPrescricaoItem(PrescricaoItem pi){
		ConsultaGeral<Object[]> cg = new ConsultaGeral<Object[]>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idPrecricaoItem", pi.getIdPrescricaoItem());
		Object total = cg.consultaUnica(new StringBuilder("select count(o) from PrescricaoItemDose o where o.prescricaoItem.idPrescricaoItem = :idPrecricaoItem"), hm);
		return (Long) total;
	}
	
	public List<PrescricaoItemDose> getPrescricaoItemDoseList(){
		List<PrescricaoItemDose> pidList = getListaPrescricaoItemDose();
		if(pidList != null){
			Collections.sort(pidList, new DoseDataComparador());
			return pidList;
		}else{
			return null;
		}
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

	public void setControleMedicacaoRestritoSCHI(
			ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI) {
		this.controleMedicacaoRestritoSCHI = controleMedicacaoRestritoSCHI;
	}
}
