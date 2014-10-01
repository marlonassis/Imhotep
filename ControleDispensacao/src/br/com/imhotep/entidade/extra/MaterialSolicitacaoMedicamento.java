package br.com.imhotep.entidade.extra;

import javax.persistence.Id;

import br.com.imhotep.entidade.Material;

public class MaterialSolicitacaoMedicamento {
	private Material material;
	private Integer quantidadeAtual;
	private Integer quantidadeSolicitada;
	private Boolean selecionado;
	
	@Id
	public int getIdMaterialSolicitacaoMedicamento(){
		if(getMaterial() != null)
			return getMaterial().getIdMaterial();
		return 0;
	}
	
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public Integer getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(Integer quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	public Integer getQuantidadeSolicitada() {
		if(quantidadeSolicitada != null && quantidadeSolicitada.intValue() == 0)
			return null;
		return quantidadeSolicitada;
	}
	public void setQuantidadeSolicitada(Integer quantidadeSolicitada) {
		this.quantidadeSolicitada = quantidadeSolicitada;
	}
	public Boolean getSelecionado() {
		return selecionado;
	}
	public void setSelecionado(Boolean selecionado) {
		this.selecionado = selecionado;
	}
	
	public boolean isComSaldo() {
		return quantidadeAtual.intValue() > 0;
	}
	
	public Boolean getSaldoInsuficiente(){
		if(getQuantidadeAtual() != null && getQuantidadeSolicitada() != null)
			return getQuantidadeAtual().intValue() < getQuantidadeSolicitada().intValue() & isComSaldo();
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((material == null) ? 0 : material.hashCode());
		result = prime * result
				+ ((quantidadeAtual == null) ? 0 : quantidadeAtual.hashCode());
		result = prime
				* result
				+ ((quantidadeSolicitada == null) ? 0 : quantidadeSolicitada
						.hashCode());
		result = prime * result
				+ ((selecionado == null) ? 0 : selecionado.hashCode());
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
		MaterialSolicitacaoMedicamento other = (MaterialSolicitacaoMedicamento) obj;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (quantidadeAtual == null) {
			if (other.quantidadeAtual != null)
				return false;
		} else if (!quantidadeAtual.equals(other.quantidadeAtual))
			return false;
		if (quantidadeSolicitada == null) {
			if (other.quantidadeSolicitada != null)
				return false;
		} else if (!quantidadeSolicitada.equals(other.quantidadeSolicitada))
			return false;
		if (selecionado == null) {
			if (other.selecionado != null)
				return false;
		} else if (!selecionado.equals(other.selecionado))
			return false;
		return true;
	}
	
}
