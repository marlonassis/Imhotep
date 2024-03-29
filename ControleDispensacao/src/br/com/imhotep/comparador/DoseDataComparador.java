package br.com.imhotep.comparador;

import java.util.Comparator;
import java.util.Date;

import br.com.imhotep.entidade.PrescricaoItemDose;

public class DoseDataComparador implements Comparator<PrescricaoItemDose>  {  
	@Override
	public int compare(PrescricaoItemDose arg0, PrescricaoItemDose arg1) {
		Date data1 = (Date) arg0.getDataDose();
		Date data2 = (Date) arg1.getDataDose();
		
		return data1.compareTo(data2);
	}
}  
