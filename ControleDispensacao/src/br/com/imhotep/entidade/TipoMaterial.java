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
	private static final long serialVersionUID = 8957183054069950729L;
	
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + idTipoMaterial;
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
		TipoMaterial other = (TipoMaterial) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idTipoMaterial != other.idTipoMaterial)
			return false;
		return true;
	}
	
}
