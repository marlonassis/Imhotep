package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_grupo_almoxarifado")
public class GrupoAlmoxarifado implements Serializable {
	private static final long serialVersionUID = 2729621542707300366L;
	
	private int idGrupoAlmoxarifado;
	private String descricao;
	private boolean semFinanceiro;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_grupo_almoxarifado_id_grupo_almoxarifado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_grupo_almoxarifado", unique = true, nullable = false)
	public int getIdGrupoAlmoxarifado() {
		return this.idGrupoAlmoxarifado;
	}
	
	public void setIdGrupoAlmoxarifado(int idGrupoAlmoxarifado){
		this.idGrupoAlmoxarifado = idGrupoAlmoxarifado;
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
		result = prime * result + idGrupoAlmoxarifado;
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
		GrupoAlmoxarifado other = (GrupoAlmoxarifado) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idGrupoAlmoxarifado != other.idGrupoAlmoxarifado)
			return false;
		return true;
	}
	
}
