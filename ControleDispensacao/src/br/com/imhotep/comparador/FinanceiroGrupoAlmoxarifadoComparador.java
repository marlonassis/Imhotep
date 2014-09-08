package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.relatorio.FinanceiroGrupoAlmoxarifado;

public class FinanceiroGrupoAlmoxarifadoComparador implements Comparator<FinanceiroGrupoAlmoxarifado>  {  
	@Override
	public int compare(FinanceiroGrupoAlmoxarifado arg0, FinanceiroGrupoAlmoxarifado arg1) {
		return arg0.getGrupo().toLowerCase().compareTo(arg1.getGrupo().toLowerCase());
	}
}  
