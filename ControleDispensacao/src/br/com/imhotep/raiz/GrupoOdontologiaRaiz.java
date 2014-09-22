package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.GrupoOdontologia;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class GrupoOdontologiaRaiz extends PadraoRaiz<GrupoOdontologia>{
	private boolean exibirDialogGrupo;
	
	public void cadastrarGrupo(){
		if(enviar())
			novaInstancia();
	}
	
	public void exibirDialogGrupo(){
		novaInstancia();
		setExibirDialogGrupo(true);
	}

	public void ocultarDialogGrupo(){
		setExibirDialogGrupo(false);
	}
	
	public boolean isExibirDialogGrupo() {
		return exibirDialogGrupo;
	}

	public void setExibirDialogGrupo(boolean exibirDialogGrupo) {
		this.exibirDialogGrupo = exibirDialogGrupo;
	}
}
