package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_unidade_material_odontologia")
public class UnidadeMaterialOdontologia implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3402706779056943753L;
	private int idUnidadeMaterialOdontologia;
	private String descricao;
	private String sigla;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_unidade_material_almoxarif_id_unidade_material_almoxarif_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_unidade_material_odontologia", unique = true, nullable = false)
	public int getIdUnidadeMaterialOdontologia() {
		return this.idUnidadeMaterialOdontologia;
	}
	
	public void setIdUnidadeMaterialOdontologia(int idUnidadeMaterialOdontologia){
		this.idUnidadeMaterialOdontologia = idUnidadeMaterialOdontologia;
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
		result = prime * result + idUnidadeMaterialOdontologia;
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
		UnidadeMaterialOdontologia other = (UnidadeMaterialOdontologia) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idUnidadeMaterialOdontologia != other.idUnidadeMaterialOdontologia)
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}
	
}
