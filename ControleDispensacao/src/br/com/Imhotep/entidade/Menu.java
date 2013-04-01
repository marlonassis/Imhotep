package br.com.Imhotep.entidade;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.Imhotep.comparador.MenuComparador;

@Entity
@Table(name = "tb_menu")
public class Menu {
	private int idMenu;
	private Menu menuPai;
	private String descricao;
	private boolean bloqueado;
	private String url;
	private String urlAjuda;
	private List<Menu> menusFilho;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_menu_id_menu_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_menu", unique = true, nullable = false)
	public int getIdMenu() {
		return this.idMenu;
	}
	
	public void setIdMenu(int idMenu){
		this.idMenu = idMenu;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_menu_pai")
	public Menu getMenuPai() {
		return this.menuPai;
	}

	public void setMenuPai(Menu menuPai) {
		this.menuPai = menuPai;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "cv_url")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "cv_url_ajuda")
	public String getUrlAjuda() {
		return this.urlAjuda;
	}

	public void setUrlAjuda(String urlAjuda) {
		this.urlAjuda = urlAjuda;
	}
	
	@Column(name = "bl_bloqueado")
	public boolean getBloqueado() {
		return this.bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "menuPai")
	public List<Menu> getMenusFilho() {
		if(menusFilho != null)
			Collections.sort(menusFilho, new MenuComparador());
		return menusFilho;
	}
	public void setMenusFilho(List<Menu> menusFilho) {
		this.menusFilho = menusFilho;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Menu))
			return false;
		
		return ((Menu)obj).getIdMenu() == this.idMenu;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + descricao.hashCode() + idMenu;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
}
