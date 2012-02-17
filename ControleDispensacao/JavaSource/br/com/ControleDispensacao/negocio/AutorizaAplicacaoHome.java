package br.com.ControleDispensacao.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.AutorizaAplicacao;
import br.com.ControleDispensacao.entidade.Menu;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="autorizaAplicacaoHome")
@SessionScoped
public class AutorizaAplicacaoHome extends PadraoHome<AutorizaAplicacao>{
	private Menu menu;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
