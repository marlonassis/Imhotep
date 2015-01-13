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
@Table(name = "tb_estrutura_organizacional_painel", schema="administrativo")
public class EstruturaOrganizacionalPainel implements Serializable {
	private static final long serialVersionUID = -2880473073165701006L;
	
	private int idEstruturaOrganizacionalPainel;
	private EstruturaOrganizacional estruturaOrganizacional;
	private Painel painel;
	
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_estrutura_organizacional_m_id_estrutura_organizacional_m_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estrutura_organizacional_painel", unique = true, nullable = false)
	public int getIdEstruturaOrganizacionalPainel() {
		return idEstruturaOrganizacionalPainel;
	}
	public void setIdEstruturaOrganizacionalPainel(int idEstruturaOrganizacionalPainel) {
		this.idEstruturaOrganizacionalPainel = idEstruturaOrganizacionalPainel;
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
	@JoinColumn(name = "id_painel")
	public Painel getPainel() {
		return painel;
	}

	public void setPainel(Painel painel) {
		this.painel = painel;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((estruturaOrganizacional == null) ? 0
						: estruturaOrganizacional.hashCode());
		result = prime * result + idEstruturaOrganizacionalPainel;
		result = prime * result + ((painel == null) ? 0 : painel.hashCode());
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
		EstruturaOrganizacionalPainel other = (EstruturaOrganizacionalPainel) obj;
		if (estruturaOrganizacional == null) {
			if (other.estruturaOrganizacional != null)
				return false;
		} else if (!estruturaOrganizacional
				.equals(other.estruturaOrganizacional))
			return false;
		if (idEstruturaOrganizacionalPainel != other.idEstruturaOrganizacionalPainel)
			return false;
		if (painel == null) {
			if (other.painel != null)
				return false;
		} else if (!painel.equals(other.painel))
			return false;
		return true;
	}
	
}
