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
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoOperacaoEnum;

@Entity
@Table(name = "tb_movimento_livro")
public class MovimentoLivro implements Serializable {
	private static final long serialVersionUID = -6772557323992726612L;
	
	private int idMovimentoLivro;
	private TipoMovimento tipoMovimento;
	private Integer quantidadeMovimentacao;
	private Date dataMovimento;
	private Usuario usuarioMovimentacao;
	private Estoque estoque;
	private DispensacaoSimples dispensacaoSimples;
	private Integer quantidadeAtual;
	private String justificativa;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_movimento_livro_id_movimento_livro_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_movimento_livro", unique = true, nullable = false)
	public int getIdMovimentoLivro() {
		return idMovimentoLivro;
	}
	public void setIdMovimentoLivro(int idMovimentoLivro) {
		this.idMovimentoLivro = idMovimentoLivro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_movimentacao")
	public Usuario getUsuarioMovimentacao() {
		return usuarioMovimentacao;
	}
	public void setUsuarioMovimentacao(Usuario usuarioMovimentacao) {
		this.usuarioMovimentacao = usuarioMovimentacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_movimento")
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
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
	@JoinColumn(name = "id_estoque")
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
	@Column(name = "in_quantidade_movimentacao")
	public Integer getQuantidadeMovimentacao() {
		return quantidadeMovimentacao;
	}
	public void setQuantidadeMovimentacao(Integer quantidadeMovimentacao) {
		this.quantidadeMovimentacao = quantidadeMovimentacao;
	}
	
	@Column(name = "in_quantidade_atual")
	public Integer getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(Integer quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	
	@OneToOne(mappedBy="movimentoLivro")  
	public DispensacaoSimples getDispensacaoSimples() {
		return dispensacaoSimples;
	}
	public void setDispensacaoSimples(DispensacaoSimples dispensacaoSimples) {
		this.dispensacaoSimples = dispensacaoSimples;
	}
	
	@Column(name="cv_justificativa")
	public String getJustificativa(){
		return this.justificativa;
	}
	
	public void setJustificativa(String justificativa){
		this.justificativa = justificativa;
	}
	
	@Transient
	public Integer getQuantidadeFinal(){
		if(getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.E))
			return getQuantidadeAtual().intValue() + getQuantidadeMovimentacao().intValue();
		else
			return getQuantidadeAtual().intValue() - getQuantidadeMovimentacao().intValue();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataMovimento == null) ? 0 : dataMovimento.hashCode());
		result = prime * result + ((estoque == null) ? 0 : estoque.hashCode());
		result = prime * result + idMovimentoLivro;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime * result
				+ ((quantidadeAtual == null) ? 0 : quantidadeAtual.hashCode());
		result = prime
				* result
				+ ((quantidadeMovimentacao == null) ? 0
						: quantidadeMovimentacao.hashCode());
		result = prime * result
				+ ((tipoMovimento == null) ? 0 : tipoMovimento.hashCode());
		result = prime
				* result
				+ ((usuarioMovimentacao == null) ? 0 : usuarioMovimentacao
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
		MovimentoLivro other = (MovimentoLivro) obj;
		if (dataMovimento == null) {
			if (other.dataMovimento != null)
				return false;
		} else if (!dataMovimento.equals(other.dataMovimento))
			return false;
		if (estoque == null) {
			if (other.estoque != null)
				return false;
		} else if (!estoque.equals(other.estoque))
			return false;
		if (idMovimentoLivro != other.idMovimentoLivro)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
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
		if (tipoMovimento == null) {
			if (other.tipoMovimento != null)
				return false;
		} else if (!tipoMovimento.equals(other.tipoMovimento))
			return false;
		if (usuarioMovimentacao == null) {
			if (other.usuarioMovimentacao != null)
				return false;
		} else if (!usuarioMovimentacao.equals(other.usuarioMovimentacao))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return tipoMovimento.getDescricao().concat(" - ").concat(estoque.getMaterial().getDescricao());
	}
}
