package br.com.imhotep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.Menu;

@ManagedBean
@SessionScoped
public class ControleMenu implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<String> menuAutorizadoStringList = new ArrayList<String>();
	private List<Menu> menuAutorizadoList = new ArrayList<Menu>();
	
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
	
	public void converteMenuString(){
		setMenuAutorizadoStringList(new ArrayList<String>());
		for(Menu menu : menuAutorizadoList){
			getMenuAutorizadoStringList().add(menu.getDescricao());
		}
	}
	
	public List<Menu> getMenuAutorizadoList() {
		return menuAutorizadoList;
	}
	public void setMenuAutorizadoList(List<Menu> menuAutorizadoList) {
		this.menuAutorizadoList = menuAutorizadoList;
	}
	public List<String> getMenuAutorizadoStringList() {
		return menuAutorizadoStringList;
	}
	public void setMenuAutorizadoStringList(List<String> menuAutorizadoStringList) {
		this.menuAutorizadoStringList = menuAutorizadoStringList;
	}
	
}
