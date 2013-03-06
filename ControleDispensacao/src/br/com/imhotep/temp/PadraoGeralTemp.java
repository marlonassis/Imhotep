package br.com.imhotep.temp;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class PadraoGeralTemp {

	protected void mensagem(String msg, String msg2, Severity tipoMensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipoMensagem,msg, msg2));
	}
	
}
