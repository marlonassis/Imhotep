package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EhealthFormularioRedeSocial;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class EhealthFormularioRedeSocialRaiz extends PadraoHome<EhealthFormularioRedeSocial>{
	
	public EhealthFormularioRedeSocialRaiz(){
		super();
	}
	
	public EhealthFormularioRedeSocialRaiz(EhealthFormularioRedeSocial ehealthFormularioRedeSocial){
		super();
		setInstancia(ehealthFormularioRedeSocial);
	}
	
	public void remover(EhealthFormularioRedeSocial efrs){
		setInstancia(efrs);
		super.apagar();
	}
}
