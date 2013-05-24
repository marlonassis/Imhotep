package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.PacienteEntradaCid;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class PacienteEntradaCidRaiz extends PadraoHome<PacienteEntradaCid>{
	
	public PacienteEntradaCidRaiz(){
		super();
	}
	
	public PacienteEntradaCidRaiz(boolean exibeMensagemInsercao){
		super.setExibeMensagemInsercao(exibeMensagemInsercao);
	}
}
