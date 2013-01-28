package br.com.Imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.Imhotep.enums.TipoStatusEnum;

@Entity
@Table(name = "tb_menu")
public class Menu {
	private int idMenu;
	private Menu menuPai;
	private String descricao;
	private TipoStatusEnum bloqueado;
	private String url;
	private String urlAjuda;
	
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
	
	@Column(name = "ds_descricao")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "ds_url")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "ds_url_ajuda")
	public String getUrlAjuda() {
		return this.urlAjuda;
	}

	public void setUrlAjuda(String urlAjuda) {
		this.urlAjuda = urlAjuda;
	}
	
	@Column(name = "tp_bloqueado")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getBloqueado() {
		return this.bloqueado;
	}

	public void setBloqueado(TipoStatusEnum bloqueado) {
		this.bloqueado = bloqueado;
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
	    return hash * 31 + descricao.hashCode();
	}

	@Override
	public String toString() {
		return descricao;
	}
	
}
