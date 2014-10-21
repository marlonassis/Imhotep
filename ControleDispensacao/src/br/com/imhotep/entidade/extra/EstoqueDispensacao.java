package br.com.imhotep.entidade.extra;

import br.com.imhotep.entidade.Estoque;

public class EstoqueDispensacao {
	private Estoque estoque;
	private Integer quantidadeDispensada;
	
	public EstoqueDispensacao(){
		super();
	}
	
	public EstoqueDispensacao(Estoque estoque, Integer quantidadeDispensada){
		this.estoque = estoque;
		this.quantidadeDispensada = quantidadeDispensada;
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

}
