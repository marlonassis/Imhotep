package br.com.imhotep.entidade.extra;

import br.com.imhotep.entidade.Estoque;

public class EstoqueDispensacao {
	private Estoque estoque;
	private Integer quantidadeDispensada;
	private Integer quantidadeSolicitada;
	
	public EstoqueDispensacao(){
	}
	
	public EstoqueDispensacao(Estoque estoque, Integer quantidadeDispensada, Integer quantidadeSolicitada){
		this.estoque = estoque;
		this.quantidadeDispensada = quantidadeDispensada;
		this.quantidadeSolicitada = quantidadeSolicitada;
	}
	
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public Integer getQuantidadeDispensada() {
		return quantidadeDispensada;
	}
	public void setQuantidadeDispensada(Integer quantidadeDispensada) {
		this.quantidadeDispensada = quantidadeDispensada;
	}

	public Integer getQuantidadeSolicitada() {
		return quantidadeSolicitada;
	}

	public void setQuantidadeSolicitada(Integer quantidadeSolicitada) {
		this.quantidadeSolicitada = quantidadeSolicitada;
	}
}
