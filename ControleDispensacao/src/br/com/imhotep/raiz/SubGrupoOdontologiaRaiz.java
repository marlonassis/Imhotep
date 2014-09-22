package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.SubGrupoOdontologia;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class SubGrupoOdontologiaRaiz extends PadraoRaiz<SubGrupoOdontologia>{
	private boolean exibirDialogSubGrupo;
	
	public void cadastrarSubGrupo(){
		if(enviar())
			novaInstancia();
	}
	
	public void exibirDialogSubGrupo(){
		novaInstancia();
		setExibirDialogSubGrupo(true);
	}

	public void ocultarDialogSubGrupo(){
		setExibirDialogSubGrupo(false);
	}
	
	public boolean isExibirDialogSubGrupo() {
		return exibirDialogSubGrupo;
	}

	public void setExibirDialogSubGrupo(boolean exibirDialogSubGrupo) {
		this.exibirDialogSubGrupo = exibirDialogSubGrupo;
	}
}
