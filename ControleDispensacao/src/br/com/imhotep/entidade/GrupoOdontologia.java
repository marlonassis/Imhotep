package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_grupo_odontologia")
public class GrupoOdontologia implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5833316223327145816L;
	private int idGrupoOdontologia;
	private String descricao;
	private boolean semFinanceiro;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_grupo_odontologia_id_grupo_odontologia_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_grupo_odontologia", unique = true, nullable = false)
	public int getIdGrupoOdontologia() {
		return this.idGrupoOdontologia;
	}
	
	public void setIdGrupoOdontologia(int idGrupoOdontologia){
		this.idGrupoOdontologia = idGrupoOdontologia;
	}

	@Column(name = "cv_descricao")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "bl_sem_financeiro")
	public boolean getSemFinanceiro() {
		return this.semFinanceiro;
	}

	public void setSemFinanceiro(boolean semFinanceiro) {
		this.semFinanceiro = semFinanceiro;
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
		result = prime * result + idGrupoOdontologia;
		result = prime * result + (semFinanceiro ? 1231 : 1237);
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
		GrupoOdontologia other = (GrupoOdontologia) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idGrupoOdontologia != other.idGrupoOdontologia)
			return false;
		if (semFinanceiro != other.semFinanceiro)
			return false;
		return true;
	}

}
