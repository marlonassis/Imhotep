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

@Entity
@Table(name = "tb_financeiro_mensal_farmacia")
public class FinanceiroMensalFarmacia implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idFinanceiroMensalFarmacia;
	private Material material;
	private Double precoMedio;
	private Integer saldoInicial;
	private Integer saldoFinal;
	private String mesReferencia;
	private Date dataInsercao;
	private Date dataAtualizacao;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_financeiro_mensal_farmacia_id_financeiro_mensal_farmacia_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_financeiro_mensal_farmacia", unique = true, nullable = false)
	public int getIdFinanceiroMensalFarmacia() {
		return idFinanceiroMensalFarmacia;
	}
	public void setIdFinanceiroMensalFarmacia(int idFinanceiroMensalFarmacia) {
		this.idFinanceiroMensalFarmacia = idFinanceiroMensalFarmacia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@Column(name = "db_preco_medio")
	public Double getPrecoMedio() {
		return precoMedio;
	}
	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
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
	@Column(name = "dt_data_atualizacao")
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	@Column(name = "in_saldo_inicial")
	public int getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(int saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	
	@Column(name = "in_saldo_final")
	public int getSaldoFinal() {
		return saldoFinal;
	}
	public void setSaldoFinal(int saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	
	@Column(name = "cv_mes_referencia")
	public String getMesReferencia() {
		return mesReferencia;
	}
	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}
	
	
}
