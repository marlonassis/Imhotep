package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.List;

import br.com.imhotep.entidade.Menu;
import br.com.imhotep.entidade.Variavel;

public class MenuVariavel {
	private Menu menu;
	private List<Variavel> variaveis = new ArrayList<Variavel>();
	
	
	
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public List<Variavel> getVariaveis() {
		return variaveis;
	}
	public void setVariaveis(List<Variavel> variaveis) {
		this.variaveis = variaveis;
	}
	
}
