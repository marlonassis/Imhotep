package br.com.imhotep.auxiliar;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.imhotep.excecoes.ExcecaoForaRedeHU;

public class RestringirAcessoRedeHU {
	
	public void validarAcessoRedeHU() throws ExcecaoForaRedeHU{
		verificaIPSolicitacao();
	}
	
	private void verificaIPSolicitacao() throws ExcecaoForaRedeHU {
		FacesContext fc = FacesContext.getCurrentInstance();  
		HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();  
		String ip = request.getRemoteAddr();
		if(!ip.equals(Constantes.IP_HU) && !ip.equals(Constantes.IP_LOCAL)){
			throw new ExcecaoForaRedeHU();
		}
	}
	
}
