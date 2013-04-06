package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Fabricante;
import br.com.remendo.PadraoHome;

@ManagedBean(name="fabricanteRaiz")
@SessionScoped
public class FabricanteRaiz extends PadraoHome<Fabricante>{
	
}
