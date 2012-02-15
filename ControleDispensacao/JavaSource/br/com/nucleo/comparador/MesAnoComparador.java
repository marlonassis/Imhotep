package br.com.nucleo.comparador;

import java.util.Comparator;

import br.com.nucleo.utilidades.Utilities;

public class MesAnoComparador implements Comparator<Object>  {  
	public int compare(Object o1, Object o2) {  
		String[] mesAno1 = o1.toString().split("/");
		String[] mesAno2 = o2.toString().split("/");
		           
		int mes1 = Utilities.posMes(mesAno1[0]);
		int ano1 = Integer.parseInt(mesAno1[1]);
		
		int mes2 = Utilities.posMes(mesAno2[0]);
		int ano2 = Integer.parseInt(mesAno2[1]);
		
		int compare = ano1 - ano2; //Começamos pelo ano
		
		if (compare != 0)
			return compare;
		
		return mes1 - mes2; //E se o ano for igual, comparamos o mês
	}  
}  
