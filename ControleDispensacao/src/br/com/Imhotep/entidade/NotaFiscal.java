package br.com.Imhotep.entidade;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.Id;
import br.com.Imhotep.entidade.Profissional;
import javax.persistence.SequenceGenerator;
import br.com.Imhotep.entidade.Fornecedor;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "tb_nota_fiscal")
public class NotaFiscal {
	private int idNotaFiscal;
	private boolean fechada;
	private boolean bloqueada;
	private Profissional profissionalInsercao;
	private Fornecedor fornecedor;
	private String identificacaoNotaFiscal;
	private Date dataEntrega;
	private Date dataEmissao;
	private Double valorTotal;
	private Date dataInsercao;

	
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_fornecedor")	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Column(name = "cv_identificacao_nota_fiscal")	
	public String getIdentificacaoNotaFiscal() {
		return identificacaoNotaFiscal;
	}

	public void setIdentificacaoNotaFiscal(String identificacaoNotaFiscal) {
		this.identificacaoNotaFiscal = identificacaoNotaFiscal;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_entrega")	
	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_emissao")	
	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	@Column(name = "db_valor_total")	
	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_insercao")	
	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
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
