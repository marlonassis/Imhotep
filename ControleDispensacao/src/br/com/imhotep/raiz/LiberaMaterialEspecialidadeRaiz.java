package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Especialidade;
import br.com.imhotep.entidade.LiberaMaterialEspecialidade;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class LiberaMaterialEspecialidadeRaiz extends PadraoHome<LiberaMaterialEspecialidade>{
	
	@Override
	public boolean enviar() {
		Especialidade especialidade = getInstancia().getEspecialidade();
		if(super.enviar()){
			super.novaInstancia();
			getInstancia().setEspecialidade(especialidade);
			return true;
		}
		return false;
	}

}
