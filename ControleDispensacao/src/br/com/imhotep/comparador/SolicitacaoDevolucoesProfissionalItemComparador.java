package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.extra.SolicitacoesDevolucoesProfissionalItem;

public class SolicitacaoDevolucoesProfissionalItemComparador implements Comparator<SolicitacoesDevolucoesProfissionalItem>  {  
	@Override
	public int compare(SolicitacoesDevolucoesProfissionalItem arg0, SolicitacoesDevolucoesProfissionalItem arg1) {
		return arg0.getDescricao().compareTo(arg1.getDescricao());
	}
}  
