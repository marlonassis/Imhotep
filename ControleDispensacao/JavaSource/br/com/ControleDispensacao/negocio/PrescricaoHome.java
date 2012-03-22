package br.com.ControleDispensacao.negocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import br.com.ControleDispensacao.entidade.ReservaMaterialPrescricao;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
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
	
	public List<Prescricao> getListaPrescricaoPendente(){
		return getBusca("select o from Prescricao o where o.dispensado = 'N'");
	}
	
	public void iniciaDosagem(){
		limpaVariaveis();
		setPrescricaoItem(new PrescricaoItem());
		getPrescricaoItem().setPrescricaoItemDoses(new HashSet<PrescricaoItemDose>());
	}
	
	public void removeDose(PrescricaoItemDose linha){
		getPrescricaoItem().getPrescricaoItemDoses().remove(linha);
	}
	
	public void removePrescricaoItem(PrescricaoItem linha){
		removeReserva(linha.getReferenciaUnica());
		prescricaoItens.remove(linha);
	}
	
	private void removeReserva(String referencia) {
		ReservaMaterialPrescricao obj = new ConsultaGeral<ReservaMaterialPrescricao>(new StringBuilder("select o from ReservaMaterialPrescricao o where o.referenciaUnica = '"+referencia+"'"), null).consultaUnica();
		ReservaMaterialPrescricaoHome rh = new ReservaMaterialPrescricaoHome();
		rh.setInstancia(obj);
		rh.apagar();
	}
	
	public void adicionaDoses(){
		if(liberaDose()){
			inicializaPrescricaoItemDoses();
			Calendar dataReferencia = Calendar.getInstance();
			dataReferencia.setTime(getDataInicio());
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
		sb.append("from ReservaMaterialPrescricao a where a.dispensado = 'N' and a.material.idMaterial = :idMaterial) ");
		sb.append("from Estoque o where o.material.idMaterial = :idMaterial");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("idMaterial", getPrescricaoItem().getMaterial().getIdMaterial());
		
		ConsultaGeral<Object[]> cg = new ConsultaGeral<Object[]>();
		return cg.consultaUnica(sb, map);
	}

	private String geraReferenciaUnica() {
		//a string da referência tem o seguinte formato <idPaciente>-<idMaterial>-<idUnidade>-<idProfissional>-<hash da lista de doses>
		String numeroReceita; 
		numeroReceita = String.valueOf(getInstancia().getPaciente().getIdPaciente()).concat("-");
		numeroReceita = numeroReceita.concat(String.valueOf(prescricaoItem.getMaterial().getIdMaterial())).concat("-");
		numeroReceita = numeroReceita.concat(String.valueOf(Autenticador.getInstancia().getUnidadeAtual().getIdUnidade())).concat("-");
		numeroReceita = numeroReceita.concat(String.valueOf(Autenticador.getInstancia().getProfissionalAtual().getIdProfissional())).concat("-");
		numeroReceita = numeroReceita.concat(String.valueOf(getPrescricaoItem().getPrescricaoItemDoses().hashCode()));
		return numeroReceita;
	}

	private void inicializaPrescricaoItemDoses() {
		if(getPrescricaoItem().getPrescricaoItemDoses() == null){
			getPrescricaoItem().setPrescricaoItemDoses(new HashSet<PrescricaoItemDose>());
		}
	}
	
	private int quantidadeReserva(){
		int total=0;
		for(PrescricaoItemDose pid : getPrescricaoItem().getPrescricaoItemDoses()){
			total += pid.getQuantidade();
		}
		return total;
	}
	
	private void gerarReserva() {
		ReservaMaterialPrescricao rmp = new ReservaMaterialPrescricao();
		rmp.setReferenciaUnica(geraReferenciaUnica());
		rmp.setDispensado(TipoStatusEnum.N);
		rmp.setMaterial(getPrescricaoItem().getMaterial());
		rmp.setDataReserva(new Date());
		rmp.setQuantidade(quantidadeReserva());
		ReservaMaterialPrescricaoHome rh = new ReservaMaterialPrescricaoHome();
		rh.setInstancia(rmp);
		rh.enviar();
	}

	public void cancelaDosagem(){
		limpaVariaveis();
		getPrescricaoItem().setPrescricaoItemDoses(new HashSet<PrescricaoItemDose>());
	}
	
	public void finalizaDosagem(){
		if(getPrescricaoItem().getPrescricaoItemDoses().size() > 0){
			try {
				gerarReserva();
				getPrescricaoItem().setReferenciaUnica(geraReferenciaUnica());
				
				getPrescricaoItens().add(getPrescricaoItem());
				limpaVariaveis();
				setPrescricaoItem(new PrescricaoItem());
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ControleDispensacao/PaginasWeb/Prescricao/Prescricao/prescricao.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe as doses da prescrição.", ""));
		}
	}
	
	public void finalizarPrescricao(){
		if(prescricaoItens.size() == 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não há itens prescritos para finalizar prescrição.", ""));
			return;
		}
		try{
			iniciarTransacao();
			carregaPrescricao();
			getInstancia().setDispensado(TipoStatusEnum.N);
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
		getInstancia().setNumero("1");
	}

	public void cancelarPrescricao(){
		
		for(PrescricaoItem pi : prescricaoItens){
			removeReserva(pi.getReferenciaUnica());
		}
		
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
