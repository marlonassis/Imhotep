package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.PainelAvisoEspecialidade;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class PainelAvisoEspecialidadeRaiz extends PadraoHome<PainelAvisoEspecialidade>{
	
	public PainelAvisoEspecialidadeRaiz(){
		super();
	}
	
	public PainelAvisoEspecialidadeRaiz(PainelAvisoEspecialidade pae){
		super();
		setInstancia(pae);
	}
	
}