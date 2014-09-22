package br.com.imhotep.entidade.relatorio;

import java.text.DecimalFormat;


public class FinanceiroGrupoAlmoxarifado {
	private DecimalFormat df = new DecimalFormat( "0.00" ); 
	private String grupo;
	private Double saldoTransportado;
	private Double totalEntrada;
	private Double totalSaida;
	private Double saldoFinal;
	
	public FinanceiroGrupoAlmoxarifado(){
		super();
	}
	
	public FinanceiroGrupoAlmoxarifado(String grupo, Double saldoTransportado, Double totalEntrada, Double totalSaida, Double saldoFinal){
		super();
		this.grupo = grupo;
		this.saldoTransportado = Double.valueOf(df.format(saldoTransportado));
		this.totalEntrada = Double.valueOf(df.format(totalEntrada));
		this.totalSaida = Double.valueOf(df.format(totalSaida));
		this.saldoFinal = Double.valueOf(df.format(saldoFinal));
	}
	
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public Double getSaldoTransportado() {
		return saldoTransportado;
	}
	public void setSaldoTransportado(Double saldoTransportado) {
		this.saldoTransportado = saldoTransportado;
	}
	public Double getTotalEntrada() {
		return totalEntrada;
	}
	public void setTotalEntrada(Double totalEntrada) {
		this.totalEntrada = totalEntrada;
	}
	public Double getTotalSaida() {
		return totalSaida;
	}
	public void setTotalSaida(Double totalSaida) {
		this.totalSaida = totalSaida;
	}
	public Double getSaldoFinal() {
		return saldoFinal;
	}
	public void setSaldoFinal(Double saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

}
