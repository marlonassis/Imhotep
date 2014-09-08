package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.LaboratorioSolicitacaoLog;

public class LogSolicitacaoExameComparador implements Comparator<LaboratorioSolicitacaoLog>  {  
	@Override
	public int compare(LaboratorioSolicitacaoLog arg0, LaboratorioSolicitacaoLog arg1) {
		return arg0.getDataLog().compareTo(arg1.getDataLog());
	}
}  
