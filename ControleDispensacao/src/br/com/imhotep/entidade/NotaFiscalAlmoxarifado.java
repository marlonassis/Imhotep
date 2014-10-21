package br.com.imhotep.entidade;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.Fornecedor;
import br.com.imhotep.entidade.Profissional;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "tb_nota_fiscal_almoxarifado")
public class NotaFiscalAlmoxarifado implements Serializable {
	private static final long serialVersionUID = 6762795517462827188L;
	
	private int idNotaFiscalAlmoxarifado;
	private boolean fechada;
	private boolean bloqueada;
	private Profissional profissionalInsercao;
	private Fornecedor fornecedor;
	private String identificacao;
	private Date dataEntrega;
	private Date dataEmissao;
	private Double valorTotal;
	private Date dataInsercao;
	private Date dataContabil;
	private Double valorDesconto;
	private String serie;
	private List<NotaFiscalEstoqueAlmoxarifado> itens;
	private boolean doacao;
	private String chaveAcesso;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_nota_fiscal_almoxarifado_id_nota_fiscal_almoxarifado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_nota_fiscal_almoxarifado", unique = true, nullable = false)	
	public int getIdNotaFiscalAlmoxarifado() {
		return idNotaFiscalAlmoxarifado;
	}

	public void setIdNotaFiscalAlmoxarifado(int idNotaFiscalAlmoxarifado) {
		this.idNotaFiscalAlmoxarifado = idNotaFiscalAlmoxarifado;
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

	@Column(name = "cv_identificacao")	
	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_contabil")	
	public Date getDataContabil() {
		return dataContabil;
	}

	public void setDataContabil(Date dataContabil) {
		this.dataContabil = dataContabil;
	}
	
	@Column(name = "db_valor_desconto")	
	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	
	@Column(name="cv_serie")
	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "notaFiscalAlmoxarifado")
	public List<NotaFiscalEstoqueAlmoxarifado> getItens() {
		return itens;
	}
	public void setItens(List<NotaFiscalEstoqueAlmoxarifado> itens) {
		this.itens = itens;
	}
	
	@Column(name="bl_doacao") 	
	public Boolean getDoacao() {
		return doacao;
	}

	public void setDoacao(Boolean doacao) {
		this.doacao = doacao;
	}
	
	@Column(name = "cv_chave_acesso")	
	public String getChaveAcesso() {
		return chaveAcesso;
	}

	public void setChaveAcesso(String chaveAcesso) {
		this.chaveAcesso = chaveAcesso;
	}

	
	@Transient
	public Double getValorDescontado(){
		Double valorTotal2 = getValorTotal() == null ? 0d : getValorTotal();
		Double valorDesconto2 = getValorDesconto() == null ? 0d : getValorDesconto();
		return valorTotal2-valorDesconto2;
	}
	
	@Transient
	public String getValorDescontadoFormatado(){
		if(getValorTotal() != null){
			NumberFormat nf = NumberFormat.getCurrencyInstance(Constantes.LOCALE_BRASIL);
			return nf.format(getValorDescontado());
		}
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (bloqueada ? 1231 : 1237);
		result = prime * result
				+ ((chaveAcesso == null) ? 0 : chaveAcesso.hashCode());
		result = prime * result
				+ ((dataContabil == null) ? 0 : dataContabil.hashCode());
		result = prime * result
				+ ((dataEmissao == null) ? 0 : dataEmissao.hashCode());
		result = prime * result
				+ ((dataEntrega == null) ? 0 : dataEntrega.hashCode());
		result = prime * result
				+ ((dataInsercao == null) ? 0 : dataInsercao.hashCode());
		result = prime * result + (doacao ? 1231 : 1237);
		result = prime * result + (fechada ? 1231 : 1237);
		result = prime * result
				+ ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result + idNotaFiscalAlmoxarifado;
		result = prime * result
				+ ((identificacao == null) ? 0 : identificacao.hashCode());
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		result = prime
				* result
				+ ((profissionalInsercao == null) ? 0 : profissionalInsercao
						.hashCode());
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
		result = prime * result
				+ ((valorDesconto == null) ? 0 : valorDesconto.hashCode());
		result = prime * result
				+ ((valorTotal == null) ? 0 : valorTotal.hashCode());
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
		NotaFiscalAlmoxarifado other = (NotaFiscalAlmoxarifado) obj;
		if (bloqueada != other.bloqueada)
			return false;
		if (chaveAcesso == null) {
			if (other.chaveAcesso != null)
				return false;
		} else if (!chaveAcesso.equals(other.chaveAcesso))
			return false;
		if (dataContabil == null) {
			if (other.dataContabil != null)
				return false;
		} else if (!dataContabil.equals(other.dataContabil))
			return false;
		if (dataEmissao == null) {
			if (other.dataEmissao != null)
				return false;
		} else if (!dataEmissao.equals(other.dataEmissao))
			return false;
		if (dataEntrega == null) {
			if (other.dataEntrega != null)
				return false;
		} else if (!dataEntrega.equals(other.dataEntrega))
			return false;
		if (dataInsercao == null) {
			if (other.dataInsercao != null)
				return false;
		} else if (!dataInsercao.equals(other.dataInsercao))
			return false;
		if (doacao != other.doacao)
			return false;
		if (fechada != other.fechada)
			return false;
		if (fornecedor == null) {
			if (other.fornecedor != null)
				return false;
		} else if (!fornecedor.equals(other.fornecedor))
			return false;
		if (idNotaFiscalAlmoxarifado != other.idNotaFiscalAlmoxarifado)
			return false;
		if (identificacao == null) {
			if (other.identificacao != null)
				return false;
		} else if (!identificacao.equals(other.identificacao))
			return false;
		if (itens == null) {
			if (other.itens != null)
				return false;
		} else if (!itens.equals(other.itens))
			return false;
		if (profissionalInsercao == null) {
			if (other.profissionalInsercao != null)
				return false;
		} else if (!profissionalInsercao.equals(other.profissionalInsercao))
			return false;
		if (serie == null) {
			if (other.serie != null)
				return false;
		} else if (!serie.equals(other.serie))
			return false;
		if (valorDesconto == null) {
			if (other.valorDesconto != null)
				return false;
		} else if (!valorDesconto.equals(other.valorDesconto))
			return false;
		if (valorTotal == null) {
			if (other.valorTotal != null)
				return false;
		} else if (!valorTotal.equals(other.valorTotal))
			return false;
		return true;
	}
	
}
