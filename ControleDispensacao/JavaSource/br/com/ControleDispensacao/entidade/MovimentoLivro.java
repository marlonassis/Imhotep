package br.com.ControleDispensacao.entidade;

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
	private Integer quantidadeEntrada;
	private Integer quantidadeSaida;
	private Integer quantidadePerda;
	private Integer saldoAtual;
	private Date dataMovimento;
	private String historico;
	private Usuario usuarioMovimentacao;
	
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
	@JoinColumn(name = "id_unidade")
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
	
	@Column(name = "in_quantidade_entrada")
	public Integer getQuantidadeEntrada() {
		return quantidadeEntrada;
	}
	public void setQuantidadeEntrada(Integer quantidadeEntrada) {
		this.quantidadeEntrada = quantidadeEntrada;
	}
	
	@Column(name = "in_quantidade_saida")
	public Integer getQuantidadeSaida() {
		return quantidadeSaida;
	}
	public void setQuantidadeSaida(Integer quantidadeSaida) {
		this.quantidadeSaida = quantidadeSaida;
	}
	
	@Column(name = "in_quantidade_perda")
	public Integer getQuantidadePerda() {
		return quantidadePerda;
	}
	public void setQuantidadePerda(Integer quantidadePerda) {
		this.quantidadePerda = quantidadePerda;
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
	
	@Column(name = "ds_historico", length = 200)
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
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
