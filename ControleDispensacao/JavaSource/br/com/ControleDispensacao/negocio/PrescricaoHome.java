package br.com.ControleDispensacao.negocio;

import java.io.IOException;
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

import br.com.ControleDispensacao.comparador.DoseDataComparador;
import br.com.ControleDispensacao.entidade.ErroAplicacao;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.entidade.PrescricaoItemDose;
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
	
	private void limpaVariaveis(){
		setDataInicio(null);
		setQuantidadeDoses(null);
		setQuantidadePorDose(null);
		setIntervaloEntreDoses(null);
	}
	
	public List<Prescricao> getListaPrescricaoPendente(){
		return getBusca("select o from Prescricao o where o.dispensado = 'N'");
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
	
	public void adicionaDoses(){
		if(liberaDose()){
			inicializaPrescricaoItemDoses();
			if(getPrescricaoItem().getIdPrescricaoItem() != 0 || gravaPrescricaoItem()){
				gravaPrescricaoItemDose();
			}
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
				temp.setDispensado(TipoStatusEnum.N);
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
		Object[] totais = consultaEstoque();
		Integer estoqueAtual = (Integer) totais[0] - (Integer) totais[1];
		return !estoqueVazio(estoqueAtual) && !estoqueInsuficiente(estoqueAtual);
	}

	private boolean estoqueInsuficiente(Integer quantidade) {
		int quantidadeReserva = getQuantidadeDoses() * getQuantidadePorDose();
		if(quantidadeReserva > quantidade){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não há quantidade suficiente no estoque. O máximo disponível é de " + String.valueOf(quantidade), ""));
			return true;
		}else{
			return false;
		}
	}

	private boolean estoqueVazio(Integer quantidade) {
		if(quantidade > 0){
			return false;
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Este medicamento está em falta.", ""));
			return true;
		}
	}

	private Object[] consultaEstoque() {
		StringBuilder sb = new StringBuilder("select CASE WHEN sum(o.quantidade) = null THEN 0 ELSE sum(o.quantidade)END, ");
		sb.append("(select CASE WHEN sum(a.quantidade) = null THEN 0 ELSE sum(a.quantidade) END ");
		sb.append("from PrescricaoItemDose a where a.dispensado = 'N' and a.prescricaoItem.material.idMaterial = :idMaterial) ");
		sb.append("from Estoque o where o.material.idMaterial = :idMaterial");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("idMaterial", getPrescricaoItem().getMaterial().getIdMaterial());
		
		ConsultaGeral<Object[]> cg = new ConsultaGeral<Object[]>();
		return cg.consultaUnica(sb, map);
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
			getPrescricaoItem().setPrescricaoItemDoses(new HashSet<PrescricaoItemDose>());
			iniciaDosagem = false;
		}
	}
	
	public void finalizaDosagem(){
		if(getListaPrescricaoItemDose().size() > 0){
			try {
				limpaVariaveis();
				setPrescricaoItem(new PrescricaoItem());
				iniciaDosagem = false;
				String paginaAtual = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
				FacesContext.getCurrentInstance().getExternalContext().redirect(paginaAtual);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe as doses da prescrição.", ""));
		}
	}
	
	public void finalizarPrescricao(){
		getInstancia().setDispensavel(TipoStatusEnum.S);
		if(super.atualizar()){
			novaInstancia();
		}
	}
	
	private boolean gravaPrescricaoItem() {
		boolean ret = false;
		
		try{
			iniciarTransacao();
			getPrescricaoItem().setPrescricao(getInstancia());
			getPrescricaoItem().setReferenciaUnica(geraReferenciaUnica());
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
			session.save(getInstancia());
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
		ConsultaGeral<PrescricaoItemDose> cg = new ConsultaGeral<PrescricaoItemDose>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idPrecricaoItem", getPrescricaoItem().getIdPrescricaoItem());
		return (List<PrescricaoItemDose>) cg.consulta(new StringBuilder("select o from PrescricaoItemDose o where o.prescricaoItem.idPrescricaoItem = :idPrecricaoItem"), hm);
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
}
