package br.com.ControleDispensacao.fluxo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.ControleDispensacao.auxiliar.Constantes;
import br.com.ControleDispensacao.auxiliar.ControlePrescricaoItem;
import br.com.ControleDispensacao.auxiliar.ControlePrescricaoItemDose;
import br.com.ControleDispensacao.comparador.DoseDataComparador;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.entidade.PrescricaoItemDose;
import br.com.ControleDispensacao.entidadeExtra.Dose;
import br.com.ControleDispensacao.negocio.EstoqueHome;
import br.com.ControleDispensacao.negocio.PrescricaoHome;
import br.com.ControleDispensacao.negocio.PrescricaoItemDoseHome;
import br.com.ControleDispensacao.negocio.PrescricaoItemHome;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoFluxo;

@ManagedBean(name="fluxoPrescricaoMedicamento")
@RequestScoped
public class FluxoPrescricaoMedicamento extends PadraoFluxo{
	
	private String fluxoAtualPrescricao = PrescricaoHome.getInstanciaHome().getFluxoAtualPrescricao();
	
	private Dose dose = new Dose();
	private Prescricao prescricaoAtual = PrescricaoHome.getInstanciaHome().getPrescricaoAtual();
	
	public void removePrescricaoItem(PrescricaoItem linha){
		PrescricaoItemHome pih = new PrescricaoItemHome();
		pih.setInstancia(linha);
		pih.apagar();
	}
	
	public void inserirItem(){
			if(!formularioDoseVazio(getDose()) && liberaDose(getDose().getPrescricaoItem().getMaterial(), getDose())){
				if(prescricaoAtual.getIdPrescricao() == 0){
					super.mensagem("Ocorrreu erro ao gravar a prescrição.", "", FacesMessage.SEVERITY_ERROR);
					return;
				}
				getDose().getPrescricaoItem().setPrescricao(prescricaoAtual);
				if(!new ControlePrescricaoItem().gravaPrescricaoItem(getDose().getPrescricaoItem())){
					super.mensagem("Ocorrreu erro ao gravar a prescrição item.", "", FacesMessage.SEVERITY_ERROR);
					return;
				}
				if(!new ControlePrescricaoItemDose().gravaPrescricaoItemDose(getDose())){
					super.mensagem("Ocorrreu erro ao gravar a dose.", "", FacesMessage.SEVERITY_ERROR);
					return;
				}
				novaDose();
			}
	}
	
	private boolean liberaDose(Material material, Dose dose) {
		EstoqueHome eh = new EstoqueHome();
		Object[] totais = eh.consultaEstoque(material);
		Integer estoqueAtual = (Integer) totais[0] - (Integer) totais[1];
		return !eh.estoqueVazio(estoqueAtual) && !eh.estoqueInsuficiente(estoqueAtual, dose.getQuantidadeDoses(), dose.getQuantidadePorDose());
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
	
	public List<PrescricaoItemDose> getPrescricaoItemDoseList2(){
			List<PrescricaoItemDose> pidList = getListaPrescricaoItemDose(prescricaoAtual);
			if(pidList != null){
				Collections.sort(pidList, new DoseDataComparador());
				return pidList;
			}
			return null;
	}
	
	public void removeDose(PrescricaoItemDose linha){
		PrescricaoItemDoseHome pidh = new PrescricaoItemDoseHome();
		pidh.setInstancia(linha);
		pidh.apagar();
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
	
	private void novaDose() {
		setDose(new Dose());
	}

	public Dose getDose() {
		return dose;
	}

	public void setDose(Dose dose) {
		this.dose = dose;
	}
}
