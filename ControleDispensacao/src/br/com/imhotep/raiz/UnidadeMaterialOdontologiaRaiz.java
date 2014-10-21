package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.UnidadeMaterialOdontologia;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class UnidadeMaterialOdontologiaRaiz extends PadraoRaiz<UnidadeMaterialOdontologia>{
	private boolean exibirDialogUnidadeMaterial;
	
	public void cadastrarUnidadeMaterial(){
		if(enviar())
			novaInstancia();
	}
	
	public void exibirDialogUnidadeMaterial(){
		novaInstancia();
		setExibirDialogUnidadeMaterial(true);
	}

	public void ocultarDialogUnidadeMaterial(){
		setExibirDialogUnidadeMaterial(false);
	}
	
	public boolean isExibirDialogUnidadeMaterial() {
		return exibirDialogUnidadeMaterial;
	}

	public void setExibirDialogUnidadeMaterial(boolean exibirDialogUnidadeMaterial) {
		this.exibirDialogUnidadeMaterial = exibirDialogUnidadeMaterial;
	}
}