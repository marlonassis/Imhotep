package br.com.imhotep.entidade.extra;

public class EstoqueInventario {
	private String material;
	private String lote;
	private String cadastrado;
	private Integer quantidadeContada;
	private Integer quantidadeAtual;
	private String validade;
	
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	
	public String getCadastrado() {
		return cadastrado;
	}
	public void setCadastrado(String cadastrado) {
		this.cadastrado = cadastrado;
	}
	
	public Integer getQuantidadeContada() {
		return quantidadeContada;
	}
	public void setQuantidadeContada(Integer quantidadeContada) {
		this.quantidadeContada = quantidadeContada;
	}
	public Integer getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(Integer quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	public String getValidade() {
		return validade;
	}
	public void setValidade(String validade) {
		this.validade = validade;
	}
}
