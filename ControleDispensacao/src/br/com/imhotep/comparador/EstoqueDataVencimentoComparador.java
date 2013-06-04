package br.com.imhotep.comparador;

import java.util.Comparator;
import java.util.Date;

import br.com.imhotep.entidade.Estoque;

public class EstoqueDataVencimentoComparador implements Comparator<Estoque>  {  
	@Override
	public int compare(Estoque arg0, Estoque arg1) {
		Date data1 = (Date) arg0.getDataValidade();
		Date data2 = (Date) arg1.getDataValidade();
		
		return data1.compareTo(data2);
	}
}  
