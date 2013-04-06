package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.AvaliacaoPsicologicaLog;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class AvaliacaoPsicologicaLogRaiz extends PadraoHome<AvaliacaoPsicologicaLog>{
	
	public boolean enviar(AvaliacaoPsicologicaLog log) {
		setInstancia(log);
		super.setExibeMensagemInsercao(false);
		return super.enviar();
	}
}
