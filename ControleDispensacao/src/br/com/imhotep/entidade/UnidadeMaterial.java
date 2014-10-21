package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_unidade_material")
public class UnidadeMaterial implements Serializable {
	private static final long serialVersionUID = -3618386383934172664L;
	
	private int idUnidadeMaterial;
	private String descricao;
	private String sigla;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_unidade_material_id_unidade_material_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_unidade_material", unique = true, nullable = false)
	public int getIdUnidadeMaterial() {
		return this.idUnidadeMaterial;
	}
	
	public void setIdUnidadeMaterial(int idUnidadeMaterial){
		this.idUnidadeMaterial = idUnidadeMaterial;
	}

	@Column(name = "cv_unidade")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "cv_sigla")
	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + idUnidadeMaterial;
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		UnidadeMaterial other = (UnidadeMaterial) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idUnidadeMaterial != other.idUnidadeMaterial)
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}
	
}
