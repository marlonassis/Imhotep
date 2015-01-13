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
@Table(name = "tb_estrutura_organizacional_email", schema="administrativo")
public class EstruturaOrganizacionalEmail  implements Serializable {
	private static final long serialVersionUID = -1596105769211658981L;
	
	private int idEstruturaOrganizacionalEmail;
	private EstruturaOrganizacional estruturaOrganizacional;
	private String email;
	
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_estrutura_organizacional_e_id_estrutura_organizacional_e_seq")
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estrutura_organizacional_email", unique = true, nullable = false)
	public int getIdEstruturaOrganizacionalEmail() {
		return idEstruturaOrganizacionalEmail;
	}
	public void setIdEstruturaOrganizacionalEmail(int idEstruturaOrganizacionalEmail) {
		this.idEstruturaOrganizacionalEmail = idEstruturaOrganizacionalEmail;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estrutura_organizacional")
	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}
	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}
	
	@Column(name = "cv_email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime
				* result
				+ ((estruturaOrganizacional == null) ? 0
						: estruturaOrganizacional.hashCode());
		result = prime * result + idEstruturaOrganizacionalEmail;
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
		EstruturaOrganizacionalEmail other = (EstruturaOrganizacionalEmail) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (estruturaOrganizacional == null) {
			if (other.estruturaOrganizacional != null)
				return false;
		} else if (!estruturaOrganizacional
				.equals(other.estruturaOrganizacional))
			return false;
		if (idEstruturaOrganizacionalEmail != other.idEstruturaOrganizacionalEmail)
			return false;
		return true;
	}
	
}
