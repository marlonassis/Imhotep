package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;

public class SolicitacaoMedicamentoUnidadeItemComparador implements Comparator<SolicitacaoMedicamentoUnidadeItem>  {  
	@Override
	public int compare(SolicitacaoMedicamentoUnidadeItem arg0, SolicitacaoMedicamentoUnidadeItem arg1) {
		return arg0.getMaterial().getDescricao().compareTo(arg1.getMaterial().getDescricao());
	}
}  
