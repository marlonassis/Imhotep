package br.com.imhotep.entidade.extra;

public class MaterialFaltaEstoque {
	private String material;
	private Integer quantidadeMinima;
	private Integer quantidadeAtual;
	
	public MaterialFaltaEstoque() {
		
	}
	
	public MaterialFaltaEstoque(String material, Integer quantidadeMinima, Integer quantidadeAtual) {
		this.material = material;
		this.quantidadeMinima = quantidadeMinima;
		this.quantidadeAtual = quantidadeAtual;
	}
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Integer getQuantidadeMinima() {
		return quantidadeMinima;
	}
	public void setQuantidadeMinima(Integer quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}
	public Integer getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(Integer quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	
}
