package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.EstruturaOrganizacionalMenu;
import br.com.imhotep.entidade.LotacaoProfissional;
import br.com.imhotep.entidade.Menu;
import br.com.imhotep.entidade.MenuVariavel;
import br.com.imhotep.entidade.Variavel;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class VariavelConsultaRaiz  extends ConsultaGeral<Variavel>{
	
	public List<MenuVariavel> getVariavelAutorizadaMenuFuncao(EstruturaOrganizacionalMenu estruturaOrganizacionalMenu) {
		int idEOM = estruturaOrganizacionalMenu.getIdEstruturaOrganizacionalMenu();
		String hql = "select o.menuVariavel from AcessoFuncaoVariavel o where o.acessoFuncao.estruturaOrganizacionalMenu.idEstruturaOrganizacionalMenu = " + idEOM
				+ " order by to_ascii(o.menuVariavel.variavel.nome)";
		StringBuilder sb = new StringBuilder(hql);
		return new ArrayList<MenuVariavel>(new ConsultaGeral<MenuVariavel>().consulta(sb, null));
	}
	
	public List<MenuVariavel> getVariavelAutorizadaMenuLotacao(EstruturaOrganizacionalMenu estruturaOrganizacionalMenu, LotacaoProfissional lotacaoProfissional) {
		int idEOM = estruturaOrganizacionalMenu.getIdEstruturaOrganizacionalMenu();
		int idLP = lotacaoProfissional.getIdLotacaoProfissional();
		String hql = "select o.menuVariavel from AcessoLotacaoVariavel o where o.acessoLotacao.estruturaOrganizacionalMenu.idEstruturaOrganizacionalMenu = " + idEOM
				+ " and o.acessoLotacao.lotacaoProfissional.idLotacaoProfissional = " + idLP
				+ " order by to_ascii(o.menuVariavel.variavel.nome)";
		StringBuilder sb = new StringBuilder(hql);
		return new ArrayList<MenuVariavel>(new ConsultaGeral<MenuVariavel>().consulta(sb, null));
	}
	
	public List<MenuVariavel> getMenuVariavel(Menu menu) {
		int id = menu.getIdMenu();
		String hql = "select o from MenuVariavel o where o.menu.idMenu = "+id+" order by to_ascii(o.variavel.nome)";
		StringBuilder sb = new StringBuilder(hql);
		return new ArrayList<MenuVariavel>(new ConsultaGeral<MenuVariavel>().consulta(sb, null));
	}
	
	public List<Variavel> getVariaveisMenu(Menu menu) {
		int id = menu.getIdMenu();
		String hql = "select o.variavel from MenuVariavel o where o.menu.idMenu = "+id+" order by to_ascii(o.variavel.nome)";
		StringBuilder sb = new StringBuilder(hql);
		return new ArrayList<Variavel>(new ConsultaGeral<Variavel>().consulta(sb, null));
	}
	
}
