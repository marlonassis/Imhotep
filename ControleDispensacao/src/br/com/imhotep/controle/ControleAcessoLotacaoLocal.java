package br.com.imhotep.controle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.primefaces.model.TreeNode;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.MenuConsultaRaiz;
import br.com.imhotep.consulta.raiz.VariavelConsultaRaiz;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalMenu;
import br.com.imhotep.entidade.LotacaoProfissional;
import br.com.imhotep.entidade.MenuVariavel;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class ControleAcessoLotacaoLocal {
	public ControleAcessoLotacaoLocal(){
		super();
	}
	
	public void carregarMenuAutorizadoNode(LotacaoProfissional lotacaoProfissional, 
											EstruturaOrganizacional estruturaOrganizacional, TreeNode root) {
		List<EstruturaOrganizacionalMenu> menusAutorizados = new MenuConsultaRaiz().consultarMenuAutorizadoProfissional(lotacaoProfissional);
		for(EstruturaOrganizacionalMenu menu : menusAutorizados){
			selecionarNodeMenuLiberado(menu, root);
			carregarVariaveisLiberadas(menu, root, lotacaoProfissional);
		}
	}
		
	private void carregarVariaveisLiberadas(EstruturaOrganizacionalMenu menu, TreeNode root, LotacaoProfissional lotacaoProfissional) {
		List<MenuVariavel> variaveisAutorizados = new VariavelConsultaRaiz().getVariavelAutorizadaMenuLotacao(menu, lotacaoProfissional);
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
	
	
	
	
	
	
	
	
	
	public void alterarStatusAcesso(TreeNode[] selectedNode, LotacaoProfissional lotacaoProfissional) {
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		removerAcessoLotacao(lm, lotacaoProfissional);
		inserirAcessoLotacao(lm, lotacaoProfissional, selectedNode);
		inserirAcessoLotacaoVariavel(lm, selectedNode, lotacaoProfissional);
	}
	
	private void inserirAcessoLotacaoVariavel(LinhaMecanica lm, TreeNode[] selectedNode, LotacaoProfissional lotacaoProfissional) {
		for(TreeNode item : selectedNode){
			if(item.getData() instanceof MenuVariavel){
				int idEOM = ((EstruturaOrganizacionalMenu) item.getParent().getData()).getIdEstruturaOrganizacionalMenu();
				int idLP = lotacaoProfissional.getIdLotacaoProfissional();
				int idMV = ((MenuVariavel) item.getData()).getIdMenuVariavel();
				int idAL = getIdAcessoLotacao(idEOM, idLP, lm);
				String sql = "INSERT INTO controle.tb_acesso_lotacao_variavel(id_acesso_lotacao, id_menu_variavel) VALUES ("+idAL+", "+idMV+");";
				lm.executarCUD(sql);
			}
		}
	}
	
	private int getIdAcessoLotacao(int idEOM, int idLP, LinhaMecanica lm){
		String sql = "select id_acesso_lotacao from controle.tb_acesso_lotacao where id_lotacao_profissional = "+idLP+" and id_estrutura_organizacional_menu = "+idEOM;
		ResultSet rs = lm.consultar(sql);
		try {
			if(rs.next()){
				return rs.getInt("id_acesso_lotacao");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private void inserirAcessoLotacao(LinhaMecanica lm, LotacaoProfissional lotacaoProfissional, TreeNode[] selectedNode) {
		for(TreeNode item : selectedNode){
			cadastrarAcessoLotacao(lm, item, lotacaoProfissional);
		}
	}
	
	private void removerAcessoLotacao(LinhaMecanica lm, LotacaoProfissional lotacaoProfissional){
		String sql = "delete from controle.tb_acesso_lotacao where id_lotacao_profissional = " + lotacaoProfissional.getIdLotacaoProfissional();
		lm.executarCUD(sql);
	}
	
	private void cadastrarAcessoLotacao(LinhaMecanica lm, TreeNode item, LotacaoProfissional lotacaoProfissional) {
		if(item.getData() instanceof EstruturaOrganizacionalMenu){
			int idEOM = ((EstruturaOrganizacionalMenu) item.getData()).getIdEstruturaOrganizacionalMenu();
			int idLP = lotacaoProfissional.getIdLotacaoProfissional();
			String sql = "INSERT INTO controle.tb_acesso_lotacao(id_lotacao_profissional, id_estrutura_organizacional_menu) VALUES ("+idLP+", "+idEOM+");";
			lm.executarCUD(sql);
		}
		if(item.getParent() != null){
			cadastrarAcessoLotacao(lm, item.getParent(), lotacaoProfissional);
		}
	}
}
