package br.com.Imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.PrescricaoItem;
import br.com.remendo.PadraoHome;

@ManagedBean(name="prescricaoItemRaiz")
@SessionScoped
public class PrescricaoItemRaiz extends PadraoHome<PrescricaoItem>{
	
	public PrescricaoItemRaiz(){
	}
	
	public PrescricaoItemRaiz(PrescricaoItem prescricaoItem, boolean exibeMensagemAtualizacao){
		super();
		super.setExibeMensagemAtualizacao(exibeMensagemAtualizacao);
		setInstancia(prescricaoItem);
	}
	
}