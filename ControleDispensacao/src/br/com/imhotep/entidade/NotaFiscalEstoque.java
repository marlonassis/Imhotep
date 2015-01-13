package br.com.imhotep.entidade;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;
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
public class NotaFiscalEstoque implements Serializable {
	private static final long serialVersionUID = -3211584641495242393L;
	
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataInsercao == null) ? 0 : dataInsercao.hashCode());
		result = prime * result + ((estoque == null) ? 0 : estoque.hashCode());
		result = prime * result + idNotaFiscalEstoque;
		result = prime * result
				+ ((movimentoLivro == null) ? 0 : movimentoLivro.hashCode());
		result = prime * result
				+ ((notaFiscal == null) ? 0 : notaFiscal.hashCode());
		result = prime
				* result
				+ ((profissionalInsercao == null) ? 0 : profissionalInsercao
						.hashCode());
		result = prime * result + quantidadeEntrada;
		result = prime * result
				+ ((valorUnitario == null) ? 0 : valorUnitario.hashCode());
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
		NotaFiscalEstoque other = (NotaFiscalEstoque) obj;
		if (dataInsercao == null) {
			if (other.dataInsercao != null)
				return false;
		} else if (!dataInsercao.equals(other.dataInsercao))
			return false;
		if (estoque == null) {
			if (other.estoque != null)
				return false;
		} else if (!estoque.equals(other.estoque))
			return false;
		if (idNotaFiscalEstoque != other.idNotaFiscalEstoque)
			return false;
		if (movimentoLivro == null) {
			if (other.movimentoLivro != null)
				return false;
		} else if (!movimentoLivro.equals(other.movimentoLivro))
			return false;
		if (notaFiscal == null) {
			if (other.notaFiscal != null)
				return false;
		} else if (!notaFiscal.equals(other.notaFiscal))
			return false;
		if (profissionalInsercao == null) {
			if (other.profissionalInsercao != null)
				return false;
		} else if (!profissionalInsercao.equals(other.profissionalInsercao))
			return false;
		if (quantidadeEntrada != other.quantidadeEntrada)
			return false;
		if (valorUnitario == null) {
			if (other.valorUnitario != null)
				return false;
		} else if (!valorUnitario.equals(other.valorUnitario))
			return false;
		return true;
	}
	
}
