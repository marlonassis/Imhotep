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
@Table(name = "tb_inventario_medicamento_estoque", schema="farmacia")
public class InventarioMedicamentoEstoque implements Serializable {
	private static final long serialVersionUID = -7681351000414540973L;
	
	private int idInventarioMedicamentoEstoque;
	private Estoque estoque;
	private InventarioMedicamento inventarioMedicamento;
	private MovimentoLivro movimentoLivro;
	
	@SequenceGenerator(name = "generator", sequenceName = "farmacia.tb_inventario_medicamento_est_id_inventario_medicamento_est_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_inventario_medicamento_estoque", unique = true, nullable = false)
	public int getIdInventarioMedicamentoEstoque() {
		return idInventarioMedicamentoEstoque;
	}
	public void setIdInventarioMedicamentoEstoque(int idInventarioMedicamentoEstoque) {
		this.idInventarioMedicamentoEstoque = idInventarioMedicamentoEstoque;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estoque")
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_inventario_medicamento")
	public InventarioMedicamento getInventarioMedicamento() {
		return inventarioMedicamento;
	}
	public void setInventarioMedicamento(InventarioMedicamento inventarioMedicamento) {
		this.inventarioMedicamento = inventarioMedicamento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimento_livro")
	public MovimentoLivro getMovimentoLivro() {
		return movimentoLivro;
	}
	public void setMovimentoLivro(MovimentoLivro movimentoLivro) {
		this.movimentoLivro = movimentoLivro;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estoque == null) ? 0 : estoque.hashCode());
		result = prime * result + idInventarioMedicamentoEstoque;
		result = prime
				* result
				+ ((inventarioMedicamento == null) ? 0 : inventarioMedicamento
						.hashCode());
		result = prime * result
				+ ((movimentoLivro == null) ? 0 : movimentoLivro.hashCode());
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
		InventarioMedicamentoEstoque other = (InventarioMedicamentoEstoque) obj;
		if (estoque == null) {
			if (other.estoque != null)
				return false;
		} else if (!estoque.equals(other.estoque))
			return false;
		if (idInventarioMedicamentoEstoque != other.idInventarioMedicamentoEstoque)
			return false;
		if (inventarioMedicamento == null) {
			if (other.inventarioMedicamento != null)
				return false;
		} else if (!inventarioMedicamento.equals(other.inventarioMedicamento))
			return false;
		if (movimentoLivro == null) {
			if (other.movimentoLivro != null)
				return false;
		} else if (!movimentoLivro.equals(other.movimentoLivro))
			return false;
		return true;
	}
	
}
