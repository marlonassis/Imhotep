package br.com.ControleDispensacao.comparador;

import java.util.Comparator;

import br.com.ControleDispensacao.entidade.CuidadosPaciente;

public class CuidadosPacienteComparador implements Comparator<CuidadosPaciente>  {  
	@Override
	public int compare(CuidadosPaciente arg0, CuidadosPaciente arg1) {
		return arg0.getDescricao().compareTo(arg1.getDescricao());
	}
}  
