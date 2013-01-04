package br.com.ControleDispensacao.comparador;

import java.util.Comparator;

import br.com.ControleDispensacao.entidade.Menu;

public class MenuComparador implements Comparator<Menu>  {  
	@Override
	public int compare(Menu arg0, Menu arg1) {
		return arg0.getDescricao().compareTo(arg1.getDescricao());
	}
}  
