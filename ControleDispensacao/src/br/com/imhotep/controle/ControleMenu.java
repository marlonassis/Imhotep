package br.com.imhotep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.comparador.MenuComparador;
import br.com.imhotep.consulta.raiz.MenuConsultaRaiz;
import br.com.imhotep.entidade.Menu;
import br.com.imhotep.entidade.Variavel;

@ManagedBean
@SessionScoped
public class ControleMenu implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Menu> menuAutorizadoList = new ArrayList<Menu>();
	private List<String> menuAutorizadoString = new ArrayList<String>();
	private MenuModel menuModel;
	
	public boolean menuBloqueado(Object obj){
		if(!(obj instanceof String) && !(obj instanceof Variavel)){
			Menu menu = (Menu) obj;
			return menu.getBloqueado();
		}
		return false;
	}
	
	public boolean menuConstrucao(Object obj){
		if(!(obj instanceof String) && !(obj instanceof Variavel)){
			Menu menu = (Menu) obj;
			return menu.getConstrucao();
		}
		return false;
	}
	
	public TreeNode carregarMenu(){
		TreeNode root = new DefaultTreeNode("Menu", null);
		root.setSelectable(false);
		root.setExpanded(true);
		List<Menu> menusPai = new MenuConsultaRaiz().getMenusPai();
		montarMenuTree(root, menusPai);
		return root;
	}
	
	private void montarMenuTree(TreeNode root, List<Menu> menusPai) {
		for(Menu menu : menusPai){
			DefaultTreeNode node = new DefaultTreeNode(menu, root);
			List<Menu> menusFilho = new MenuConsultaRaiz().getMenusFilho(menu);
			montarMenuTree(node, menusFilho);
		}
	}
	
	public void montarMenuModel(){
		menuModel = new DefaultMenuModel();
		Collections.sort(menuAutorizadoList, new MenuComparador());
		adicionarHome();
		for(Menu menu : getMenuAutorizadoList())
			if(menu.getMenuPai() == null && !menu.getInterno()){
				DefaultSubMenu subMenu = contruirMenu(menu, filhosMenuPai(menu));
				getMenuModel().addElement(subMenu);
			}
		adicionarFinalMenu();
	}

	private void adicionarHome() {
	    DefaultMenuItem item = new DefaultMenuItem();
	    item.setValue("Home");
	    item.setUrl("/PaginasWeb/home.hu");
	    getMenuModel().addElement(item);
	}
	
	private void adicionarFinalMenu() {
		DefaultMenuItem item = new DefaultMenuItem();
	    item.setValue("Ajuda");
	    item.setUrl("/PaginasWeb/Ajuda/Ajuda/ajuda.hu");
	    getMenuModel().addElement(item);
	    
	    item = new DefaultMenuItem();
	    item.setValue("Sair");
	    item.setCommand("#{autenticador.logout()}");
	    getMenuModel().addElement(item);
	}

	private DefaultSubMenu contruirMenu(Menu menu, List<Menu> filhos) {
	    DefaultSubMenu subMenu = new DefaultSubMenu();
		subMenu.setLabel(menu.getDescricao());
		Collections.sort(filhos, new MenuComparador());
		for(Menu filho : filhos){
			if(!filho.getInterno()){
				List<Menu> sons = filhosMenuPai(filho);
				if(!sons.isEmpty()){
					DefaultSubMenu subMenu2 = contruirMenu(filho, filhosMenuPai(filho));
					subMenu2.setLabel(filho.getDescricao());
					subMenu.addElement(subMenu2);
				}else{
					DefaultMenuItem menuItem = new DefaultMenuItem();
					menuItem.setValue(filho.getDescricao());
					menuItem.setUrl(filho.getUrl());
					if(filho.getConstrucao()){
						menuItem.setStyle("background-color:#FF0000;");
					}
					subMenu.addElement(menuItem);
				}
			}
		}
		return subMenu;
	}

	public void montarMenuPlanoString(){
		for(Menu m : menuAutorizadoList){
			getMenuAutorizadoString().add(m.getDescricao());
		}
	}
	
	private List<Menu> filhosMenuPai(Menu menu){
		List<Menu> filhos = new ArrayList<Menu>();
		for(Menu item : menuAutorizadoList){
			if(item.getMenuPai() != null && menu.getIdMenu() == item.getMenuPai().getIdMenu()){
				filhos.add(item);
			}
		}
		return filhos;
	}

	public static ControleMenu getInstancia(){
		try {
			return (ControleMenu) Utilitarios.procuraInstancia(ControleMenu.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean urlAutorizada(String url){
		url = url.toLowerCase().replaceAll("/imhotep", "");
		for(Menu menu : menuAutorizadoList){
			if(menu.getUrl() != null){
				String url2 = menu.getUrl().toLowerCase();
				if(url2.equals(url))
					return true;
			}
		}
		return false;
	}
	
	public List<Menu> getMenuAutorizadoList() {
		return menuAutorizadoList;
	}
	public void setMenuAutorizadoList(List<Menu> menuAutorizadoList) {
		this.menuAutorizadoList = menuAutorizadoList;
	}

	public MenuModel getMenuModel() {
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public List<String> getMenuAutorizadoString() {
		return menuAutorizadoString;
	}

	public void setMenuAutorizadoString(List<String> menuAutorizadoString) {
		this.menuAutorizadoString = menuAutorizadoString;
	}
	
}
