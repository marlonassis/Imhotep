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
import javax.persistence.Transient;


@Entity
@Table(name = "tb_nota_fiscal_estoque_almoxarifado")
public class NotaFiscalEstoqueAlmoxarifado {
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
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof NotaFiscalEstoqueAlmoxarifado))
			return false;
		
		return ((NotaFiscalEstoqueAlmoxarifado)obj).getIdNotaFiscalEstoqueAlmoxarifado() == this.idNotaFiscalEstoqueAlmoxarifado;
	}
}
