package br.com.imhotep.entidade;

import java.io.Serializable;
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
import javax.persistence.Transient;

import br.com.imhotep.comparador.MenuComparador;

@Entity
@Table(name = "tb_menu")
public class Menu implements Serializable {
	private static final long serialVersionUID = 7307010015392256068L;
	
	private int idMenu;
	private Menu menuPai;
	private String descricao;
	private boolean bloqueado;
	private boolean construcao;
	private boolean interno;
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
	
	@Column(name = "bl_interno")
	public boolean getInterno() {
		return this.interno;
	}

	public void setInterno(boolean interno) {
		this.interno = interno;
	}
	
	@Column(name = "bl_construcao")
	public boolean getConstrucao() {
		return this.construcao;
	}

	public void setConstrucao(boolean construcao) {
		this.construcao = construcao;
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
	
	@Transient
	public String getCaminhoMenu(){
		return caminho(this, "-> ");
	}
	
	@Transient
	private String caminho(Menu pai, String path){
		path =  path.concat(pai.getDescricao());
		if(pai.getMenuPai() != null){
			path =  path.concat(" -> ");
			return caminho(pai.getMenuPai(), path);
		}
		return path;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + idMenu;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idMenu != other.idMenu)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return descricao;
	}

}
