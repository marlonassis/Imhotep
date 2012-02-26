package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Menu;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="menuHome")
@SessionScoped
public class MenuHome extends PadraoHome<Menu>{

	/**
	 * Método que retorna uma lista de menus
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<Menu> getListaMenuAutoComplete(String sql){
		return super.getBusca("select o from Menu as o where o.descricao like '%"+sql+"%' ");
	}
	
}
