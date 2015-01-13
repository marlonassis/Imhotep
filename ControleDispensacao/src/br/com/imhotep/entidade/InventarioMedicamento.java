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
@Table(name = "tb_inventario_medicamento", schema="farmacia")
public class InventarioMedicamento implements Serializable {
	private static final long serialVersionUID = 160887520535499488L;
	
	private int idInventarioMedicamento;
	private Material medicamento;
	private InventarioFarmacia inventarioFarmacia;
	
	@SequenceGenerator(name = "generator", sequenceName = "farmacia.tb_inventario_medicamento_id_inventario_medicamento_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_inventario_medicamento", unique = true, nullable = false)
	public int getIdInventarioMedicamento() {
		return idInventarioMedicamento;
	}
	public void setIdInventarioMedicamento(int idInventarioMedicamento) {
		this.idInventarioMedicamento = idInventarioMedicamento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_medicamento")
	public Material getMedicamento() {
		return medicamento;
	}
	public void setMedicamento(Material medicamento) {
		this.medicamento = medicamento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_inventario")
	public InventarioFarmacia getInventarioFarmacia() {
		return inventarioFarmacia;
	}
	public void setInventarioFarmacia(InventarioFarmacia inventarioFarmacia) {
		this.inventarioFarmacia = inventarioFarmacia;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idInventarioMedicamento;
		result = prime
				* result
				+ ((inventarioFarmacia == null) ? 0 : inventarioFarmacia
						.hashCode());
		result = prime * result
				+ ((medicamento == null) ? 0 : medicamento.hashCode());
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
		InventarioMedicamento other = (InventarioMedicamento) obj;
		if (idInventarioMedicamento != other.idInventarioMedicamento)
			return false;
		if (inventarioFarmacia == null) {
			if (other.inventarioFarmacia != null)
				return false;
		} else if (!inventarioFarmacia.equals(other.inventarioFarmacia))
			return false;
		if (medicamento == null) {
			if (other.medicamento != null)
				return false;
		} else if (!medicamento.equals(other.medicamento))
			return false;
		return true;
	}
	
}
