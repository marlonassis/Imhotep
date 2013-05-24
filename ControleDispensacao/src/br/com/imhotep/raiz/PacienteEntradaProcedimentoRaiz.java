package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.PacienteEntradaResponsavel;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class PacienteEntradaProcedimentoRaiz extends PadraoHome<PacienteEntradaResponsavel>{
	
	public PacienteEntradaProcedimentoRaiz(){
		super();
	}
	
	public PacienteEntradaProcedimentoRaiz(boolean exibeMensagemInsercao){
		super.setExibeMensagemInsercao(exibeMensagemInsercao);
	}
	
}
