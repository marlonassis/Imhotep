package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.EhealthFormulario;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EhealthFormularioRaiz extends PadraoRaiz<EhealthFormulario>{
	
	
	
	
	
	
	public static EhealthFormularioRaiz getInstanciaAtual(){
		try {
			return (EhealthFormularioRaiz) Utilitarios.procuraInstancia(EhealthFormularioRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
}
