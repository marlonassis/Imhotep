package br.com.imhotep.raiz;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.imhotep.consulta.raiz.MenuConsultaRaiz;
import br.com.imhotep.entidade.AutorizaMenuProfissional;
import br.com.imhotep.entidade.Menu;
import br.com.imhotep.excecoes.ExcecaoProfissionalNaoEncontrado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class AutorizaMenuProfissionalRaiz extends PadraoRaiz<AutorizaMenuProfissional>{

	private TreeNode root;
	private List<Menu> menusAutorizado;
	private TreeNode[] selectedNodes;
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		menusAutorizado = null;
		selectedNodes = null;
		root = null;
	}
	
	public void atualizarAutorizacao(){
		for(TreeNode node : getSelectedNodes()){
			Menu menu = (Menu) node.getData();
			if(getMenusAutorizado().contains(menu)){
				removeMenu(menu);
			}else{
				try {
					addMenu(menu);
				} catch (ExcecaoProfissionalNaoEncontrado e) {
					e.printStackTrace();
				}
			}
		}
		carregarTreeMenu();
	}
	
	private void addMenu(Menu menu) throws ExcecaoProfissionalNaoEncontrado{
		LinhaMecanica lm = new LinhaMecanica();
		lm.inserirAutorizacaoMenu(getInstancia().getProfissional(), menu);
	}
	
	private void removeMenu(Menu menu){
		LinhaMecanica lm = new LinhaMecanica();
		lm.removerAutorizacaoMenu(getInstancia().getProfissional(), menu);
	}
	
	private void getNos(TreeNode root, List<Menu> menus){
		for(Menu menu : menus){
			TreeNode root2 = new DefaultTreeNode(menu, root);
			if(menu.getMenusFilho() != null && !menu.getMenusFilho().isEmpty()){
				getNos(root2, menu.getMenusFilho());
			}
		}
	}

	public void carregarTreeMenu(){
		if(getInstancia().getProfissional() != null){
			root = new DefaultTreeNode("Root", null);
			List<Menu> menusPai = new MenuConsultaRaiz().consultarMenuPai();
			setMenusAutorizado(new MenuConsultaRaiz().consultarMenuAutorizado(getInstancia().getProfissional()));
			getNos(root, menusPai);
		}
	}
	
	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public List<Menu> getMenusAutorizado() {
		return menusAutorizado;
	}

	public void setMenusAutorizado(List<Menu> menusAutorizado) {
		this.menusAutorizado = menusAutorizado;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

}
