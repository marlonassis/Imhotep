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
@Table(name = "tb_estrutura_organizacional_menu", schema="administrativo")
public class EstruturaOrganizacionalMenu  implements Serializable {
	private static final long serialVersionUID = -3619068655921175515L;
	
	private int idEstruturaOrganizacionalMenu;
	private EstruturaOrganizacional estruturaOrganizacional;
	private Menu menu;
	
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_estrutura_organizacional_m_id_estrutura_organizacional_m_seq")
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estrutura_organizacional_menu", unique = true, nullable = false)
	public int getIdEstruturaOrganizacionalMenu() {
		return idEstruturaOrganizacionalMenu;
	}
	public void setIdEstruturaOrganizacionalMenu(int idEstruturaOrganizacionalMenu) {
		this.idEstruturaOrganizacionalMenu = idEstruturaOrganizacionalMenu;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estrutura_organizacional")
	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}
	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_menu")
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((estruturaOrganizacional == null) ? 0
						: estruturaOrganizacional.hashCode());
		result = prime * result + idEstruturaOrganizacionalMenu;
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
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
		EstruturaOrganizacionalMenu other = (EstruturaOrganizacionalMenu) obj;
		if (estruturaOrganizacional == null) {
			if (other.estruturaOrganizacional != null)
				return false;
		} else if (!estruturaOrganizacional
				.equals(other.estruturaOrganizacional))
			return false;
		if (idEstruturaOrganizacionalMenu != other.idEstruturaOrganizacionalMenu)
			return false;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getMenu().getDescricao();
	}
}
