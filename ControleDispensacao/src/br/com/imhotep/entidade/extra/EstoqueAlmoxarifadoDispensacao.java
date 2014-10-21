package br.com.imhotep.entidade.extra;

import br.com.imhotep.entidade.EstoqueAlmoxarifado;

public class EstoqueAlmoxarifadoDispensacao {
	private EstoqueAlmoxarifado estoqueAlmoxarifado;
	private Integer quantidadeDispensada;
	
	public EstoqueAlmoxarifadoDispensacao(){
	}
	
	public EstoqueAlmoxarifadoDispensacao(EstoqueAlmoxarifado estoqueAlmoxarifado, Integer quantidadeDispensada){
		this.estoqueAlmoxarifado = estoqueAlmoxarifado;
		this.quantidadeDispensada = quantidadeDispensada;
	}
	
	public EstoqueAlmoxarifado getEstoqueAlmoxarifado() {
		return estoqueAlmoxarifado;
	}
	public void setEstoqueAlmoxarifado(EstoqueAlmoxarifado estoqueAlmoxarifado) {
		this.estoqueAlmoxarifado = estoqueAlmoxarifado;
	}
	public Integer getQuantidadeDispensada() {
		return quantidadeDispensada;
	}
	public void setQuantidadeDispensada(Integer quantidadeDispensada) {
		this.quantidadeDispensada = quantidadeDispensada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((estoqueAlmoxarifado == null) ? 0 : estoqueAlmoxarifado
						.hashCode());
		result = prime
				* result
				+ ((quantidadeDispensada == null) ? 0 : quantidadeDispensada
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
		EstoqueAlmoxarifadoDispensacao other = (EstoqueAlmoxarifadoDispensacao) obj;
		if (estoqueAlmoxarifado == null) {
			if (other.estoqueAlmoxarifado != null)
				return false;
		} else if (!estoqueAlmoxarifado.equals(other.estoqueAlmoxarifado))
			return false;
		if (quantidadeDispensada == null) {
			if (other.quantidadeDispensada != null)
				return false;
		} else if (!quantidadeDispensada.equals(other.quantidadeDispensada))
			return false;
		return true;
	}

	
	
}
