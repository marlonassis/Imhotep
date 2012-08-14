package br.com.ControleDispensacao.auxiliar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FlowEvent;

import br.com.ControleDispensacao.entidade.Paciente;
import br.com.ControleDispensacao.entidade.Prescricao;

@ManagedBean(name="controlePrescricao")
@SessionScoped
public class ControlePrescricao {
	
	private boolean skip;
	private Paciente paciente;
	private Prescricao prescricao = new Prescricao();
	
	private void inciarPrescricao(){
		
	}
	
	public void adicionarDose(){
		
	}
	
	public void save(ActionEvent actionEvent) {
		//Persist user
		
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
	
}
