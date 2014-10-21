package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.LaboratorioSolicitacaoItemLog;

public class LogSolicitacaoExameItemComparador implements Comparator<LaboratorioSolicitacaoItemLog>  {  
	@Override
	public int compare(LaboratorioSolicitacaoItemLog arg0, LaboratorioSolicitacaoItemLog arg1) {
		return arg0.getDataLog().compareTo(arg1.getDataLog());
	}
}  
