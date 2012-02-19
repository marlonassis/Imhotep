package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "material_prescritor")
public class LiberaMaterialTipoProfissional {
	private int idLiberaMaterialTipoProfissional;
	private TipoProfissional tipoProfissional;
	private Material material;
	
	@Id
	@GeneratedValue
	@Column(name = "id_material_prescritor")
	public int getIdLiberaMaterialTipoProfissional() {
		return this.idLiberaMaterialTipoProfissional;
	}
	
	public void setIdLiberaMaterialTipoProfissional(int idLiberaMaterialTipoProfissional){
		this.idLiberaMaterialTipoProfissional = idLiberaMaterialTipoProfissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_prescritor_id_tipo_prescritor")
	public TipoProfissional getTipoProfissional() {
		return tipoProfissional;
	}
	public void setTipoProfissional(TipoProfissional tipoProfissional) {
		this.tipoProfissional = tipoProfissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "material_id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Material))
			return false;
		
		return ((LiberaMaterialTipoProfissional)obj).getIdLiberaMaterialTipoProfissional() == this.idLiberaMaterialTipoProfissional;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + material.getDescricao().hashCode();
	}

	@Override
	public String toString() {
		return tipoProfissional.getDescricao().concat(" - ").concat(material.getDescricao());
	}
}
