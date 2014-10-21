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
@Table(name = "tb_sub_grupo_odontologia")
public class SubGrupoOdontologia implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1909476456697905374L;
	private int idSubGrupoOdontologia;
	private GrupoOdontologia grupoOdontologia;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_sub_grupo_odontologia_id_sub_grupo_odontologia_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_sub_grupo_odontologia", unique = true, nullable = false)
	public int getIdSubGrupoOdontologia() {
		return this.idSubGrupoOdontologia;
	}
	
	public void setIdSubGrupoOdontologia(int idSubGrupoOdontologia){
		this.idSubGrupoOdontologia = idSubGrupoOdontologia;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_grupo_odontologia")
	public GrupoOdontologia getGrupoOdontologia(){
		return grupoOdontologia;
	}
	
	public void setGrupoOdontologia(GrupoOdontologia grupoOdontologia){
		this.grupoOdontologia = grupoOdontologia;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime
				* result
				+ ((grupoOdontologia == null) ? 0 : grupoOdontologia
						.hashCode());
		result = prime * result + idSubGrupoOdontologia;
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
		SubGrupoOdontologia other = (SubGrupoOdontologia) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (grupoOdontologia == null) {
			if (other.grupoOdontologia != null)
				return false;
		} else if (!grupoOdontologia.equals(other.grupoOdontologia))
			return false;
		if (idSubGrupoOdontologia != other.idSubGrupoOdontologia)
			return false;
		return true;
	}
	
}
