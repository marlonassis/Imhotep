package br.com.imhotep.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.TreeNode;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.MenuConsultaRaiz;
import br.com.imhotep.controle.ControleMenu;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalMenu;
import br.com.imhotep.entidade.Menu;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EstruturaOrganizacionalMenuRaiz  extends PadraoRaiz<EstruturaOrganizacionalMenu>{
	private TreeNode root;
	private TreeNode selectedNode[];
	private EstruturaOrganizacional estruturaOrganizacional;
	private List<Menu> menuAutorizadoEstrutura = new ArrayList<Menu>();
	
	public void iniciarEdicao(EstruturaOrganizacional estruturaOrganizacional){
		setEstruturaOrganizacional(estruturaOrganizacional);
		montarMenu();
		carregarMenuEstrutura();
	}
	
	private void carregarMenuEstrutura() {
		setMenuAutorizadoEstrutura(new MenuConsultaRaiz().getMenusEstruturaOrganizacional(getEstruturaOrganizacional()));
	}

	public void montarMenu(){
		setRoot(new ControleMenu().carregarMenu());
	}
	
	public void alterarAcessoMenu(){
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		int idEstruturaOrganizacional = getEstruturaOrganizacional().getIdEstruturaOrganizacional();
		for(TreeNode no : getSelectedNode()){
			Menu menuSelecionado = (Menu) no.getData();
			boolean menuLotado = new MenuConsultaRaiz().isMenuLotadoEstrutura(estruturaOrganizacional, menuSelecionado);
			if(!menuLotado)
				adicionarAcessoMenu(menuSelecionado.getIdMenu(), lm, idEstruturaOrganizacional);
			else
				removerAcessoMenu(menuSelecionado.getIdMenu(), lm, idEstruturaOrganizacional);
		}
		carregarMenuEstrutura();
	}
	
	private void adicionarAcessoMenu(Integer idMenu, LinhaMecanica lm, int idEstruturaOrganizacional) {
		if(idMenu != null){
			String sql = "INSERT INTO administrativo.tb_estrutura_organizacional_menu(id_estrutura_organizacional, id_menu) "+
					"VALUES (" + idEstruturaOrganizacional + ", " + idMenu.intValue() + ");";
			lm.executarCUD(sql);
			
			sql = "select id_menu_pai from controle.tb_menu where id_menu = " + idMenu; 
			ResultSet rs = lm.consultar(sql);
			try {
				if(rs.next()){
					Integer id = rs.getInt("id_menu_pai");
					adicionarAcessoMenu(id, lm, idEstruturaOrganizacional);
				}else{
					adicionarAcessoMenu(null, lm, idEstruturaOrganizacional);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void removerAcessoMenu(Integer idMenu, LinhaMecanica lm, int idEstruturaOrganizacional) {
		if(idMenu != null){
			String sql = "delete from administrativo.tb_estrutura_organizacional_menu "
					+ "where id_estrutura_organizacional = " + idEstruturaOrganizacional + " and id_menu = " + idMenu.intValue();
			
			lm.executarCUD(sql);
			
			sql = "select id_menu from controle.tb_menu where id_menu_pai = " + idMenu; 
			ResultSet rs = lm.consultar(sql);
			try {
				while(rs.next()){
					int id = rs.getInt("id_menu");
					removerAcessoMenu(id, lm, idEstruturaOrganizacional);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public TreeNode getRoot() {
		return root;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
	public TreeNode[] getSelectedNode() {
		return selectedNode;
	}
	public void setSelectedNode(TreeNode[] selectedNode) {
		this.selectedNode = selectedNode;
	}
	
	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}
	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}

	public List<Menu> getMenuAutorizadoEstrutura() {
		return menuAutorizadoEstrutura;
	}

	public void setMenuAutorizadoEstrutura(List<Menu> menuAutorizadoEstrutura) {
		this.menuAutorizadoEstrutura = menuAutorizadoEstrutura;
	}
}