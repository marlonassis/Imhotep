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
@Table(name = "tb_movimento_livro_almoxarifado")
public class MovimentoLivroAlmoxarifado {
	private int idMovimentoLivroAlmoxarifado;
	private TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado;
	private Integer quantidadeMovimentacao;
	private Date dataMovimento;
	private Profissional profissionalInsercao;
	private EstoqueAlmoxarifado estoqueAlmoxarifado;
	private Integer quantidadeAtual;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_movimento_livro_almoxarifa_id_movimento_livro_almoxarifa_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_movimento_livro_almoxarifado", unique = true, nullable = false)
	public int getIdMovimentoLivro() {
		return idMovimentoLivroAlmoxarifado;
	}
	public void setIdMovimentoLivro(int idMovimentoLivro) {
		this.idMovimentoLivroAlmoxarifado = idMovimentoLivro;
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof MovimentoLivroAlmoxarifado))
			return false;
		
		return ((MovimentoLivroAlmoxarifado)obj).getIdMovimentoLivro() == this.idMovimentoLivroAlmoxarifado;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + estoqueAlmoxarifado.hashCode() + profissionalInsercao.hashCode() + dataMovimento.hashCode();
	}

	@Override
	public String toString() {
		return tipoMovimentoAlmoxarifado.getDescricao().concat(" - ").concat(estoqueAlmoxarifado.getMaterialAlmoxarifado().getDescricao());
	}
}
