package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_inventario", schema="almoxarifado")
public class InventarioAlmoxarifado implements Serializable {
	private static final long serialVersionUID = 5929937705302460137L;
	
	private int idInventarioAlmoxarifado;
	private Integer quantidadeContada;
	private MaterialAlmoxarifado material;
	private String lote;
	private Integer quantidadeAtualEstoque;
	private Date dataCadastro;
	private Profissional profisionalCadastro;
	
	@SequenceGenerator(name = "generator", sequenceName = "almoxarifado.tb_inventario_id_inventario_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_inventario", unique = true, nullable = false)
	public int getIdInventarioAlmoxarifado() {
		return this.idInventarioAlmoxarifado;
	}
	
	public void setIdInventarioAlmoxarifado(int idInventarioAlmoxarifado){
		this.idInventarioAlmoxarifado = idInventarioAlmoxarifado;
	}
	
	@Column(name = "cv_lote")
	public String getLote() {
		return this.lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}
	
	@Column(name = "in_quantidade_atual_estoque")
	public Integer getQuantidadeAtualEstoque() {
		return this.quantidadeAtualEstoque;
	}

	public void setQuantidadeAtualEstoque(Integer quantidadeAtualEstoque) {
		this.quantidadeAtualEstoque = quantidadeAtualEstoque;
	}
	
	@Column(name = "in_quantidade_contada")
	public Integer getQuantidadeContada() {
		return this.quantidadeContada;
	}

	public void setQuantidadeContada(Integer quantidadeContada) {
		this.quantidadeContada = quantidadeContada;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public MaterialAlmoxarifado getMaterial() {
		return this.material;
	}
	
	public void setMaterial(MaterialAlmoxarifado material) {
		this.material = material;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profisional_cadastro")
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
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + idInventarioAlmoxarifado;
		result = prime * result + ((lote == null) ? 0 : lote.hashCode());
		result = prime * result
				+ ((material == null) ? 0 : material.hashCode());
		result = prime
				* result
				+ ((profisionalCadastro == null) ? 0 : profisionalCadastro
						.hashCode());
		result = prime
				* result
				+ ((quantidadeAtualEstoque == null) ? 0
						: quantidadeAtualEstoque.hashCode());
		result = prime
				* result
				+ ((quantidadeContada == null) ? 0 : quantidadeContada
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
		InventarioAlmoxarifado other = (InventarioAlmoxarifado) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (idInventarioAlmoxarifado != other.idInventarioAlmoxarifado)
			return false;
		if (lote == null) {
			if (other.lote != null)
				return false;
		} else if (!lote.equals(other.lote))
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
		if (quantidadeAtualEstoque == null) {
			if (other.quantidadeAtualEstoque != null)
				return false;
		} else if (!quantidadeAtualEstoque.equals(other.quantidadeAtualEstoque))
			return false;
		if (quantidadeContada == null) {
			if (other.quantidadeContada != null)
				return false;
		} else if (!quantidadeContada.equals(other.quantidadeContada))
			return false;
		return true;
	}
	
}
