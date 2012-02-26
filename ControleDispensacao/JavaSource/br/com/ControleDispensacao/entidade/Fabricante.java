package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_fabricante")
public class Fabricante {
	private int idFabricante;
	private String descricao;

	@Id
	@GeneratedValue
	@Column(name = "id_fabricante")
	public int getIdFabricante() {
		return this.idFabricante;
	}
	
	public void setIdFabricante(int idFabricante){
		this.idFabricante = idFabricante;
	}

	@Column(name = "ds_descricao", length = 120)
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
		if(!(obj instanceof Fabricante))
			return false;
		
		return ((Fabricante)obj).getIdFabricante() == this.idFabricante;
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
