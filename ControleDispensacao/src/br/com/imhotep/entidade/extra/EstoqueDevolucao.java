package br.com.imhotep.entidade.extra;

import br.com.imhotep.entidade.Estoque;

public class EstoqueDevolucao {
	private Estoque estoque;
	private Integer quantidadeDevolvida;
	private String lote;
	
	public EstoqueDevolucao(){
		super();
	}
	
	public EstoqueDevolucao(Estoque estoque, Integer quantidadeDevolvida, String lote){
		super();
		this.estoque = estoque;
		this.quantidadeDevolvida = quantidadeDevolvida;
		this.lote = lote;
	}
	
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public Integer getQuantidadeDevolvida() {
		return quantidadeDevolvida;
	}
	public void setQuantidadeDevolvida(Integer quantidadeDevolvida) {
		this.quantidadeDevolvida = quantidadeDevolvida;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	
}
