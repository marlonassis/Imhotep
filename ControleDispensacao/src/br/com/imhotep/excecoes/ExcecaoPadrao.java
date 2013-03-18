package br.com.imhotep.excecoes;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ExcecaoPadrao extends Exception {
	
	private static final long serialVersionUID = 6114104791961625068L;

	protected void mensagem(String msg, String msg2){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, msg2));
	}
	
}
