package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.List;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;

public class ItemDispensacao {
	private SolicitacaoMedicamentoUnidadeItem item = new SolicitacaoMedicamentoUnidadeItem();
	private List<EstoqueDispensacao> estoques = new ArrayList<EstoqueDispensacao>();
	
	public SolicitacaoMedicamentoUnidadeItem getItem() {
		return item;
	}
	public void setItem(SolicitacaoMedicamentoUnidadeItem item) {
		this.item = item;
	}
	
	public List<EstoqueDispensacao> getEstoques() {
		return estoques;
	}
	public void setEstoques(List<EstoqueDispensacao> estoques) {
		this.estoques = estoques;
	}
	
}
