package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_tipo_material")
public class TipoMaterial implements Serializable {
	private static final long serialVersionUID = 2291419033808469794L;
	
	private int idTipoMaterial;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_tipo_material_id_tipo_material_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_tipo_material", unique = true, nullable = false)
	public int getIdTipoMaterial() {
		return this.idTipoMaterial;
	}
	
	public void setIdTipoMaterial(int idTipoMaterial){
		this.idTipoMaterial = idTipoMaterial;
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
		if(!(obj instanceof TipoMaterial))
			return false;
		
		return ((TipoMaterial)obj).getIdTipoMaterial() == this.idTipoMaterial;
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
