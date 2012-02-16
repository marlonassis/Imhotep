package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unidade_material")
public class UnidadeMaterial {
	private int idUnidadeMaterial;
	private String descricao;
	
	@Id
	@GeneratedValue
	@Column(name = "id_unidade_material")
	public int getIdUnidadeMaterial() {
		return this.idUnidadeMaterial;
	}
	
	public void setIdUnidadeMaterial(int idUnidadeMaterial){
		this.idUnidadeMaterial = idUnidadeMaterial;
	}
	
	@Column(name = "unidade", length = 2)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
