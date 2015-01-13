package br.com.imhotep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.imhotep.consulta.raiz.MenuConsultaRaiz;
import br.com.imhotep.consulta.raiz.VariavelConsultaRaiz;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalMenu;
import br.com.imhotep.entidade.MenuVariavel;

@ManagedBean
@SessionScoped
public class ControleMenuLocal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public boolean menuBloqueado(Object obj){
		if(!(obj instanceof String) && !(obj instanceof MenuVariavel)){
			EstruturaOrganizacionalMenu eom = (EstruturaOrganizacionalMenu) obj;
			return eom.getMenu().getBloqueado();
		}
		return false;
	}
	
	public boolean menuConstrucao(Object obj){
		if(!(obj instanceof String) && !(obj instanceof MenuVariavel)){
			EstruturaOrganizacionalMenu eom = (EstruturaOrganizacionalMenu) obj;
			return eom.getMenu().getConstrucao();
		}
		return false;
	}
	
	public TreeNode carregarMenu(EstruturaOrganizacional estruturaOrganizacional){
		TreeNode root = new DefaultTreeNode("Menu", null);
		root.setSelectable(false);
		root.setExpanded(true);
		List<EstruturaOrganizacionalMenu> menusEstruturaOrganizacional = new MenuConsultaRaiz().getEstruturaOrganizacionalMenu(estruturaOrganizacional);
		for(EstruturaOrganizacionalMenu pai : getMenusPai(menusEstruturaOrganizacional)){
			montarMenuTree(root, pai, menusEstruturaOrganizacional);
		}
		return root;
	}
	
	private List<EstruturaOrganizacionalMenu> getMenusPai(List<EstruturaOrganizacionalMenu> menus){
		List<EstruturaOrganizacionalMenu> lista = new ArrayList<EstruturaOrganizacionalMenu>();
		for(EstruturaOrganizacionalMenu item : menus){
			if(item.getMenu().getMenuPai() == null){
				lista.add(item);
			}
		}
		return lista;
	}
	
	private List<EstruturaOrganizacionalMenu> getMenusFilho(EstruturaOrganizacionalMenu pai, List<EstruturaOrganizacionalMenu> menus){
		List<EstruturaOrganizacionalMenu> lista = new ArrayList<EstruturaOrganizacionalMenu>();
		for(EstruturaOrganizacionalMenu item : menus){
			if(item.getMenu().getMenuPai() != null && pai.getMenu().getIdMenu() == item.getMenu().getMenuPai().getIdMenu()){
				lista.add(item);
			}
		}
		return lista;
	}
	
	private void montarMenuTree(TreeNode root, EstruturaOrganizacionalMenu pai, List<EstruturaOrganizacionalMenu> menus) {
		DefaultTreeNode node = new DefaultTreeNode(pai, root);
		List<EstruturaOrganizacionalMenu> menusFilho = getMenusFilho(pai, menus);
		for(EstruturaOrganizacionalMenu filho : menusFilho){
			List<EstruturaOrganizacionalMenu> sons = getMenusFilho(filho, menus);
			if(sons.isEmpty()){
				DefaultTreeNode dtn = new DefaultTreeNode(filho);
				carregarVariaveis(filho, dtn);
				node.getChildren().add(dtn);
			}else{
				montarMenuTree(node, filho, sons);
			}
		}
	}

	private void carregarVariaveis(EstruturaOrganizacionalMenu filho, DefaultTreeNode dtn) {
		List<MenuVariavel> list = new VariavelConsultaRaiz().getMenuVariavel(filho.getMenu());
		for(MenuVariavel var : list){
			dtn.getChildren().add(new DefaultTreeNode(var));
		}
	}
	
}
