package br.com.imhotep.entidade.extra;

public class MaterialFaltaEstoque {
	private Integer idMaterial;
	private Integer codigoMaterial;
	private String material;
	private Integer quantidadeMinima;
	private Long quantidadeAtual;
	
	public MaterialFaltaEstoque() {
		
	}
	
	public MaterialFaltaEstoque(Integer idMaterial, Integer codigoMaterial, String material, Integer quantidadeMinima, Long quantidadeAtual) {
		this.idMaterial = idMaterial;
		this.codigoMaterial = codigoMaterial;
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
	public Long getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(Long quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}

	public Integer getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(Integer idMaterial) {
		this.idMaterial = idMaterial;
	}

	public Integer getCodigoMaterial() {
		return codigoMaterial;
	}

	public void setCodigoMaterial(Integer codigoMaterial) {
		this.codigoMaterial = codigoMaterial;
	}
	
}
