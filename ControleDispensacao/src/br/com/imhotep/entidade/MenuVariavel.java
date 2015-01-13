package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_menu_variavel", schema="controle")
public class MenuVariavel implements Serializable {
	private static final long serialVersionUID = 2327111238097469856L;
	
	private int idMenuVariavel;
	private Menu menu;
	private Variavel variavel;

	@SequenceGenerator(name = "generator", sequenceName = "controle.tb_menu_variavel_id_menu_variavel_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_menu_variavel", unique = true, nullable = false)
	public int getIdMenuVariavel() {
		return idMenuVariavel;
	}
	
	public void setIdMenuVariavel(int idMenuVariavel) {
		this.idMenuVariavel = idMenuVariavel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_menu")
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_variavel")
	public Variavel getVariavel() {
		return variavel;
	}

	public void setVariavel(Variavel variavel) {
		this.variavel = variavel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idMenuVariavel;
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
		result = prime * result
				+ ((variavel == null) ? 0 : variavel.hashCode());
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
		MenuVariavel other = (MenuVariavel) obj;
		if (idMenuVariavel != other.idMenuVariavel)
			return false;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		if (variavel == null) {
			if (other.variavel != null)
				return false;
		} else if (!variavel.equals(other.variavel))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getVariavel().getNome();
	}
	
}
