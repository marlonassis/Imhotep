package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.PacienteEntradaProcedimentoSaude;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class PacienteEntradaProcedimentoSaudeRaiz extends PadraoRaiz<PacienteEntradaProcedimentoSaude>{
	
	public PacienteEntradaProcedimentoSaudeRaiz(){
		super();
	}
	
	public PacienteEntradaProcedimentoSaudeRaiz(boolean exibeMensagemInsercao){
		super.setExibeMensagemInsercao(exibeMensagemInsercao);
	}
}
