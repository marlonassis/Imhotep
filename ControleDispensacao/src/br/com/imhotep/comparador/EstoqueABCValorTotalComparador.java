package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.extra.ItemEstoqueABC;

public class EstoqueABCValorTotalComparador implements Comparator<ItemEstoqueABC>  {  
	@Override
	public int compare(ItemEstoqueABC arg0, ItemEstoqueABC arg1) {
		return arg1.getValorTotal().compareTo(arg0.getValorTotal());
	}
}  
