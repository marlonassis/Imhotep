package br.com.Imhotep.raiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DualListModel;

import br.com.Imhotep.comparador.MenuComparador;
import br.com.Imhotep.entidade.AutorizaMenu;
import br.com.Imhotep.entidade.Menu;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="autorizaMenuRaiz")
@SessionScoped
public class AutorizaMenuRaiz extends PadraoHome<AutorizaMenu>{

	private DualListModel<Menu> menuDualList = new DualListModel<Menu>();;
	
	public boolean apagar(AutorizaMenu autorizaMenu, boolean exibeMensagem){
		setExibeMensagemDelecao(exibeMensagem);
		setInstancia(autorizaMenu);
		return super.apagar();
	}
	
	public boolean enviar(AutorizaMenu autorizaMenu, boolean exibeMensagem){
		setExibeMensagemInsercao(exibeMensagem);
		setInstancia(autorizaMenu);
		return super.enviar();
	}

	@Override
	public boolean atualizar() {
		removerItens();
		inserirItens();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Atualização finalizada", ""));
		return true;
	}
	
	private void inserirItens() {
		String [] ids = getIds(menuDualList.getTarget()).split(","); 
		for(String id : ids){
			AutorizaMenu am = new AutorizaMenu();
			am.setEspecialidade(getInstancia().getEspecialidade());
			am.setMenu(new Menu());
			am.getMenu().setIdMenu(Integer.parseInt(id));
			new AutorizaMenuRaiz().enviar(am, false);
		}
	}
	
	private void removerItens() {
		String[] ids = getIds(menuDualList.getSource()).split(",");
		
		for(String idMenu : ids){
			AutorizaMenu am = getAutorizaMenu(getInstancia().getEspecialidade().getIdEspecialidade(), idMenu);
			if(am != null){
				new AutorizaMenuRaiz().apagar(am, false);
			}
		}
	}

	private AutorizaMenu getAutorizaMenu(Integer idEspecialidade, String idMenu){
		String sql = "select o from AutorizaMenu o where o.especialidade.idEspecialidade = "+idEspecialidade+" and o.menu.idMenu = "+idMenu;
		AutorizaMenu am = new ConsultaGeral<AutorizaMenu>(new StringBuilder(sql), null).consultaUnica();
		return am;
	}
	
	private String getIds(List<Menu> itensRemovidos) {
		String ids="";
		int size = itensRemovidos.size();
		for(int i = 0; i < size; i++){
			ids = ids.concat(String.valueOf(itensRemovidos.get(i).getIdMenu()));
			if(size > i+1){
				ids = ids.concat(",");
			}
		}
		return ids;
	}

	public DualListModel<Menu> getMenuDualList() {
		if(getInstancia().getEspecialidade() != null){
			setMenuDualList(new DualListModel<Menu>(getListaMenuNaoAutorizado(), getListaMenuAutorizado()));
		}else{
			setMenuDualList(new DualListModel<Menu>(getListaMenu(), new ArrayList<Menu>()));
		}
		return menuDualList;
	}

	public void setMenuDualList(DualListModel<Menu> menuDualList) {
		this.menuDualList = menuDualList;
	}

	private List<Menu> getListaMenu(){
		ConsultaGeral<Menu> cg = new ConsultaGeral<Menu>();
		return new ArrayList<Menu>(cg.consulta(new StringBuilder("select o from Menu o order by to_ascii(o.descricao)"), null));
	}

	private List<Menu> getListaMenuPelaEspecialidade(String sql){
		ConsultaGeral<Menu> cg = new ConsultaGeral<Menu>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idEspecialidade", getInstancia().getEspecialidade().getIdEspecialidade());
		return new ArrayList<Menu>(cg.consulta(new StringBuilder(sql), hm));
	}
	
	private List<Menu> getListaMenuNaoAutorizado(){
		List<Menu> menus = getListaMenuPelaEspecialidade("select o from Menu o where o not in (select o.menu from AutorizaMenu o where o.especialidade.idEspecialidade = :idEspecialidade) order by to_ascii(o.descricao)");
//		Collections.sort(menus, new MenuComparador());
		return menus;
	}
	
	private List<Menu> getListaMenuAutorizado(){
		List<Menu> menus = getListaMenuPelaEspecialidade("select o.menu from AutorizaMenu o where o.especialidade.idEspecialidade = :idEspecialidade) order by to_ascii(o.menu.descricao)");
		Collections.sort(menus, new MenuComparador());
		return menus;
	}
	
	@Override
	public void novaInstancia() {
		// TODO Auto-generated method stub
		super.novaInstancia();
	}
}
