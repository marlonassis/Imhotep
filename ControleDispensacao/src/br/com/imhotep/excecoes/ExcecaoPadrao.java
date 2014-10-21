package br.com.imhotep.excecoes;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ExcecaoPadrao extends Exception {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3504501854362894814L;

	public ExcecaoPadrao(){
		super();
	}
	
	public ExcecaoPadrao(String msg, String msg2){
		super();
		mensagem(msg, msg2);
	}
	
	protected void mensagem(String msg, String msg2){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, msg2));
	}
	
}
