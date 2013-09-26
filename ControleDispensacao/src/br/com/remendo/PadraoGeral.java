package br.com.remendo;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import br.com.remendo.gerenciador.GerenciadorConexao;

public class PadraoGeral extends GerenciadorConexao {

	protected void mensagem(String msg, String msg2, Severity tipoMensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipoMensagem,msg, msg2));
	}
	
}
