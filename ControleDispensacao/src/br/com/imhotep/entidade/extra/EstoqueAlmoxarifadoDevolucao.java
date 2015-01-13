package br.com.imhotep.entidade.extra;

import br.com.imhotep.entidade.EstoqueAlmoxarifado;

public class EstoqueAlmoxarifadoDevolucao {
	private EstoqueAlmoxarifado estoqueAlmoxarifado;
	private Double quantidadeDevolvida;
	private String lote;
	
	public EstoqueAlmoxarifadoDevolucao(){
		super();
	}
	
	public EstoqueAlmoxarifadoDevolucao(EstoqueAlmoxarifado estoqueAlmoxarifado, Double quantidadeDevolvida, String lote){
		super();
		this.estoqueAlmoxarifado = estoqueAlmoxarifado;
		this.quantidadeDevolvida = quantidadeDevolvida;
		this.lote = lote;
	}
	
	public EstoqueAlmoxarifado getEstoqueAlmoxarifado() {
		return estoqueAlmoxarifado;
	}
	public void setEstoqueAlmoxarifado(EstoqueAlmoxarifado estoqueAlmoxarifado) {
		this.estoqueAlmoxarifado = estoqueAlmoxarifado;
	}
	public Double getQuantidadeDevolvida() {
		return quantidadeDevolvida;
	}
	public void setQuantidadeDevolvida(Double quantidadeDevolvida) {
		this.quantidadeDevolvida = quantidadeDevolvida;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	
}
