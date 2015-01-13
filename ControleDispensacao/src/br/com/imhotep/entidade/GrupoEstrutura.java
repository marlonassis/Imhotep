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
@Table(name = "tb_grupo_estrutura", schema="administrativo")
public class GrupoEstrutura implements Serializable {
	private static final long serialVersionUID = 1515484572610711170L;
	
	private int idGrupoEstrutura;
	private GrupoAdm grupoAdm;
	private EstruturaOrganizacional estruturaOrganizacional;

	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_grupo_estrutura_id_grupo_estrutura_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_grupo_estrutura", unique = true, nullable = false)
	public int getIdGrupoEstrutura() {
		return idGrupoEstrutura;
	}
	
	public void setIdGrupoEstrutura(int idGrupoEstrutura) {
		this.idGrupoEstrutura = idGrupoEstrutura;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_grupo")
	public GrupoAdm getGrupoAdm() {
		return grupoAdm;
	}

	public void setGrupoAdm(GrupoAdm grupoAdm) {
		this.grupoAdm = grupoAdm;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estrutura_organizacional")
	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}

	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((estruturaOrganizacional == null) ? 0
						: estruturaOrganizacional.hashCode());
		result = prime * result
				+ ((grupoAdm == null) ? 0 : grupoAdm.hashCode());
		result = prime * result + idGrupoEstrutura;
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
		GrupoEstrutura other = (GrupoEstrutura) obj;
		if (estruturaOrganizacional == null) {
			if (other.estruturaOrganizacional != null)
				return false;
		} else if (!estruturaOrganizacional
				.equals(other.estruturaOrganizacional))
			return false;
		if (grupoAdm == null) {
			if (other.grupoAdm != null)
				return false;
		} else if (!grupoAdm.equals(other.grupoAdm))
			return false;
		if (idGrupoEstrutura != other.idGrupoEstrutura)
			return false;
		return true;
	}
	
}
