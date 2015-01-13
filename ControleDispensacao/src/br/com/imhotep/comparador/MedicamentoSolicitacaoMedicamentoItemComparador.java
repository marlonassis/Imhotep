package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;

public class MedicamentoSolicitacaoMedicamentoItemComparador implements Comparator<SolicitacaoMedicamentoUnidadeItem>  {  
	@Override
	public int compare(SolicitacaoMedicamentoUnidadeItem arg0, SolicitacaoMedicamentoUnidadeItem arg1) {
		if(arg0.getMaterial() != null && arg1.getMaterial() != null){
			return (arg0.getMaterial().getDescricao()).compareTo(arg1.getMaterial().getDescricao());
		}
		return 0;
	}
}  
