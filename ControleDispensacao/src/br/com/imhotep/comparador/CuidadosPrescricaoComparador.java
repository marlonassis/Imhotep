package br.com.imhotep.comparador;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import br.com.imhotep.entidade.CuidadosPrescricao;

public class CuidadosPrescricaoComparador implements Comparator<CuidadosPrescricao>  {  
	@Override
	public int compare(CuidadosPrescricao arg0, CuidadosPrescricao arg1) {
		Collator collator = Collator.getInstance (new Locale("pt", "BR"));  
	    collator.setStrength(Collator.PRIMARY); 
		
		if(arg0.getCuidadosPaciente() != null && arg1.getCuidadosPaciente() != null)
			return collator.compare(arg0.getCuidadosPaciente().getDescricao(), arg1.getCuidadosPaciente().getDescricao());
		
		if(arg0.getDescricaoOutros() != null && arg1.getCuidadosPaciente() != null)
			return collator.compare(arg0.getDescricaoOutros(), arg1.getCuidadosPaciente().getDescricao());
		
		if(arg0.getCuidadosPaciente() != null && arg1.getDescricaoOutros() != null)
			return collator.compare(arg0.getCuidadosPaciente().getDescricao(), arg1.getDescricaoOutros());
		
		if(arg0.getDescricaoOutros() != null && arg1.getDescricaoOutros() != null)
			return collator.compare(arg0.getDescricaoOutros(), arg1.getDescricaoOutros());
		
		return 2;
	}
}  
