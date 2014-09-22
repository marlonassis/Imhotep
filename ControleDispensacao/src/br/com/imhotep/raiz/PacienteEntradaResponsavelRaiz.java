package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.PacienteEntradaResponsavel;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class PacienteEntradaResponsavelRaiz extends PadraoRaiz<PacienteEntradaResponsavel>{
	
	public PacienteEntradaResponsavelRaiz(){
		super();
	}
	
	public PacienteEntradaResponsavelRaiz(boolean exibeMensagemInsercao){
		super.setExibeMensagemInsercao(exibeMensagemInsercao);
	}
}
