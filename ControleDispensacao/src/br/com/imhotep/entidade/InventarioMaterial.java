package br.com.imhotep.entidade;

import java.io.Serializable;

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
@Table(name = "tb_inventario_material", schema="almoxarifado")
public class InventarioMaterial implements Serializable {
	private static final long serialVersionUID = 8171048657487783339L;
	
	private int idInventarioMaterial;
	private MaterialAlmoxarifado material;
	private InventarioAlmoxarifado inventarioAlmoxarifado;
	
	@SequenceGenerator(name = "generator", sequenceName = "almoxarifado.tb_inventario_material_id_inventario_material_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_inventario_material", unique = true, nullable = false)
	public int getIdInventarioMaterial() {
		return idInventarioMaterial;
	}
	public void setIdInventarioMaterial(int idInventarioMaterial) {
		this.idInventarioMaterial = idInventarioMaterial;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public MaterialAlmoxarifado getMaterial() {
		return material;
	}
	public void setMaterial(MaterialAlmoxarifado material) {
		this.material = material;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_inventario")
	public InventarioAlmoxarifado getInventarioAlmoxarifado() {
		return inventarioAlmoxarifado;
	}
	public void setInventarioAlmoxarifado(InventarioAlmoxarifado inventarioAlmoxarifado) {
		this.inventarioAlmoxarifado = inventarioAlmoxarifado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idInventarioMaterial;
		result = prime
				* result
				+ ((inventarioAlmoxarifado == null) ? 0 : inventarioAlmoxarifado
						.hashCode());
		result = prime * result
				+ ((material == null) ? 0 : material.hashCode());
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
		InventarioMaterial other = (InventarioMaterial) obj;
		if (idInventarioMaterial != other.idInventarioMaterial)
			return false;
		if (inventarioAlmoxarifado == null) {
			if (other.inventarioAlmoxarifado != null)
				return false;
		} else if (!inventarioAlmoxarifado.equals(other.inventarioAlmoxarifado))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		return true;
	}
	
}
