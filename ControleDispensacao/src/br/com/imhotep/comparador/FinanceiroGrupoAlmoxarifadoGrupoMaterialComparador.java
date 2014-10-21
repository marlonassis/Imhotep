package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.relatorio.FinanceiroGrupoAlmoxarifadoGrupoMaterial;

public class FinanceiroGrupoAlmoxarifadoGrupoMaterialComparador implements Comparator<FinanceiroGrupoAlmoxarifadoGrupoMaterial>  {  
	@Override
	public int compare(FinanceiroGrupoAlmoxarifadoGrupoMaterial arg0, FinanceiroGrupoAlmoxarifadoGrupoMaterial arg1) {
		if(arg0 != null && arg1 != null){
			String material = arg0.getMaterial().split(" - ")[1];
			String material2 = arg1.getMaterial().split(" - ")[1];
			return material.toLowerCase().compareTo(material2.toLowerCase());
		}
		return 0;
	}
}  
