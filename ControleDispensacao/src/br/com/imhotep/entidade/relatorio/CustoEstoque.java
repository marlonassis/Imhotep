package br.com.imhotep.entidade.relatorio;

import br.com.imhotep.entidade.Material;

public class CustoEstoque {

	private Material material;
	private String lote;
	private Double valorUnitario;
	private Integer quantidadeEntrada;
	private Double total;
	
	
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	
	public Double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public Integer getQuantidadeEntrada() {
		return quantidadeEntrada;
	}
	public void setQuantidadeEntrada(Integer quantidadeEntrada) {
		this.quantidadeEntrada = quantidadeEntrada;
	}
	
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}

}
