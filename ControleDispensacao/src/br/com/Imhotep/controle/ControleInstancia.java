package br.com.Imhotep.controle;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class ControleInstancia {
	
	public Object instancia(String nome){
		if(FacesContext.getCurrentInstance() != null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			if(session != null){
				Object attribute = session.getAttribute(nome);
				return attribute;
			}
		}
		return null;
	}
	
	public Object procuraInstancia(Class<?> classe) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(session != null){
			Object attribute = session.getAttribute(classe.getSimpleName().toLowerCase());
			if(attribute == null)
				attribute = criaInstancia(classe, session);
			return attribute;
		}else{
			return null;
		}
	}

	private Object criaInstancia(Class<?> classe, HttpSession session) throws InstantiationException, IllegalAccessException {
		Object attribute = session.getAttribute(primeiraLetraMinuscula(classe.getSimpleName()));
		if (attribute == null){
			session.setAttribute(primeiraLetraMinuscula(classe.getSimpleName()), classe.newInstance());
			attribute = session.getAttribute(primeiraLetraMinuscula(classe.getSimpleName()));
		}
		return attribute;
	}
	
	private String primeiraLetraMinuscula(String palavra) {    
	      return palavra.substring(0,1).toLowerCase().concat(palavra.substring(1));    
	} 
}
