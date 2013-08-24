package br.com.imhotep.entidade.extra;

import br.com.imhotep.entidade.DevolucaoMedicamentoItem;
import br.com.imhotep.entidade.Estoque;

public class EstoqueDevolucao {
	private Estoque estoque;
	private DevolucaoMedicamentoItem devolucaoMedicamentoItem = new DevolucaoMedicamentoItem();
	private Integer quantidadeDevolvida;
	
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public DevolucaoMedicamentoItem getDevolucaoMedicamentoItem() {
		return devolucaoMedicamentoItem;
	}
	public void setDevolucaoMedicamentoItem(DevolucaoMedicamentoItem devolucaoMedicamentoItem) {
		this.devolucaoMedicamentoItem = devolucaoMedicamentoItem;
	}
	public Integer getQuantidadeDevolvida() {
		return quantidadeDevolvida;
	}
	public void setQuantidadeDevolvida(Integer quantidadeDevolvida) {
		this.quantidadeDevolvida = quantidadeDevolvida;
	}
	
	public void somarQuantidadeDevolvida(Integer qtd){
		quantidadeDevolvida = quantidadeDevolvida == null ? 0 : quantidadeDevolvida;
		quantidadeDevolvida += qtd == null ? 0 : qtd;
	}
}
