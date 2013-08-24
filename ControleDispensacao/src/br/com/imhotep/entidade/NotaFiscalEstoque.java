package br.com.imhotep.entidade;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.NotaFiscal;
import br.com.imhotep.entidade.Profissional;

import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;


@Entity
@Table(name = "tb_nota_fiscal_estoque")
public class NotaFiscalEstoque {
	private Estoque estoque;
	private NotaFiscal notaFiscal;
	private Profissional profissionalInsercao;
	private Date dataInsercao;
	private int idNotaFiscalEstoque;
	private Double valorUnitario;
	private int quantidadeEntrada;
	private MovimentoLivro movimentoLivro;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estoque")	
	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_nota_fiscal")	
	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_insercao")	
	public Profissional getProfissionalInsercao() {
		return profissionalInsercao;
	}

	public void setProfissionalInsercao(Profissional profissionalInsercao) {
		this.profissionalInsercao = profissionalInsercao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_insercao")	
	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	@SequenceGenerator(name = "generator", sequenceName = "public.tb_nota_fiscal_estoque_id_nota_fiscal_estoque_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_nota_fiscal_estoque", unique = true, nullable = false)	
	public int getIdNotaFiscalEstoque() {
		return idNotaFiscalEstoque;
	}

	public void setIdNotaFiscalEstoque(int idNotaFiscalEstoque) {
		this.idNotaFiscalEstoque = idNotaFiscalEstoque;
	}
	
	@Column(name = "db_valor_unitario")
	public Double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	@Column(name = "in_quantidade_entrada")
	public int getQuantidadeEntrada() {
		return quantidadeEntrada;
	}
	public void setQuantidadeEntrada(int quantidadeEntrada) {
		this.quantidadeEntrada = quantidadeEntrada;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimento_livro")	
	public MovimentoLivro getMovimentoLivro() {
		return movimentoLivro;
	}

	public void setMovimentoLivro(MovimentoLivro movimentoLivro) {
		this.movimentoLivro = movimentoLivro;
	}
	
	@Transient
	public Double getTotal(){
		if(getValorUnitario() != null){
			return getQuantidadeEntrada() * getValorUnitario();
		}
		return 0d;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof NotaFiscalEstoque))
			return false;
		
		return ((NotaFiscalEstoque)obj).getIdNotaFiscalEstoque() == this.idNotaFiscalEstoque;
	}
}
