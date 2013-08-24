package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EhealthFormularioTecnologia;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class EhealthFormularioTecnologiaRaiz extends PadraoHome<EhealthFormularioTecnologia>{
	
	public EhealthFormularioTecnologiaRaiz(){
		super();
	}
	
	public EhealthFormularioTecnologiaRaiz(EhealthFormularioTecnologia ehealthFormularioTecnologia){
		super();
		setInstancia(ehealthFormularioTecnologia);
	}
	
	public void remover(EhealthFormularioTecnologia eft){
		setInstancia(eft);
		super.apagar();
	}
	
}
