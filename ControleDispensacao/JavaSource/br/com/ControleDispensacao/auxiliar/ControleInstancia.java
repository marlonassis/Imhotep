package br.com.ControleDispensacao.auxiliar;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class ControleInstancia<T> {
	@SuppressWarnings("unchecked")
	public T instancia(String nome){
		if(FacesContext.getCurrentInstance() != null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
			if(session != null){
				T attribute = (T) session.getAttribute(nome);
				return attribute;
			}
		}
		return null;
	}
}
