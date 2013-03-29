package br.com.Imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_unidade_material")
public class UnidadeMaterial {
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
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof UnidadeMaterial))
			return false;
		
		return ((UnidadeMaterial)obj).getIdUnidadeMaterial() == this.idUnidadeMaterial;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + descricao.hashCode();
	}

	@Override
	public String toString() {
		return descricao;
	}
}
