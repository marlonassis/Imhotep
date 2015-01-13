package br.com.imhotep.consulta.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalFuncao;
import br.com.imhotep.entidade.EstruturaOrganizacionalMenu;
import br.com.imhotep.entidade.LotacaoProfissional;
import br.com.imhotep.entidade.Menu;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class MenuConsultaRaiz  extends ConsultaGeral<Menu>{
	
	public List<EstruturaOrganizacionalMenu> getEstruturaOrganizacionalMenu(EstruturaOrganizacional estruturaOrganizacional){
		int id = estruturaOrganizacional.getIdEstruturaOrganizacional();
		String hql = "select o from EstruturaOrganizacionalMenu o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id + " order by o.menu.descricao";
		List<EstruturaOrganizacionalMenu> list = new ArrayList<EstruturaOrganizacionalMenu>(new ConsultaGeral<EstruturaOrganizacionalMenu>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<Menu> getMenusEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional){
		int id = estruturaOrganizacional.getIdEstruturaOrganizacional();
		String hql = "select o.menu from EstruturaOrganizacionalMenu o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id + " order by o.menu.descricao";
		List<Menu> list = new ArrayList<Menu>(new ConsultaGeral<Menu>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<Menu> getMenusPai(){
		String hql = "select o from Menu o where o.menuPai is null order by o.descricao";
		List<Menu> list = new ArrayList<Menu>(new ConsultaGeral<Menu>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<Menu> getMenusFilho(Menu menu){
		String hql = "select o from Menu o where o.menuPai.idMenu = " + menu.getIdMenu() + " order by o.descricao";
		List<Menu> list = new ArrayList<Menu>(new ConsultaGeral<Menu>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public boolean isMenuLotadoEstrutura(EstruturaOrganizacional estruturaOrganizacional, Menu menu){
		String sql = "select case when (select a.id_menu from administrativo.tb_estrutura_organizacional_menu a "+
						"where a.id_estrutura_organizacional = " + estruturaOrganizacional.getIdEstruturaOrganizacional() + 
						" and a.id_menu = " + menu.getIdMenu() + ") is null then false else true end as menuLotado";
		
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		ResultSet rs = lm.consultar(sql);
		try {
			rs.next();
			return rs.getBoolean("menuLotado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Menu> consultarMenuPai() {
		String hql = "select o from Menu o where o.menuPai is null order by o.descricao";
		List<Menu> list = new ArrayList<Menu>(new ConsultaGeral<Menu>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<EstruturaOrganizacionalMenu> consultarMenuAutorizadoFuncao(EstruturaOrganizacionalFuncao estruturaOrganizacionalFuncao) {
		int idEstruturaOrganizacionalFuncao = estruturaOrganizacionalFuncao.getIdEstruturaOrganizacionalFuncao();
		String hql = "select o.estruturaOrganizacionalMenu from AcessoFuncao o "
						+ "where o.estruturaOrganizacionalFuncao.idEstruturaOrganizacionalFuncao = "+idEstruturaOrganizacionalFuncao+" "
						+ "order by lower(to_ascii(o.estruturaOrganizacionalMenu.menu.descricao))";
		List<EstruturaOrganizacionalMenu> list = new ArrayList<EstruturaOrganizacionalMenu>(new ConsultaGeral<EstruturaOrganizacionalMenu>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<EstruturaOrganizacionalMenu> consultarMenuAutorizadoProfissional(LotacaoProfissional lotacaoProfissional) {
		int idLP = lotacaoProfissional.getIdLotacaoProfissional();
		String hql = "select o.estruturaOrganizacionalMenu from AcessoLotacao o "
						+ "where o.lotacaoProfissional.idLotacaoProfissional = "+idLP+" "
						+ "order by lower(to_ascii(o.estruturaOrganizacionalMenu.menu.descricao))";
		List<EstruturaOrganizacionalMenu> list = new ArrayList<EstruturaOrganizacionalMenu>(new ConsultaGeral<EstruturaOrganizacionalMenu>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
	public List<EstruturaOrganizacionalMenu> consultarEstruturaOrganizacionalMenuAutorizado(Profissional profissional, EstruturaOrganizacional estruturaOrganizacional) {
		int idProfissional = profissional.getIdProfissional();
		String hql = "select o.estruturaOrganizacionalMenu from AcessoLotacao o "
						+ "where o.lotacaoProfissional.profissional.idProfissional = "+idProfissional+" "
						+ "and o.lotacaoProfissional.estruturaOrganizacional.idEstruturaOrganizacional = "+estruturaOrganizacional.getIdEstruturaOrganizacional()+" "
						+ "order by lower(to_ascii(o.estruturaOrganizacionalMenu.menu.descricao))";
		List<EstruturaOrganizacionalMenu> list = new ArrayList<EstruturaOrganizacionalMenu>(new ConsultaGeral<EstruturaOrganizacionalMenu>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
}
