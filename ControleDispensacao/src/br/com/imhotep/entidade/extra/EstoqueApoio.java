package br.com.imhotep.entidade.extra;

import java.util.Date;

import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;


public class EstoqueApoio {
	private Estoque estoque;
	private EstoqueAlmoxarifado estoqueAlmoxarifado;
	private String lote;
	private String tipo;
	private Integer quantidadeAtual;
	private String validade;
	private Date dataValidade;
	
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public EstoqueAlmoxarifado getEstoqueAlmoxarifado() {
		return estoqueAlmoxarifado;
	}
	public void setEstoqueAlmoxarifado(EstoqueAlmoxarifado estoqueAlmoxarifado) {
		this.estoqueAlmoxarifado = estoqueAlmoxarifado;
	}
	public Date getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataValidade == null) ? 0 : dataValidade.hashCode());
		result = prime * result + ((estoque == null) ? 0 : estoque.hashCode());
		result = prime
				* result
				+ ((estoqueAlmoxarifado == null) ? 0 : estoqueAlmoxarifado
						.hashCode());
		result = prime * result + ((lote == null) ? 0 : lote.hashCode());
		result = prime * result
				+ ((quantidadeAtual == null) ? 0 : quantidadeAtual.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result
				+ ((validade == null) ? 0 : validade.hashCode());
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
		EstoqueApoio other = (EstoqueApoio) obj;
		if (dataValidade == null) {
			if (other.dataValidade != null)
				return false;
		} else if (!dataValidade.equals(other.dataValidade))
			return false;
		if (estoque == null) {
			if (other.estoque != null)
				return false;
		} else if (!estoque.equals(other.estoque))
			return false;
		if (estoqueAlmoxarifado == null) {
			if (other.estoqueAlmoxarifado != null)
				return false;
		} else if (!estoqueAlmoxarifado.equals(other.estoqueAlmoxarifado))
			return false;
		if (lote == null) {
			if (other.lote != null)
				return false;
		} else if (!lote.equals(other.lote))
			return false;
		if (quantidadeAtual == null) {
			if (other.quantidadeAtual != null)
				return false;
		} else if (!quantidadeAtual.equals(other.quantidadeAtual))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (validade == null) {
			if (other.validade != null)
				return false;
		} else if (!validade.equals(other.validade))
			return false;
		return true;
	}
	
}
