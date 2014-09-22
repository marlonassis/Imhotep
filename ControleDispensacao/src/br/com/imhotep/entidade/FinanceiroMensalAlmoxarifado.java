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
@Table(name = "tb_financeiro_mensal_almoxarifado")
public class FinanceiroMensalAlmoxarifado implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idFinanceiroMensalAlmoxarifado;
	private MaterialAlmoxarifado materialAlmoxarifado;
	private Double precoMedio;
	private Double precoMedioTransportado;
	private Integer saldoTransportado;
	private Integer totalEntrada;
	private Integer totalSaida;
	private String mesReferencia;
	private Date dataInsercao;
	private Date dataAtualizacao;
	private GrupoAlmoxarifado grupoAlmoxarifado;
	private SubGrupoAlmoxarifado subGrupoAlmoxarifado;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_financeiro_mensal_almoxari_id_financeiro_mensal_almoxari_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_financeiro_mensal_almoxarifado", unique = true, nullable = false)
	public int getIdFinanceiroMensalAlmoxarifado() {
		return idFinanceiroMensalAlmoxarifado;
	}
	public void setIdFinanceiroMensalAlmoxarifado(int idFinanceiroMensalFarmacia) {
		this.idFinanceiroMensalAlmoxarifado = idFinanceiroMensalFarmacia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material_almoxarifado")
	public MaterialAlmoxarifado getMaterialAlmoxarifado() {
		return materialAlmoxarifado;
	}
	public void setMaterialAlmoxarifado(MaterialAlmoxarifado materialAlmoxarifado) {
		this.materialAlmoxarifado = materialAlmoxarifado;
	}
	
	@Column(name = "db_preco_medio")
	public Double getPrecoMedio() {
		return precoMedio;
	}
	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
	}
	
	@Column(name = "db_preco_medio_transportado")
	public Double getPrecoMedioTransportado() {
		return precoMedioTransportado;
	}
	public void setPrecoMedioTransportado(Double precoMedioTransportado) {
		this.precoMedioTransportado = precoMedioTransportado;
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
	
	@Column(name = "in_saldo_transportado")
	public int getSaldoTransportado() {
		return saldoTransportado;
	}
	public void setSaldoTransportado(int saldoTransportado) {
		this.saldoTransportado = saldoTransportado;
	}
	
	@Column(name = "in_total_entrada")
	public int getTotalEntrada() {
		return totalEntrada;
	}
	public void setTotalEntrada(int saldoFinal) {
		this.totalEntrada = saldoFinal;
	}
	
	@Column(name = "in_total_saida")
	public int getTotalSaida() {
		return totalSaida;
	}
	public void setTotalSaida(int totalSaida) {
		this.totalSaida = totalSaida;
	}
	
	@Column(name = "cv_mes_referencia")
	public String getMesReferencia() {
		return mesReferencia;
	}
	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_grupo_almoxarifado")
	public GrupoAlmoxarifado getGrupoAlmoxarifado() {
		return grupoAlmoxarifado;
	}
	public void setGrupoAlmoxarifado(GrupoAlmoxarifado grupoAlmoxarifado) {
		this.grupoAlmoxarifado = grupoAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sub_grupo_almoxarifado")
	public SubGrupoAlmoxarifado getSubGrupoAlmoxarifado() {
		return subGrupoAlmoxarifado;
	}
	public void setSubGrupoAlmoxarifado(SubGrupoAlmoxarifado subGrupoAlmoxarifado) {
		this.subGrupoAlmoxarifado = subGrupoAlmoxarifado;
	}
}
