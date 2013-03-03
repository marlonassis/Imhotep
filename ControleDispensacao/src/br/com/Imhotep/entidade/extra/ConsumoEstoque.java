package br.com.Imhotep.entidade.extra;

public class ConsumoEstoque {

	private String material;
	private Integer codigoMaterial;
	private Integer consumoTotal;
	private Integer consumoPeriodo;
	
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Integer getCodigoMaterial() {
		return codigoMaterial;
	}
	public void setCodigoMaterial(Integer codigoMaterial) {
		this.codigoMaterial = codigoMaterial;
	}
	public Integer getConsumoTotal() {
		return consumoTotal;
	}
	public void setConsumoTotal(Integer consumoTotal) {
		this.consumoTotal = consumoTotal;
	}
	public Integer getConsumoPeriodo() {
		return consumoPeriodo;
	}
	public void setConsumoPeriodo(Integer consumoPeriodo) {
		this.consumoPeriodo = consumoPeriodo;
	}

}
