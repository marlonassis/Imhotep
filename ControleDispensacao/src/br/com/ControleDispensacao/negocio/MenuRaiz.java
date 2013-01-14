package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Menu;
import br.com.remendo.PadraoHome;

@ManagedBean(name="menuRaiz")
@SessionScoped
public class MenuRaiz extends PadraoHome<Menu>{

	/**
	 * Método que retorna uma lista de menus
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<Menu> getListaMenuAutoComplete(String sql){
		return super.getBusca("select o from Menu as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) order by to_ascii(o.descricao) ");
	}
	
}
