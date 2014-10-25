package br.com.imhotep.entidade.extra;



public class MedLynxTransportadoAlmoxarifado {
	private int codigoMaterial;
	private String descricao;
	private int saldoTransportado;
	private int saldoImhotep;
	private double precoMedioTransportado;
	private double precoMedioImhotep;
	
	public MedLynxTransportadoAlmoxarifado(int codigoMaterial, String descricao,
			int saldoTransportado, int saldoImhotep,
			double precoMedioTransportado, double precoMedioImhotep) {
		super();
		this.codigoMaterial = codigoMaterial;
		this.descricao = descricao;
		this.saldoTransportado = saldoTransportado;
		this.saldoImhotep = saldoImhotep;
		this.precoMedioTransportado = precoMedioTransportado;
		this.precoMedioImhotep = precoMedioImhotep;
	}
	
	public int getCodigoMaterial() {
		return codigoMaterial;
	}
	public void setCodigoMaterial(int codigoMaterial) {
		this.codigoMaterial = codigoMaterial;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getSaldoTransportado() {
		return saldoTransportado;
	}
	public void setSaldoTransportado(int saldoTransportado) {
		this.saldoTransportado = saldoTransportado;
	}
	public int getSaldoImhotep() {
		return saldoImhotep;
	}
	public void setSaldoImhotep(int saldoImhotep) {
		this.saldoImhotep = saldoImhotep;
	}
	public double getPrecoMedioTransportado() {
		return precoMedioTransportado;
	}
	public void setPrecoMedioTransportado(double precoMedioTransportado) {
		this.precoMedioTransportado = precoMedioTransportado;
	}
	public double getPrecoMedioImhotep() {
		return precoMedioImhotep;
	}
	public void setPrecoMedioImhotep(double precoMedioImhotep) {
		this.precoMedioImhotep = precoMedioImhotep;
	}
	
	public String getMensagem(){
		if(getPrecoMedioImhotep() == getPrecoMedioTransportado() && getSaldoImhotep() == getSaldoTransportado()){
			return "OK";
		}else{
			return "Conferir";
		}
	}
	
	public int getSaldoFinal(){
		if(getSaldoTransportado() == 0){
			return getSaldoImhotep();
		}
		return getSaldoTransportado();
	}
	
	public double getPrecoMedioFinal(){
		if(getPrecoMedioTransportado() == 0d){
			return getPrecoMedioImhotep();
		}
		return getPrecoMedioTransportado();
	}
	
}
