package br.com.imhotep.controle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.primefaces.model.TreeNode;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.MenuConsultaRaiz;
import br.com.imhotep.consulta.raiz.VariavelConsultaRaiz;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalFuncao;
import br.com.imhotep.entidade.EstruturaOrganizacionalMenu;
import br.com.imhotep.entidade.MenuVariavel;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class ControleAcessoFuncaoLocal {
	public ControleAcessoFuncaoLocal(){
		super();
	}
	
	public void carregarMenuAutorizadoNode(EstruturaOrganizacionalFuncao estruturaOrganizacionalFuncao, 
											EstruturaOrganizacional estruturaOrganizacional, TreeNode root) {
		List<EstruturaOrganizacionalMenu> menusAutorizados = new MenuConsultaRaiz().consultarMenuAutorizadoFuncao(estruturaOrganizacionalFuncao);
		for(EstruturaOrganizacionalMenu menu : menusAutorizados){
			selecionarNodeMenuLiberado(menu, root);
			carregarVariaveisLiberadas(menu, root);
		}
	}
		
	private void carregarVariaveisLiberadas(EstruturaOrganizacionalMenu menu, TreeNode root) {
		List<MenuVariavel> variaveisAutorizados = new VariavelConsultaRaiz().getVariavelAutorizadaMenuFuncao(menu);
		for(MenuVariavel var : variaveisAutorizados){
			selecionarNodeVariavelLiberada(var, root);
		}
	}
		
	private void selecionarNodeVariavelLiberada(MenuVariavel menuVariavel, TreeNode root){
		boolean achou = root.getData() instanceof MenuVariavel ? 
				menuVariavel.getIdMenuVariavel() == ((MenuVariavel) root.getData()).getIdMenuVariavel() : false;
		if(achou){
			root.setSelected(true);
		}else{
			for(TreeNode node : root.getChildren()){
				selecionarNodeVariavelLiberada(menuVariavel, node);
			}
		}
	}
		
	private void selecionarNodeMenuLiberado(EstruturaOrganizacionalMenu menu, TreeNode root){
		boolean achou = root.getData() instanceof EstruturaOrganizacionalMenu ? 
				menu.getIdEstruturaOrganizacionalMenu() == ((EstruturaOrganizacionalMenu) root.getData()).getIdEstruturaOrganizacionalMenu() : false;
		if(achou){
			root.setSelected(true);
		}else{
			for(TreeNode node : root.getChildren()){
					selecionarNodeMenuLiberado(menu, node);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	public void alterarStatusAcesso(TreeNode[] selectedNode, EstruturaOrganizacionalFuncao estruturaOrganizacionalFuncao){
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		removerAcessoLotacaoFuncao(lm, estruturaOrganizacionalFuncao);
		inserirAcessoLotacaoFuncao(lm, estruturaOrganizacionalFuncao, selectedNode);
		inserirAcessoLotacaoFuncaoVariavel(lm, estruturaOrganizacionalFuncao, selectedNode);
	}
	
	private void inserirAcessoLotacaoFuncaoVariavel(LinhaMecanica lm, EstruturaOrganizacionalFuncao estruturaOrganizacionalFuncao, TreeNode[] selectedNode) {
		for(TreeNode item : selectedNode){
			if(item.getData() instanceof MenuVariavel){
				int idEOM = ((EstruturaOrganizacionalMenu) item.getParent().getData()).getIdEstruturaOrganizacionalMenu();
				int idEOF = estruturaOrganizacionalFuncao.getIdEstruturaOrganizacionalFuncao();
				int idMV = ((MenuVariavel) item.getData()).getIdMenuVariavel();
				int idAL = getIdAcessoFuncao(idEOM, idEOF, lm);
				String sql = "INSERT INTO controle.tb_acesso_funcao_variavel(id_acesso_funcao, id_menu_variavel) VALUES ("+idAL+", "+idMV+");";
				lm.executarCUD(sql);
			}
		}
	}
	
	private int getIdAcessoFuncao(int idEOM, int idEOF, LinhaMecanica lm){
		String sql = "select id_acesso_funcao from controle.tb_acesso_funcao where id_estrutura_organizacional_funcao = "+idEOF+" and id_estrutura_organizacional_menu = "+idEOM;
		ResultSet rs = lm.consultar(sql);
		try {
			if(rs.next()){
				return rs.getInt("id_acesso_funcao");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private void removerAcessoLotacaoFuncao(LinhaMecanica lm, EstruturaOrganizacionalFuncao organizacionalFuncao){
		String sql = "delete from controle.tb_acesso_funcao where id_estrutura_organizacional_funcao = " + organizacionalFuncao.getIdEstruturaOrganizacionalFuncao();
		lm.executarCUD(sql);
	}
	
	private void inserirAcessoLotacaoFuncao(LinhaMecanica lm, EstruturaOrganizacionalFuncao estruturaOrganizacionalFuncao, TreeNode[] selectedNode) {
		for(TreeNode item : selectedNode){
			cadastrarAcessoLotacaoFuncao(lm, item, estruturaOrganizacionalFuncao);
		}
	}
	
	private void cadastrarAcessoLotacaoFuncao(LinhaMecanica lm, TreeNode item, EstruturaOrganizacionalFuncao estruturaOrganizacionalFuncao) {
		if(item.getData() instanceof EstruturaOrganizacionalMenu){
			int idEOM = ((EstruturaOrganizacionalMenu) item.getData()).getIdEstruturaOrganizacionalMenu();
			int idEOF = estruturaOrganizacionalFuncao.getIdEstruturaOrganizacionalFuncao();
			String sql = "INSERT INTO controle.tb_acesso_funcao(id_estrutura_organizacional_funcao, id_estrutura_organizacional_menu) VALUES ("+idEOF+", "+idEOM+");";
			lm.executarCUD(sql);
		}
		if(item.getParent() != null){
			cadastrarAcessoLotacaoFuncao(lm, item.getParent(), estruturaOrganizacionalFuncao);
		}
	}
	
	
}
