package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.extra.FinanceiroGrupoMaterialData;

public class FinanceiroGrupoMaterialDataComparador implements Comparator<FinanceiroGrupoMaterialData>  {  
	@Override
	public int compare(FinanceiroGrupoMaterialData arg0, FinanceiroGrupoMaterialData arg1) {
		return arg0.getDataBruta().compareTo(arg1.getDataBruta());
	}
}  
