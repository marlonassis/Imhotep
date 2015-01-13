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
@Table(name = "tb_estrutura_organizacional_telefone", schema="administrativo")
public class EstruturaOrganizacionalTelefone  implements Serializable {
	private static final long serialVersionUID = -3619068655921175515L;
	
	private int idEstruturaOrganizacionalTelefone;
	private EstruturaOrganizacional estruturaOrganizacional;
	private String telefone;
	
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_estrutura_organizacional_t_id_estrutura_organizacional_t_seq")
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estrutura_organizacional_telefone", unique = true, nullable = false)
	public int getIdEstruturaOrganizacionalTelefone() {
		return idEstruturaOrganizacionalTelefone;
	}
	public void setIdEstruturaOrganizacionalTelefone(int idEstruturaOrganizacionalTelefone) {
		this.idEstruturaOrganizacionalTelefone = idEstruturaOrganizacionalTelefone;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estrutura_organizacional")
	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}
	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}
	
	@Column(name = "cv_telefone")
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((estruturaOrganizacional == null) ? 0
						: estruturaOrganizacional.hashCode());
		result = prime * result + idEstruturaOrganizacionalTelefone;
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
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
		EstruturaOrganizacionalTelefone other = (EstruturaOrganizacionalTelefone) obj;
		if (estruturaOrganizacional == null) {
			if (other.estruturaOrganizacional != null)
				return false;
		} else if (!estruturaOrganizacional
				.equals(other.estruturaOrganizacional))
			return false;
		if (idEstruturaOrganizacionalTelefone != other.idEstruturaOrganizacionalTelefone)
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}
	
}
