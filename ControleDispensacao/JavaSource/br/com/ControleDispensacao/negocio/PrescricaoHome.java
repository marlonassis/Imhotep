package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.ControleDispensacao.comparador.DoseDataComparador;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.entidade.PrescricaoItemDose;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="prescricaoHome")
@SessionScoped
public class PrescricaoHome extends PadraoHome<Prescricao>{    
	
	private Set<PrescricaoItem> prescricaoItens = new HashSet<PrescricaoItem>();
	private PrescricaoItem prescricaoItem = new PrescricaoItem();
	private Date dataInicio;
	private Integer quantidadeDoses;
	private Integer periodoDosagem;
	private Integer quantidadePorDose;
	private Integer intervaloEntreDoses;
	
	private void limpaVariaveis(){
		setDataInicio(null);
		setQuantidadeDoses(null);
		setPeriodoDosagem(null);
		setQuantidadePorDose(null);
		setIntervaloEntreDoses(null);
	}
	
	public void iniciaDosagem(){
		setPrescricaoItem(new PrescricaoItem());
		getPrescricaoItem().setPrescricaoItemDoses(new HashSet<PrescricaoItemDose>());
	}
	
	public void removeDose(PrescricaoItemDose linha){
		getPrescricaoItem().getPrescricaoItemDoses().remove(linha);
	}
	
	public void removePrescricaoItem(PrescricaoItem linha){
		prescricaoItens.remove(linha);
	}
	
	public void adicionaDoses(){
		Calendar dataReferencia = Calendar.getInstance();
		dataReferencia.setTime(getDataInicio());
		
		if(getPrescricaoItem().getPrescricaoItemDoses() == null){
			getPrescricaoItem().setPrescricaoItemDoses(new HashSet<PrescricaoItemDose>());
		}
		
		for(int i = 0; i < getQuantidadeDoses(); i++){
			PrescricaoItemDose temp = new PrescricaoItemDose();
			temp.setDataDose(dataReferencia.getTime());
			temp.setPeriodo(getPeriodoDosagem());
			temp.setQuantidade(getQuantidadePorDose());
			dataReferencia.add(Calendar.HOUR, getIntervaloEntreDoses());
			getPrescricaoItem().getPrescricaoItemDoses().add(temp);
		}
		limpaVariaveis();
	}
	
	public void cancelaDosagem(){
		limpaVariaveis();
		getPrescricaoItem().setPrescricaoItemDoses(new HashSet<PrescricaoItemDose>());
	}
	
	public void finalizaDosagem(){
		getPrescricaoItens().add(getPrescricaoItem());
		limpaVariaveis();
		setPrescricaoItem(new PrescricaoItem());
	}
	
	public void finalizarPrescricao(){
		if(Autenticador.getInstancia().getUnidadeAtual() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Você não está alocado em uma unidade", "Escolha uma unidade na combo acima do menu."));
			return;
		}
		try{
			iniciarTransacao();
			carregaPrescricao();
			session.save(getInstancia());
			for(PrescricaoItem item : prescricaoItens){
				item.setPrescricao(getInstancia());
				session.save(item);
				for(PrescricaoItemDose item2 : item.getPrescricaoItemDoses()){
					item2.setPrescricaoItem(item);
					item2.setPeriodo(1);
					session.save(item2);
				}
			}
			session.flush();  
			tx.commit(); 
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Prescrição cadastrada com sucesso!", "Paciente: " + getInstancia().getPaciente().getNome()));
			finalizaPersistenciaPrescricao();
		}catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu um erro ao cadastrar a prescrição", e.getMessage()));
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		
	}
	
	private void finalizaPersistenciaPrescricao() {
		novaInstancia();
		prescricaoItens = new HashSet<PrescricaoItem>();
	}

	private void carregaPrescricao() {
		getInstancia().setAno(Calendar.getInstance().get(Calendar.YEAR));
		getInstancia().setDataInclusao(new Date());
		getInstancia().setProfissional(Autenticador.getInstancia().getProfissionalAtual());
		getInstancia().setUnidade(Autenticador.getInstancia().getUnidadeAtual());
		getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setNumero(1);
	}

	public void cancelarPrescricao(){
		novaInstancia();
		prescricaoItens = new HashSet<PrescricaoItem>();
	}
	
	public Set<PrescricaoItem> getPrescricaoItens() {
		return prescricaoItens;
	}

	public void setPrescricaoItens(Set<PrescricaoItem> prescricaoItens) {
		this.prescricaoItens = prescricaoItens;
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

	public Integer getPeriodoDosagem() {
		return periodoDosagem;
	}

	public void setPeriodoDosagem(Integer periodoDosagem) {
		this.periodoDosagem = periodoDosagem;
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
		return new ArrayList<PrescricaoItem>(prescricaoItens);
	}
	
	public List<PrescricaoItemDose> getPrescricaoItemDoseList(){
		Set<PrescricaoItemDose> prescricaoItemDoses = getPrescricaoItem() == null ? null : getPrescricaoItem().getPrescricaoItemDoses();
		if(prescricaoItemDoses != null){
			ArrayList<PrescricaoItemDose> doses = new ArrayList<PrescricaoItemDose>(prescricaoItemDoses);
			Collections.sort(doses, new DoseDataComparador());
			return doses;
		}else{
			return null;
		}
	}
}
