package br.com.imhotep.comparador;

import java.util.Comparator;

public class TextoStringComparador implements Comparator<String>  {  
	@Override
	public int compare(String arg0, String arg1) {
		return arg0.compareTo(arg1);
	}
}  
