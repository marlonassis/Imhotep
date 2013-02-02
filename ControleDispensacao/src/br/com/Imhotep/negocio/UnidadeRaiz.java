package br.com.Imhotep.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Unidade;
import br.com.remendo.PadraoHome;

@ManagedBean(name="unidadeRaiz")
@SessionScoped
public class UnidadeRaiz extends PadraoHome<Unidade>{
	
}
