package br.com.imhotep.entidade;

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
@Table(name="tb_devolucao_medicamento_item_movimento")
public class DevolucaoMedicamentoItemMovimento {
	
	private int idDevolucaoMedicamentoItemMovimento;
	private DevolucaoMedicamentoItem devolucaoMedicamentoItem;
	private MovimentoLivro movimentoLivro;
	private Date dataInsercao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_devolucao_medicamento_item_id_devolucao_medicamento_ite_seq1")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_devolucao_medicamento_item_movimento", unique = true, nullable = false)
	public int getIdDevolucaoMedicamentoItemMovimento() {
		return idDevolucaoMedicamentoItemMovimento;
	}

	public void setIdDevolucaoMedicamentoItemMovimento(int idDevolucaoMedicamentoItemMovimento) {
		this.idDevolucaoMedicamentoItemMovimento = idDevolucaoMedicamentoItemMovimento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_devolucao_medicamento_item")
	public DevolucaoMedicamentoItem getDevolucaoMedicamentoItem() {
		return devolucaoMedicamentoItem;
	}

	public void setDevolucaoMedicamentoItem(DevolucaoMedicamentoItem devolucaoMedicamentoItem) {
		this.devolucaoMedicamentoItem = devolucaoMedicamentoItem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimento_livro")
	public MovimentoLivro getMovimentoLivro() {
		return movimentoLivro;
	}

	public void setMovimentoLivro(MovimentoLivro movimentoLivro) {
		this.movimentoLivro = movimentoLivro;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_insercao")
	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
}
