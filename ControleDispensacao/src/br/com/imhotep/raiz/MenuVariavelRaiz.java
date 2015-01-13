package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.Menu;
import br.com.imhotep.entidade.MenuVariavel;
import br.com.imhotep.entidade.Variavel;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class MenuVariavelRaiz extends PadraoRaiz<MenuVariavel>{
	private Menu menu;
	private List<MenuVariavel> variaveisMenu = new ArrayList<MenuVariavel>();
	private List<Variavel> variaveis = new ArrayList<Variavel>();
	private boolean exibirDialogVariavel;
	
	public void cadastrarVariaveisCRUDMenu() {
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		for(int i = 1; i < 5; i++){
			String sql = "insert into controle.tb_menu_variavel(id_menu, id_variavel) VALUES ("+
							getMenu().getIdMenu()+", "+
							i+");";
			lm.executarCUD(sql);
		}
		consultarVariaveisForaMenu();
		carregarVariaveisMenu();
	}
	
	public void exibirDialogVariavel(){
		setExibirDialogVariavel(true);
		carregarVariaveisMenu();
		consultarVariaveisForaMenu();
	}
	
	public void ocultarDialogVariavel(){
		setExibirDialogVariavel(false);
	}
	
	private void carregarVariaveisMenu(){
		int id = getMenu().getIdMenu();
		setVariaveisMenu(super.getBusca("select o from MenuVariavel o where o.menu.idMenu = " + id));
	}
	
	public void consultarVariaveisForaMenu(){
		int id = getMenu().getIdMenu();
		String hql = "select a from Variavel a where a.idVariavel not in (select o.variavel.idVariavel from MenuVariavel o where o.menu.idMenu = " + id + ")";
		ConsultaGeral<Variavel> cg = new ConsultaGeral<Variavel>(new StringBuilder(hql));
		setVariaveis(new ArrayList<Variavel>(cg.consulta()));
	}
	
	@Override
	public boolean apagar() {
		if(super.apagar()){
			carregarVariaveisMenu();
			consultarVariaveisForaMenu();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean enviar() {
		getInstancia().setMenu(getMenu());
		if(super.enviar()){
			carregarVariaveisMenu();
			consultarVariaveisForaMenu();
			return true;
		}
		return false;
	}
	
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public boolean isExibirDialogVariavel() {
		return exibirDialogVariavel;
	}

	public void setExibirDialogVariavel(boolean exibirDialogVariavel) {
		this.exibirDialogVariavel = exibirDialogVariavel;
	}

	public List<MenuVariavel> getVariaveisMenu() {
		return variaveisMenu;
	}

	public void setVariaveisMenu(List<MenuVariavel> variaveisMenu) {
		this.variaveisMenu = variaveisMenu;
	}

	public List<Variavel> getVariaveis() {
		return variaveis;
	}

	public void setVariaveis(List<Variavel> variaveis) {
		this.variaveis = variaveis;
	}
}
