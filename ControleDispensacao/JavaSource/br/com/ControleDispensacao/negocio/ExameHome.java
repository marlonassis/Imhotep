package br.com.ControleDispensacao.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Exame;
import br.com.remendo.PadraoHome;

@ManagedBean(name="exameHome")
@SessionScoped
public class ExameHome extends PadraoHome<Exame>{
	
}
