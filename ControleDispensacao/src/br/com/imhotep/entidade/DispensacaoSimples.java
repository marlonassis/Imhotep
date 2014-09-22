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
@Table(name = "tb_dispensacao_simples")
public class DispensacaoSimples  implements Serializable {
	private static final long serialVersionUID = -1309274327397598165L;
	
	private int idDispensacaoSimples;
	private Unidade unidadeDispensada;
	private MovimentoLivro movimentoLivro;
	private SolicitacaoMedicamentoUnidadeItem solicitacaoMedicamentoUnidadeItem;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_dispensacao_simples_id_dispensacao_simples_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_dispensacao_simples", unique = true, nullable = false)
	public int getIdDispensacaoSimples() {
		return idDispensacaoSimples;
	}
	
	public void setIdDispensacaoSimples(int idDispensacaoSimples) {
		this.idDispensacaoSimples = idDispensacaoSimples;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_dispensada")
	public Unidade getUnidadeDispensada() {
		return unidadeDispensada;
	}

	public void setUnidadeDispensada(Unidade unidadeDispensada) {
		this.unidadeDispensada = unidadeDispensada;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimento_livro")
	public MovimentoLivro getMovimentoLivro() {
		return movimentoLivro;
	}

	public void setMovimentoLivro(MovimentoLivro movimentoLivro) {
		this.movimentoLivro = movimentoLivro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_solicitacao_medicamento_unidade_item")
	public SolicitacaoMedicamentoUnidadeItem getSolicitacaoMedicamentoUnidadeItem() {
		return solicitacaoMedicamentoUnidadeItem;
	}

	public void setSolicitacaoMedicamentoUnidadeItem(
			SolicitacaoMedicamentoUnidadeItem solicitacaoMedicamentoUnidadeItem) {
		this.solicitacaoMedicamentoUnidadeItem = solicitacaoMedicamentoUnidadeItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idDispensacaoSimples;
		result = prime * result
				+ ((movimentoLivro == null) ? 0 : movimentoLivro.hashCode());
		result = prime
				* result
				+ ((solicitacaoMedicamentoUnidadeItem == null) ? 0
						: solicitacaoMedicamentoUnidadeItem.hashCode());
		result = prime
				* result
				+ ((unidadeDispensada == null) ? 0 : unidadeDispensada
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
		DispensacaoSimples other = (DispensacaoSimples) obj;
		if (idDispensacaoSimples != other.idDispensacaoSimples)
			return false;
		if (movimentoLivro == null) {
			if (other.movimentoLivro != null)
				return false;
		} else if (!movimentoLivro.equals(other.movimentoLivro))
			return false;
		if (solicitacaoMedicamentoUnidadeItem == null) {
			if (other.solicitacaoMedicamentoUnidadeItem != null)
				return false;
		} else if (!solicitacaoMedicamentoUnidadeItem
				.equals(other.solicitacaoMedicamentoUnidadeItem))
			return false;
		if (unidadeDispensada == null) {
			if (other.unidadeDispensada != null)
				return false;
		} else if (!unidadeDispensada.equals(other.unidadeDispensada))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if(unidadeDispensada != null)
			return unidadeDispensada.toString();
		return super.toString();
	}

}
