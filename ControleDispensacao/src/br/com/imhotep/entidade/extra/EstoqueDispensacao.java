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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estoque == null) ? 0 : estoque.hashCode());
		result = prime
				* result
				+ ((quantidadeDispensada == null) ? 0 : quantidadeDispensada
						.hashCode());
		result = prime
				* result
				+ ((quantidadeSolicitada == null) ? 0 : quantidadeSolicitada
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstoqueDispensacao other = (EstoqueDispensacao) obj;
		if (estoque == null) {
			if (other.estoque != null)
				return false;
		} else if (!estoque.equals(other.estoque))
			return false;
		if (quantidadeDispensada == null) {
			if (other.quantidadeDispensada != null)
				return false;
		} else if (!quantidadeDispensada.equals(other.quantidadeDispensada))
			return false;
		if (quantidadeSolicitada == null) {
			if (other.quantidadeSolicitada != null)
				return false;
		} else if (!quantidadeSolicitada.equals(other.quantidadeSolicitada))
			return false;
		return true;
	}
	
	
	
}
