package br.com.imhotep.entidade;

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
@Table(name = "tb_familia")
public class Familia {
	private int idFamilia;
	private SubGrupo subGrupo;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_familia_id_familia_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_familia", unique = true, nullable = false)
	public int getIdFamilia() {
		return this.idFamilia;
	}
	
	public void setIdFamilia(int idFamilia){
		this.idFamilia = idFamilia;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sub_grupo")
	public SubGrupo getSubGrupo(){
		return subGrupo;
	}
	
	public void setSubGrupo(SubGrupo subGrupo){
		this.subGrupo = subGrupo;
	}
	
	@Column(name = "cv_descricao", length = 120)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + idFamilia;
		result = prime * result
				+ ((subGrupo == null) ? 0 : subGrupo.hashCode());
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
		Familia other = (Familia) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idFamilia != other.idFamilia)
			return false;
		if (subGrupo == null) {
			if (other.subGrupo != null)
				return false;
		} else if (!subGrupo.equals(other.subGrupo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
}
