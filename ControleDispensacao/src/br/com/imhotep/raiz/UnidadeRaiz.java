package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Unidade;
import br.com.remendo.PadraoHome;

@ManagedBean(name="unidadeRaiz")
@SessionScoped
public class UnidadeRaiz extends PadraoHome<Unidade>{
	
}
