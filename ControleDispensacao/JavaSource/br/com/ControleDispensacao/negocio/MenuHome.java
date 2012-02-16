package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import br.com.ControleDispensacao.consultaEntidade.AplicacaoConsulta;
import br.com.ControleDispensacao.entidade.Aplicacao;
import br.com.ControleDispensacao.entidade.Menu;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="menuHome")
@SessionScoped
public class MenuHome extends PadraoHome<Menu>{

	private MenuModel menuModel;
	
	/**
	 * Método que retorna uma lista de menus
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<Menu> getListaMenuSuggest(String sql){
		return super.getBusca("select o from Menu as o where o.descricao like '%"+sql+"%' ");
	}
	
	@Override
	public boolean enviar() {
		getInstancia().setOrdem(1);
		return super.enviar();
	}

	public MenuModel getMenuModel() {
		
		
		 menuModel = new DefaultMenuModel();
		 List<Aplicacao> listaAplicacao = new AplicacaoConsulta().getList();
		 for (Aplicacao aplicacao : listaAplicacao) {
			 Submenu submenu = new Submenu();
			 submenu.setLabel(aplicacao.getDescricao());
//			 for (Menu m : menuDAO.buscaPorMenu(aplicacao)) {
//				 if (!menuDAO.verificaSubMenu(m)) {
//					 MenuItem mi = new MenuItem();
//					 mi.setValue(m.getDescricao());
//					 mi.setIcon("imagens/" + m.getIcone());
//					 submenu.getChildren().add(mi);
//				 } else {
//					 Submenu sm = new Submenu();
//					 sm.setLabel(m.getDescricao());
//					 sm = geraSubmenu(m);
//					 submenu.getChildren().add(sm);
//				 }
//			 }
			 menuModel.addSubmenu(submenu);
		 }
		
		
		
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}
}
