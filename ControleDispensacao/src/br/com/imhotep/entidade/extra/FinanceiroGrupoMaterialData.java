package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FinanceiroGrupoMaterialData {
	private String data;
	private Date dataBruta;
	private double precoMedio;
	private int saldoInicial;
	private int saldoFinal;
	private List<FinanceiroGrupoMaterialDataMovimento> entradas = new ArrayList<FinanceiroGrupoMaterialDataMovimento>();
	private List<FinanceiroGrupoMaterialDataMovimento> saidas = new ArrayList<FinanceiroGrupoMaterialDataMovimento>();
	
	public FinanceiroGrupoMaterialData(){
		super();
	}
	
	public FinanceiroGrupoMaterialData(String data, double precoMedio, Date dataBruta, int saldoInicial, int saldoFinal){
		super();
		this.data = data;
		this.precoMedio = precoMedio;
		this.dataBruta = dataBruta;
		this.saldoInicial = saldoInicial;
		this.saldoFinal = saldoFinal;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public List<FinanceiroGrupoMaterialDataMovimento> getEntradas() {
		return entradas;
	}
	public void setEntradas(List<FinanceiroGrupoMaterialDataMovimento> entradas) {
		this.entradas = entradas;
	}
	public List<FinanceiroGrupoMaterialDataMovimento> getSaidas() {
		return saidas;
	}
	public void setSaidas(List<FinanceiroGrupoMaterialDataMovimento> saidas) {
		this.saidas = saidas;
	}
	public double getPrecoMedio() {
		return precoMedio;
	}
	public void setPrecoMedio(double precoMedio) {
		this.precoMedio = precoMedio;
	}
	public Date getDataBruta() {
		return dataBruta;
	}
	public void setDataBruta(Date dataBruta) {
		this.dataBruta = dataBruta;
	}

	public int getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(int saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public int getSaldoFinal() {
		return saldoFinal;
	}

	public void setSaldoFinal(int saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
}
