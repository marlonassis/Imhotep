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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_movimento_livro_almoxarifado")
public class MovimentoLivroAlmoxarifado implements Serializable {
	private static final long serialVersionUID = 3514665185879536756L;
	
	private int idMovimentoLivroAlmoxarifado;
	private TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado;
	private Double quantidadeMovimentacao;
	private Date dataMovimento;
	private Profissional profissionalInsercao;
	private EstoqueAlmoxarifado estoqueAlmoxarifado;
	private Double quantidadeAtual;
	private DispensacaoSimplesAlmoxarifado dispensacaoSimplesAlmoxarifado;
	private String justificativa;

	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_movimento_livro_almoxarifa_id_movimento_livro_almoxarifa_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_movimento_livro_almoxarifado", unique = true, nullable = false)
	public int getIdMovimentoLivroAlmoxarifado() {
		return idMovimentoLivroAlmoxarifado;
	}
	public void setIdMovimentoLivroAlmoxarifado(int idMovimentoLivroAlmoxarifado) {
		this.idMovimentoLivroAlmoxarifado = idMovimentoLivroAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_insercao")
	public Profissional getProfissionalInsercao() {
		return profissionalInsercao;
	}
	public void setProfissionalInsercao(Profissional profissionalInsercao) {
		this.profissionalInsercao = profissionalInsercao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_movimento_almoxarifado")
	public TipoMovimentoAlmoxarifado getTipoMovimentoAlmoxarifado() {
		return tipoMovimentoAlmoxarifado;
	}
	public void setTipoMovimentoAlmoxarifado(TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado) {
		this.tipoMovimentoAlmoxarifado = tipoMovimentoAlmoxarifado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_movimento")
	public Date getDataMovimento() {
		return dataMovimento;
	}
	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estoque_almoxarifado")
	public EstoqueAlmoxarifado getEstoqueAlmoxarifado() {
		return estoqueAlmoxarifado;
	}
	public void setEstoqueAlmoxarifado(EstoqueAlmoxarifado estoqueAlmoxarifado) {
		this.estoqueAlmoxarifado = estoqueAlmoxarifado;
	}
	
	@Column(name = "in_quantidade_movimentacao")
	public Double getQuantidadeMovimentacao() {
		return quantidadeMovimentacao;
	}
	public void setQuantidadeMovimentacao(Double quantidadeMovimentacao) {
		this.quantidadeMovimentacao = quantidadeMovimentacao;
	}
	
	@Column(name = "in_quantidade_atual")
	public Double getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(Double quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	
	@OneToOne(mappedBy="movimentoLivroAlmoxarifado")  
	public DispensacaoSimplesAlmoxarifado getDispensacaoSimplesAlmoxarifado() {
		return dispensacaoSimplesAlmoxarifado;
	}
	public void setDispensacaoSimplesAlmoxarifado(DispensacaoSimplesAlmoxarifado dispensacaoSimplesAlmoxarifado) {
		this.dispensacaoSimplesAlmoxarifado = dispensacaoSimplesAlmoxarifado;
	}
	
	@Column(name="cv_justificativa")
	public String getJustificativa(){
		return this.justificativa;
	}
	
	public void setJustificativa(String justificativa){
		this.justificativa = justificativa;
	}
	
	@Override
	public String toString() {
		return tipoMovimentoAlmoxarifado.getDescricao().concat(" - ").concat(estoqueAlmoxarifado.getMaterialAlmoxarifado().getDescricao());
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataMovimento == null) ? 0 : dataMovimento.hashCode());
		result = prime
				* result
				+ ((estoqueAlmoxarifado == null) ? 0 : estoqueAlmoxarifado
						.hashCode());
		result = prime * result + idMovimentoLivroAlmoxarifado;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime
				* result
				+ ((profissionalInsercao == null) ? 0 : profissionalInsercao
						.hashCode());
		result = prime * result
				+ ((quantidadeAtual == null) ? 0 : quantidadeAtual.hashCode());
		result = prime
				* result
				+ ((quantidadeMovimentacao == null) ? 0
						: quantidadeMovimentacao.hashCode());
		result = prime
				* result
				+ ((tipoMovimentoAlmoxarifado == null) ? 0
						: tipoMovimentoAlmoxarifado.hashCode());
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
		MovimentoLivroAlmoxarifado other = (MovimentoLivroAlmoxarifado) obj;
		if (dataMovimento == null) {
			if (other.dataMovimento != null)
				return false;
		} else if (!dataMovimento.equals(other.dataMovimento))
			return false;
		if (dispensacaoSimplesAlmoxarifado == null) {
			if (other.dispensacaoSimplesAlmoxarifado != null)
				return false;
		} else if (!dispensacaoSimplesAlmoxarifado
				.equals(other.dispensacaoSimplesAlmoxarifado))
			return false;
		if (estoqueAlmoxarifado == null) {
			if (other.estoqueAlmoxarifado != null)
				return false;
		} else if (!estoqueAlmoxarifado.equals(other.estoqueAlmoxarifado))
			return false;
		if (idMovimentoLivroAlmoxarifado != other.idMovimentoLivroAlmoxarifado)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (profissionalInsercao == null) {
			if (other.profissionalInsercao != null)
				return false;
		} else if (!profissionalInsercao.equals(other.profissionalInsercao))
			return false;
		if (quantidadeAtual == null) {
			if (other.quantidadeAtual != null)
				return false;
		} else if (!quantidadeAtual.equals(other.quantidadeAtual))
			return false;
		if (quantidadeMovimentacao == null) {
			if (other.quantidadeMovimentacao != null)
				return false;
		} else if (!quantidadeMovimentacao.equals(other.quantidadeMovimentacao))
			return false;
		if (tipoMovimentoAlmoxarifado == null) {
			if (other.tipoMovimentoAlmoxarifado != null)
				return false;
		} else if (!tipoMovimentoAlmoxarifado
				.equals(other.tipoMovimentoAlmoxarifado))
			return false;
		return true;
	}
	
}
