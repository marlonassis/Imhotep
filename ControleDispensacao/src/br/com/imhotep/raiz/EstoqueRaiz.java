package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Estoque;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class EstoqueRaiz extends PadraoHome<Estoque>{
	
	public EstoqueRaiz() {
	}
	
	public EstoqueRaiz(Estoque estoque) {
		setInstancia(estoque);
	}
	
}
