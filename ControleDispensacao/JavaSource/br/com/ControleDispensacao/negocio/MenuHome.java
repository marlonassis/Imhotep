package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import br.com.ControleDispensacao.consultaEntidade.AplicacaoConsulta;
import br.com.ControleDispensacao.consultaEntidade.UsuarioConsulta;
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
		getInstancia().setDataInclusao(new Date());
		getInstancia().setUsuarioInclusao(UsuarioHome.getUsuarioAtual());
		return super.enviar();
	}

	
	
	public MenuModel getToolBarMenuModel() {
		//TODO tornar o menu recursivo
		menuModel = new DefaultMenuModel();
		
		List<Menu> listaPrimeiroNivel = getBusca("select o from Menu o where o.menuPai is null and o.aplicacao in (select a.aplicacao from AutorizaAplicacao a where a.usuario.idUsuario = "+UsuarioHome.getUsuarioAtual().getIdUsuario()+") order by o.descricao");
		for (Menu menuPrimeiroNivel : listaPrimeiroNivel) {
			Submenu primeiroNivel = new Submenu();
			primeiroNivel.setLabel(menuPrimeiroNivel.getDescricao());
			
			List<Menu> listaSegundoNivel = getBusca("select o from Menu o where o.menuPai.idMenu = "+menuPrimeiroNivel.getIdMenu()+" and o.aplicacao in (select a.aplicacao from AutorizaAplicacao a where a.usuario.idUsuario = "+UsuarioHome.getUsuarioAtual().getIdUsuario()+") order by o.descricao");
			for (Menu menuSegundoNivel : listaSegundoNivel) {
				List<Menu> listaTerceiroNivel = getBusca("select o from Menu o where o.menuPai.idMenu = "+menuSegundoNivel.getIdMenu()+" and o.aplicacao in (select a.aplicacao from AutorizaAplicacao a where a.usuario.idUsuario = "+UsuarioHome.getUsuarioAtual().getIdUsuario()+") order by o.descricao");
				if(listaTerceiroNivel.size() > 0){
					Submenu segundoNivel = new Submenu();
					segundoNivel.setLabel(menuSegundoNivel.getDescricao());
					for (Menu menuTerceiroNivel : listaTerceiroNivel) {
						MenuItem mm = new MenuItem();
						mm.setValue(menuTerceiroNivel.getDescricao());
						mm.setUrl(menuTerceiroNivel.getAplicacao().getExecutavel());
						segundoNivel.getChildren().add(mm);
					}
					primeiroNivel.getChildren().add(segundoNivel);
				}else{
					MenuItem mm = new MenuItem();
					mm.setValue(menuSegundoNivel.getDescricao());
					mm.setUrl(menuSegundoNivel.getAplicacao().getExecutavel());
					primeiroNivel.getChildren().add(mm);
				}
			}
			
			menuModel.addSubmenu(primeiroNivel);
		}
		
		MenuItem mi = new MenuItem();
		mi.setValue("Sair");

		String action = "#{usuarioHome.logout()}";
		MethodExpression methodExpression = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
		createMethodExpression(FacesContext.getCurrentInstance().getELContext(), action, null, new Class<?>[0]);
		
		mi.setActionExpression(methodExpression);
		mi.setOncomplete("window.location.reload();");
		menuModel.addMenuItem(mi);
		
		return menuModel;
	}
	
	
	
	
	
	
	
	
	public MenuModel getMenuModel() {
		menuModel = new DefaultMenuModel();
		
		MenuItem mi = new MenuItem();
		Submenu submenu = new Submenu();
		submenu.setLabel("Home");
		mi.setValue("Home");
		mi.setUrl("/PaginasWeb/home.jsf");
		submenu.getChildren().add(mi);
		menuModel.addSubmenu(submenu);
		
		List<Menu> listaMenu = getBusca("select o from Menu o where o.idMenu = 30 or o.idMenu = 97 or o.idMenu = 18 or o.idMenu = 85 or o.idMenu = 7 or o.idMenu = 41 or o.idMenu = 8 or o.idMenu = 43");   //new AplicacaoConsulta().getList();
		for (Menu menu : listaMenu) {
			submenu = new Submenu();
			submenu.setLabel(menu.getDescricao());
			List<Menu> busca = getBusca("select o from Menu o where o.menuPai.idMenu = " + menu.getIdMenu());
			if(busca.size() > 0){
				for (Menu m : busca) {
						mi = new MenuItem();
						mi.setValue(m.getDescricao());
						mi.setUrl(m.getAplicacao().getExecutavel());
						submenu.getChildren().add(mi);
				}
			}else{
				mi = new MenuItem();
				mi.setValue(menu.getAplicacao().getDescricao());
				mi.setUrl(menu.getAplicacao().getExecutavel());
				submenu.getChildren().add(mi);
			}
			menuModel.addSubmenu(submenu);
		}
		
		submenu = new Submenu();
		submenu.setLabel("Sair");
		mi = new MenuItem();
		mi.setValue("Sair");

		String action = "#{usuarioHome.logout()}";
		MethodExpression methodExpression = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
		createMethodExpression(FacesContext.getCurrentInstance().getELContext(), action, null, new Class<?>[0]);
		
		mi.setActionExpression(methodExpression);
		mi.setOncomplete("window.location.reload();");
		submenu.getChildren().add(mi);
		menuModel.addSubmenu(submenu);
		
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}
}
