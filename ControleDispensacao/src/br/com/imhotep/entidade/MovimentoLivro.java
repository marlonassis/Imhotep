package br.com.imhotep.entidade;

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
@Table(name = "tb_movimento_livro")
public class MovimentoLivro {
	private int idMovimentoLivro;
	private Unidade unidadeCadastrante;
	private TipoMovimento tipoMovimento;
	private Integer saldoAnterior;
	private Integer quantidadeMovimentacao;
	private Date dataMovimento;
	private Usuario usuarioMovimentacao;
	private Estoque estoque;
	private DispensacaoSimples dispensacaoSimples;
	
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
	@JoinColumn(name = "id_unidade_cadastrante")
	public Unidade getUnidadeCadastrante() {
		return unidadeCadastrante;
	}
	public void setUnidadeCadastrante(Unidade unidadeCadastrante) {
		this.unidadeCadastrante = unidadeCadastrante;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_movimento")
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
	@Column(name = "in_saldo_anterior")
	public Integer getSaldoAnterior() {
		return saldoAnterior;
	}
	public void setSaldoAnterior(Integer saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
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
	
	
	@OneToOne(mappedBy="movimentoLivro")  
	public DispensacaoSimples getDispensacaoSimples() {
		return dispensacaoSimples;
	}
	public void setDispensacaoSimples(DispensacaoSimples dispensacaoSimples) {
		this.dispensacaoSimples = dispensacaoSimples;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof MovimentoLivro))
			return false;
		
		return ((MovimentoLivro)obj).getIdMovimentoLivro() == this.idMovimentoLivro;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + estoque.hashCode() + usuarioMovimentacao.hashCode() + dataMovimento.hashCode();
	}

	@Override
	public String toString() {
		return tipoMovimento.getDescricao().concat(" - ").concat(estoque.getMaterial().getDescricao());
	}
}
