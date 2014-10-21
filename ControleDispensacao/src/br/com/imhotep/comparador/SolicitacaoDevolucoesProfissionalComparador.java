package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.extra.SolicitacoesDevolucoesProfissional;

public class SolicitacaoDevolucoesProfissionalComparador implements Comparator<SolicitacoesDevolucoesProfissional>  {  
	@Override
	public int compare(SolicitacoesDevolucoesProfissional arg0, SolicitacoesDevolucoesProfissional arg1) {
		return arg1.getData().compareTo(arg0.getData());
	}
}  
