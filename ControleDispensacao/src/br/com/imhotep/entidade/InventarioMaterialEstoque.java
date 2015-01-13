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
@Table(name = "tb_inventario_material_estoque", schema="almoxarifado")
public class InventarioMaterialEstoque implements Serializable {
	private static final long serialVersionUID = -8386311631337015072L;
	
	private int idInventarioMaterialEstoque;
	private EstoqueAlmoxarifado estoque;
	private InventarioMaterial inventarioMaterial;
	private MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado;
	
	@SequenceGenerator(name = "generator", sequenceName = "almoxarifado.tb_inventario_material_estoqu_id_inventario_material_estoqu_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_inventario_material_estoque", unique = true, nullable = false)
	public int getIdInventarioMaterialEstoque() {
		return idInventarioMaterialEstoque;
	}
	public void setIdInventarioMaterialEstoque(int idInventarioMaterialEstoque) {
		this.idInventarioMaterialEstoque = idInventarioMaterialEstoque;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estoque_almoxarifado")
	public EstoqueAlmoxarifado getEstoque() {
		return estoque;
	}
	public void setEstoque(EstoqueAlmoxarifado estoque) {
		this.estoque = estoque;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_inventario_material")
	public InventarioMaterial getInventarioMaterial() {
		return inventarioMaterial;
	}
	public void setInventarioMaterial(InventarioMaterial inventarioMaterial) {
		this.inventarioMaterial = inventarioMaterial;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimento_livro_almoxarifado")
	public MovimentoLivroAlmoxarifado getMovimentoLivroAlmoxarifado() {
		return movimentoLivroAlmoxarifado;
	}
	public void setMovimentoLivroAlmoxarifado(MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado) {
		this.movimentoLivroAlmoxarifado = movimentoLivroAlmoxarifado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estoque == null) ? 0 : estoque.hashCode());
		result = prime * result + idInventarioMaterialEstoque;
		result = prime
				* result
				+ ((inventarioMaterial == null) ? 0 : inventarioMaterial
						.hashCode());
		result = prime * result
				+ ((movimentoLivroAlmoxarifado == null) ? 0 : movimentoLivroAlmoxarifado.hashCode());
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
		InventarioMaterialEstoque other = (InventarioMaterialEstoque) obj;
		if (estoque == null) {
			if (other.estoque != null)
				return false;
		} else if (!estoque.equals(other.estoque))
			return false;
		if (idInventarioMaterialEstoque != other.idInventarioMaterialEstoque)
			return false;
		if (inventarioMaterial == null) {
			if (other.inventarioMaterial != null)
				return false;
		} else if (!inventarioMaterial.equals(other.inventarioMaterial))
			return false;
		if (movimentoLivroAlmoxarifado == null) {
			if (other.movimentoLivroAlmoxarifado != null)
				return false;
		} else if (!movimentoLivroAlmoxarifado.equals(other.movimentoLivroAlmoxarifado))
			return false;
		return true;
	}
	
}
