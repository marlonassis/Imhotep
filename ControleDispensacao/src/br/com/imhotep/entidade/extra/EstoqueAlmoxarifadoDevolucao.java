package br.com.imhotep.entidade.extra;

import br.com.imhotep.entidade.EstoqueAlmoxarifado;

public class EstoqueAlmoxarifadoDevolucao {
	private EstoqueAlmoxarifado estoqueAlmoxarifado;
	private Integer quantidadeDevolvida;
	private String lote;
	
	public EstoqueAlmoxarifadoDevolucao(){
		super();
	}
	
	public EstoqueAlmoxarifadoDevolucao(EstoqueAlmoxarifado estoqueAlmoxarifado, Integer quantidadeDevolvida, String lote){
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
