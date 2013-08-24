package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Especialidade;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.entidade.ProfissionalEspecialidade;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class ProfissionalEspecialidadeRaiz extends PadraoHome<ProfissionalEspecialidade>{
	
	public ProfissionalEspecialidadeRaiz(){
		super();
	}
	
	public ProfissionalEspecialidadeRaiz(ProfissionalEspecialidade o){
		super();
		setInstancia(o);
	}
	
	public boolean enviar(Especialidade especialidade, Profissional profissional){
		getInstancia().setEspecialidade(especialidade);
		getInstancia().setProfissional(profissional);
		return super.enviar();
	}
	
}
