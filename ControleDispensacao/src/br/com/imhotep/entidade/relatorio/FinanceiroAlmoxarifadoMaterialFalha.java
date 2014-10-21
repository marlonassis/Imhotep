package br.com.imhotep.entidade.relatorio;

import java.text.DecimalFormat;

public class FinanceiroAlmoxarifadoMaterialFalha {
	private DecimalFormat df = new DecimalFormat( "0.00" );
	private String grupo;
	private int idMaterial;
	private String material;
	private Double precoMedioTransportado;
	private Double precoMedioAtual;
	private Double precoMedio;
	private Integer saldoTransportado;
	private Integer saldoAtual;
	private Integer totalEntrada;
	private Integer totalSaida;
	private Integer saldoInicial;
	
	public FinanceiroAlmoxarifadoMaterialFalha(){
		super();
	}
	
	public FinanceiroAlmoxarifadoMaterialFalha(Integer saldoInicial, String grupo, int idMaterial, String material, Double precoMedioTransportado, Double precoMedioAtual, 
														Integer saldoTransportado, Integer saldoAtual, Integer totalEntrada, Integer totalSaida, Double precoMedio){
		super();
		this.material = material;
		this.precoMedioTransportado = Double.valueOf(df.format(precoMedioTransportado));
		this.precoMedioAtual = Double.valueOf(df.format(precoMedioAtual));
		this.saldoTransportado = saldoTransportado;
		this.totalEntrada = totalEntrada;
		this.totalSaida = totalSaida;
		this.saldoAtual = saldoAtual;
		this.precoMedio = Double.valueOf(df.format(precoMedio));
		this.saldoInicial = saldoInicial; 
		this.grupo = grupo;
		this.idMaterial = idMaterial;
	}

	public Double getPrecoMedioTransportado() {
		return precoMedioTransportado;
	}

	public void setPrecoMedioTransportado(Double precoMedioTransportado) {
		this.precoMedioTransportado = precoMedioTransportado;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Double getPrecoMedioAtual() {
		return precoMedioAtual;
	}

	public void setPrecoMedioAtual(Double precoMedioAtual) {
		this.precoMedioAtual = precoMedioAtual;
	}

	public Double getPrecoMedio() {
		return precoMedio;
	}
	
	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
	}

	public Double getTotal(){
		return getSaldoTransportado()*getPrecoMedioTransportado()+getTotalEntrada()*getPrecoMedio()-getTotalSaida()*getPrecoMedio();
	}
	
	public Integer getSaldoTransportado() {
		return saldoTransportado;
	}

	public void setSaldoTransportado(Integer saldoTransportado) {
		this.saldoTransportado = saldoTransportado;
	}

	public Integer getTotalEntrada() {
		return totalEntrada;
	}

	public void setTotalEntrada(Integer totalEntrada) {
		this.totalEntrada = totalEntrada;
	}

	public Integer getTotalSaida() {
		return totalSaida;
	}

	public void setTotalSaida(Integer totalSaida) {
		this.totalSaida = totalSaida;
	}

	public Integer getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(Integer saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public int getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(int idMaterial) {
		this.idMaterial = idMaterial;
	}

	public Integer getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Integer saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	
	
}
