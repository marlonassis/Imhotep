package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.PainelAviso;

public class PainelAvisoDataInicioComparador implements Comparator<PainelAviso>  {  
	@Override
	public int compare(PainelAviso arg0, PainelAviso arg1) {
		return arg1.getDataInicio().compareTo(arg0.getDataInicio());
	}
}  
