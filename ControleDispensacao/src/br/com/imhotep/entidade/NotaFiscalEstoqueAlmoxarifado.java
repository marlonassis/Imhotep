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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "tb_nota_fiscal_estoque_almoxarifado")
public class NotaFiscalEstoqueAlmoxarifado implements Serializable {
	private static final long serialVersionUID = -1815921477926039620L;
	
	private int idNotaFiscalEstoqueAlmoxarifado;
	private EstoqueAlmoxarifado estoqueAlmoxarifado;
	private NotaFiscalAlmoxarifado notaFiscalAlmoxarifado;
	private Profissional profissionalInsercao;
	private Date dataInsercao;
	private Double valorUnitario;
	private int quantidadeEntrada;
	private MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado;

	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_nota_fiscal_estoque_almoxa_id_nota_fiscal_estoque_almoxa_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_nota_fiscal_estoque_almoxarifado", unique = true, nullable = false)	
	public int getIdNotaFiscalEstoqueAlmoxarifado() {
		return idNotaFiscalEstoqueAlmoxarifado;
	}
	
	public void setIdNotaFiscalEstoqueAlmoxarifado(int idNotaFiscalEstoqueAlmoxarifado) {
		this.idNotaFiscalEstoqueAlmoxarifado = idNotaFiscalEstoqueAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estoque_almoxarifado")	
	public EstoqueAlmoxarifado getEstoqueAlmoxarifado() {
		return estoqueAlmoxarifado;
	}

	public void setEstoqueAlmoxarifado(EstoqueAlmoxarifado estoqueAlmoxarifado) {
		this.estoqueAlmoxarifado = estoqueAlmoxarifado;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_nota_fiscal_almoxarifado")	
	public NotaFiscalAlmoxarifado getNotaFiscalAlmoxarifado() {
		return notaFiscalAlmoxarifado;
	}

	public void setNotaFiscalAlmoxarifado(NotaFiscalAlmoxarifado notaFiscalAlmoxarifado) {
		this.notaFiscalAlmoxarifado = notaFiscalAlmoxarifado;
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
	@JoinColumn(name = "id_movimento_livro_almoxarifado")	
	public MovimentoLivroAlmoxarifado getMovimentoLivroAlmoxarifado() {
		return movimentoLivroAlmoxarifado;
	}

	public void setMovimentoLivroAlmoxarifado(MovimentoLivroAlmoxarifado movimentoLivroAlmoxarifado) {
		this.movimentoLivroAlmoxarifado = movimentoLivroAlmoxarifado;
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
		result = prime * result + idNotaFiscalEstoqueAlmoxarifado;
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
		NotaFiscalEstoqueAlmoxarifado other = (NotaFiscalEstoqueAlmoxarifado) obj;
		if (dataInsercao == null) {
			if (other.dataInsercao != null)
				return false;
		} else if (!dataInsercao.equals(other.dataInsercao))
			return false;
		if (estoqueAlmoxarifado == null) {
			if (other.estoqueAlmoxarifado != null)
				return false;
		} else if (!estoqueAlmoxarifado.equals(other.estoqueAlmoxarifado))
			return false;
		if (idNotaFiscalEstoqueAlmoxarifado != other.idNotaFiscalEstoqueAlmoxarifado)
			return false;
		if (movimentoLivroAlmoxarifado == null) {
			if (other.movimentoLivroAlmoxarifado != null)
				return false;
		} else if (!movimentoLivroAlmoxarifado
				.equals(other.movimentoLivroAlmoxarifado))
			return false;
		if (notaFiscalAlmoxarifado == null) {
			if (other.notaFiscalAlmoxarifado != null)
				return false;
		} else if (!notaFiscalAlmoxarifado.equals(other.notaFiscalAlmoxarifado))
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
