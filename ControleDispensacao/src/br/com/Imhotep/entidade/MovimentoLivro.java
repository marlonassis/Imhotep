package br.com.Imhotep.entidade;

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
@Table(name = "tb_movimento_livro")
public class MovimentoLivro {
	private int idMovimentoLivro;
	private MovimentoGeral movimentoGeral;
	private Unidade unidade;
	private Material material;
	private TipoMovimento tipoMovimento;
	private Integer saldoAnterior;
	private Integer quantidadeMovimentacao;
	private Integer saldoAtual;
	private Date dataMovimento;
	private Usuario usuarioMovimentacao;
	private Unidade unidadeReceptora;
	private Estoque estoque;
	
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
	@JoinColumn(name = "id_movimento_geral")
	public MovimentoGeral getMovimentoGeral() {
		return movimentoGeral;
	}
	public void setMovimentoGeral(MovimentoGeral movimentoGeral) {
		this.movimentoGeral = movimentoGeral;
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
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
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
	
	@Column(name = "in_saldo_atual")
	public Integer getSaldoAtual() {
		return saldoAtual;
	}
	public void setSaldoAtual(Integer saldoAtual) {
		this.saldoAtual = saldoAtual;
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
	@JoinColumn(name = "id_unidade_receptora")
	public Unidade getUnidadeReceptora() {
		return unidadeReceptora;
	}
	public void setUnidadeReceptora(Unidade unidadeReceptora) {
		this.unidadeReceptora = unidadeReceptora;
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
	    return hash * 31 + tipoMovimento.getDescricao().concat(material.getDescricao()).hashCode();
	}

	@Override
	public String toString() {
		return tipoMovimento.getDescricao().concat(" - ").concat(material.getDescricao());
	}
}
