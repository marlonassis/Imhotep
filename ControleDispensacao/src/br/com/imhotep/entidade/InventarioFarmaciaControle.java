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
@Table(name = "tb_inventario_controle", schema="farmacia")
public class InventarioFarmaciaControle implements Serializable {
	private static final long serialVersionUID = -3882330806873739012L;
	
	private int idInventarioFarmacia;
	private Material material;
	private Profissional profisionalCadastro;
	
	@SequenceGenerator(name = "generator", sequenceName = "farmacia.tb_inventario_controle_id_inventario_controle_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_inventario_controle", unique = true, nullable = false)
	public int getIdInventarioFarmacia() {
		return this.idInventarioFarmacia;
	}
	
	public void setIdInventarioFarmacia(int idInventarioFarmacia){
		this.idInventarioFarmacia = idInventarioFarmacia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return this.material;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_cadastro")
	public Profissional getProfisionalCadastro() {
		return this.profisionalCadastro;
	}
	
	public void setProfisionalCadastro(Profissional profisionalCadastro) {
		this.profisionalCadastro = profisionalCadastro;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idInventarioFarmacia;
		result = prime * result
				+ ((material == null) ? 0 : material.hashCode());
		result = prime
				* result
				+ ((profisionalCadastro == null) ? 0 : profisionalCadastro
						.hashCode());
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
		InventarioFarmaciaControle other = (InventarioFarmaciaControle) obj;
		if (idInventarioFarmacia != other.idInventarioFarmacia)
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (profisionalCadastro == null) {
			if (other.profisionalCadastro != null)
				return false;
		} else if (!profisionalCadastro.equals(other.profisionalCadastro))
			return false;
		return true;
	}
	
}
