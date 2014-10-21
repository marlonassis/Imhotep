package br.com.imhotep.entidade.extra;

public class ItemEstoqueABC {
	private String material;
	private Integer consumo;
	private String classificacao;
	private Double valorUnitario;
	private Double acumulado;
	private Double porcentagemAcumulado;
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	
	public Integer getConsumo() {
		return consumo;
	}
	public void setConsumo(Integer consumo) {
		this.consumo = consumo;
	}
	
	public Double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public Double getAcumulado() {
		return acumulado;
	}
	public void setAcumulado(Double acumulado) {
		this.acumulado = acumulado;
	}
	
	public Double getPorcentagemAcumulado() {
		return porcentagemAcumulado;
	}
	public void setPorcentagemAcumulado(Double porcentagemAcumulado) {
		this.porcentagemAcumulado = porcentagemAcumulado;
	}
	
	public String getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
	
	public Double getValorTotal(){
		return getConsumo() * getValorUnitario();
	}
}
