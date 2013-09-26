package br.com.imhotep.comparador;

import java.util.Comparator;
import java.util.Date;

import br.com.imhotep.entidade.EstoqueAlmoxarifado;

public class EstoqueAlmoxarifadoDataVencimentoComparador implements Comparator<EstoqueAlmoxarifado>  {  
	@Override
	public int compare(EstoqueAlmoxarifado arg0, EstoqueAlmoxarifado arg1) {
		Date data1 = (Date) arg0.getDataValidade();
		Date data2 = (Date) arg1.getDataValidade();
		
		return data1.compareTo(data2);
	}
}  
