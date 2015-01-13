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
@Table(name = "tb_inventario_contagem", schema="farmacia")
public class InventarioFarmaciaContagem implements Serializable {
	private static final long serialVersionUID = 1891514885280350559L;
	
	private int idInventarioFarmaciaContagem;
	private Profissional profissionalContagem;
	private InventarioMedicamentoEstoque inventarioMedicamentoEstoque;
	private Double quantidadeContada;
	private Date dataContagem; 
	
	@SequenceGenerator(name = "generator", sequenceName = "farmacia.tb_inventario_contagem_id_inventario_contagem_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_inventario_contagem", unique = true, nullable = false)
	public int getIdInventarioFarmaciaContagem() {
		return idInventarioFarmaciaContagem;
	}
	public void setIdInventarioFarmaciaContagem(int idInventarioFarmaciaContagem) {
		this.idInventarioFarmaciaContagem = idInventarioFarmaciaContagem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_inventario_medicamento_estoque")
	public InventarioMedicamentoEstoque getInventarioMedicamentoEstoque() {
		return inventarioMedicamentoEstoque;
	}
	public void setInventarioMedicamentoEstoque(InventarioMedicamentoEstoque inventarioMedicamentoEstoque) {
		this.inventarioMedicamentoEstoque = inventarioMedicamentoEstoque;
	}
	
	@Column(name = "db_quantidade_contada")
	public Double getQuantidadeContada() {
		return quantidadeContada;
	}
	public void setQuantidadeContada(Double quantidadeContada) {
		this.quantidadeContada = quantidadeContada;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_contagem")
	public Date getDataContagem() {
		return dataContagem;
	}
	public void setDataContagem(Date dataContagem) {
		this.dataContagem = dataContagem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_contagem")
	public Profissional getProfissionalContagem() {
		return profissionalContagem;
	}
	public void setProfissionalContagem(Profissional profissionalContagem) {
		this.profissionalContagem = profissionalContagem;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataContagem == null) ? 0 : dataContagem.hashCode());
		result = prime * result + idInventarioFarmaciaContagem;
		result = prime
				* result
				+ ((inventarioMedicamentoEstoque == null) ? 0
						: inventarioMedicamentoEstoque.hashCode());
		result = prime
				* result
				+ ((profissionalContagem == null) ? 0 : profissionalContagem
						.hashCode());
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
		InventarioFarmaciaContagem other = (InventarioFarmaciaContagem) obj;
		if (dataContagem == null) {
			if (other.dataContagem != null)
				return false;
		} else if (!dataContagem.equals(other.dataContagem))
			return false;
		if (idInventarioFarmaciaContagem != other.idInventarioFarmaciaContagem)
			return false;
		if (inventarioMedicamentoEstoque == null) {
			if (other.inventarioMedicamentoEstoque != null)
				return false;
		} else if (!inventarioMedicamentoEstoque
				.equals(other.inventarioMedicamentoEstoque))
			return false;
		if (profissionalContagem == null) {
			if (other.profissionalContagem != null)
				return false;
		} else if (!profissionalContagem.equals(other.profissionalContagem))
			return false;
		if (quantidadeContada == null) {
			if (other.quantidadeContada != null)
				return false;
		} else if (!quantidadeContada.equals(other.quantidadeContada))
			return false;
		return true;
	}
	
}
