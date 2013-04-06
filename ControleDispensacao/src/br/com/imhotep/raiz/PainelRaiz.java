package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Painel;
import br.com.remendo.PadraoHome;

@ManagedBean(name="painelRaiz")
@SessionScoped
public class PainelRaiz extends PadraoHome<Painel>{
	
}
