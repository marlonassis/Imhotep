package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.entidade.PrescricaoItemDose;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="prescricaoHome")
@SessionScoped
public class PrescricaoHome extends PadraoHome<Prescricao>{
	private PrescricaoItem prescricaoItem = new PrescricaoItem();
	private List<PrescricaoItem> prescricaoItens = new ArrayList<PrescricaoItem>();
	private PrescricaoItemDose prescricaoItemDose = new PrescricaoItemDose(); 
	
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		limpaHome();
	}

	public void removePrescricaoItemDose(PrescricaoItemDose prescricaoItemDose){
		prescricaoItem.getPrescricaoItemDoses().remove(prescricaoItemDose);
	}
	
	public void removePrescricaoItem(PrescricaoItem prescricaoItem){
		prescricaoItens.remove(prescricaoItem);
	}
	
	public void addPrescricaoItemDoses(){
		if(prescricaoItem.getPrescricaoItemDoses() == null){
			prescricaoItem.setPrescricaoItemDoses(new ArrayList<PrescricaoItemDose>());
		}
		prescricaoItem.getPrescricaoItemDoses().add(prescricaoItemDose);
		prescricaoItemDose = null;
	}
	
	public void addPrescricaoItens(){
		prescricaoItens.add(prescricaoItem);
		prescricaoItem = null;
	}
	
	public void cancelarDosagem(){
		prescricaoItemDose = null;
		prescricaoItem.setPrescricaoItemDoses(null);
	}
	
	@Override
	public boolean enviar() {
		if(super.enviar()){
			limpaHome();
		}
		return false;
	}

	private void limpaHome() {
		prescricaoItem = new PrescricaoItem();
		prescricaoItens = new ArrayList<PrescricaoItem>();
	}
	
	public PrescricaoItem getPrescricaoItem() {
		return prescricaoItem;
	}
	public void setPrescricaoItem(PrescricaoItem prescricaoItem) {
		this.prescricaoItem = prescricaoItem;
	}
	
	public List<PrescricaoItem> getPrescricaoItens() {
		return prescricaoItens;
	}
	public void setPrescricaoItens(List<PrescricaoItem> prescricaoItens) {
		this.prescricaoItens = prescricaoItens;
	}

	public PrescricaoItemDose getPrescricaoItemDose() {
		return prescricaoItemDose;
	}

	public void setPrescricaoItemDose(PrescricaoItemDose prescricaoItemDose) {
		this.prescricaoItemDose = prescricaoItemDose;
	}
}
