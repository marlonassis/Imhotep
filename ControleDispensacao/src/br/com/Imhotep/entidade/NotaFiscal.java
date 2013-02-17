package br.com.Imhotep.entidade;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.Id;
import br.com.Imhotep.entidade.Profissional;
import javax.persistence.SequenceGenerator;
import br.com.Imhotep.entidade.Fornecedor;
import br.com.Imhotep.entidade.Estoque;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "tb_nota_fiscal")
public class NotaFiscal {
	private int idNotaFiscal;
	private Estoque estoque;
	private Fornecedor fornecedor;
	private Integer quantidade;
	private Double valorUnitario;
	private Boolean fechada;
	private Boolean bloqueada;
	private Profissional profissionalInsercao;

	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_nota_fiscal_id_nota_fiscal_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_nota_fiscal", unique = true, nullable = false)	
	public int getIdNotaFiscal() {
		return idNotaFiscal;
	}

	public void setIdNotaFiscal(int idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estoque")	
	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_fornecedor")	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Column(name = "in_quantidade")	
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Column(name = "db_valor_unitario")	
	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@Column(name="bl_fechada", nullable=false) 	
	public Boolean getFechada() {
		return fechada;
	}

	public void setFechada(Boolean fechada) {
		this.fechada = fechada;
	}

	@Column(name="bl_bloqueada", nullable=false) 	
	public Boolean getBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(Boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_insercao")	
	public Profissional getProfissionalInsercao() {
		return profissionalInsercao;
	}

	public void setProfissionalInsercao(Profissional profissionalInsercao) {
		this.profissionalInsercao = profissionalInsercao;
	}


	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof NotaFiscal))
			return false;
		
		return ((NotaFiscal)obj).getIdNotaFiscal() == this.idNotaFiscal;
	}
}
