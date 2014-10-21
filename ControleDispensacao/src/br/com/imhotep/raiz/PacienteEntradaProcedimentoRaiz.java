package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.PacienteEntradaResponsavel;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class PacienteEntradaProcedimentoRaiz extends PadraoRaiz<PacienteEntradaResponsavel>{
	
	public PacienteEntradaProcedimentoRaiz(){
		super();
	}
	
	public PacienteEntradaProcedimentoRaiz(boolean exibeMensagemInsercao){
		super.setExibeMensagemInsercao(exibeMensagemInsercao);
	}
	
}
