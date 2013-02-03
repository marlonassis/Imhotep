package br.com.Imhotep.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Menu;
import br.com.remendo.PadraoHome;

@ManagedBean(name="menuRaiz")
@SessionScoped
public class MenuRaiz extends PadraoHome<Menu>{
	
}
