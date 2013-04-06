package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Menu;
import br.com.remendo.PadraoHome;

@ManagedBean(name="menuRaiz")
@SessionScoped
public class MenuRaiz extends PadraoHome<Menu>{
	
}
