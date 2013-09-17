package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_fabricante")
public class Fabricante {
	private int idFabricante;
	private String descricao;

	@SequenceGenerator(name = "generator", sequenceName = "public.tb_fabricante_id_fabricante_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_fabricante", unique = true, nullable = false)
	public int getIdFabricante() {
		return this.idFabricante;
	}
	
	public void setIdFabricante(int idFabricante){
		this.idFabricante = idFabricante;
	}

	@Column(name = "cv_descricao", length = 120)
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
