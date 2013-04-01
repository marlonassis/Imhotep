package br.com.Imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.PsicologiaLog;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class PsicologiaLogRaiz extends PadraoHome<PsicologiaLog>{
	
	public boolean enviar(PsicologiaLog log) {
		setInstancia(log);
		super.setExibeMensagemInsercao(false);
		return super.enviar();
	}
}
