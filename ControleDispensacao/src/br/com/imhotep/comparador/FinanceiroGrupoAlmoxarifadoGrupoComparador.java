package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.relatorio.FinanceiroGrupoAlmoxarifadoGrupo;

public class FinanceiroGrupoAlmoxarifadoGrupoComparador implements Comparator<FinanceiroGrupoAlmoxarifadoGrupo>  {  
	@Override
	public int compare(FinanceiroGrupoAlmoxarifadoGrupo arg0, FinanceiroGrupoAlmoxarifadoGrupo arg1) {
		return arg0.getGrupo().toLowerCase().compareTo(arg1.getGrupo().toLowerCase());
	}
}  
