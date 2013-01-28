package br.com.Imhotep.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.controle.ControleMenu;
import br.com.Imhotep.entidade.Menu;

@ManagedBean(name="ajudaRaiz")
@SessionScoped
public class AjudaRaiz{
	
	private String nomeMenuConsulta;
	
	public void limparPesquisa(){
		nomeMenuConsulta = "";
	}
	
	public List<Menu> getListaAjuda(){
		ControleMenu controleMenu = ControleMenu.getInstancia();
		if(nomeMenuConsulta != null && !nomeMenuConsulta.isEmpty()){
			return procuraMenu(nomeMenuConsulta, controleMenu.getMenuAutorizadoList());
		}else{
			return controleMenu.getMenuAutorizadoList();
		}
	}
	
	private List<Menu> procuraMenu(String expressao, List<Menu> menuAutorizado) {
		List<Menu> menuEncontrado = new ArrayList<Menu>();
		for(Menu menu : menuAutorizado){
			String descricao = menu.getDescricao().toLowerCase();
			if(descricao.contains(expressao.toLowerCase())){
				menuEncontrado.add(menu);
			}
		}
		return menuEncontrado;
	}

	public String getNomeMenuConsulta() {
		return nomeMenuConsulta;
	}

	public void setNomeMenuConsulta(String nomeMenuConsulta) {
		this.nomeMenuConsulta = nomeMenuConsulta;
	}
	
}
