package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.extra.EstoqueDispensacao;

public class EstoqueDispensacaoComparador implements Comparator<EstoqueDispensacao>  {  
	@Override
	public int compare(EstoqueDispensacao arg0, EstoqueDispensacao arg1) {
		return arg0.getEstoque().getLote().compareTo(arg1.getEstoque().getLote());
	}
}  
