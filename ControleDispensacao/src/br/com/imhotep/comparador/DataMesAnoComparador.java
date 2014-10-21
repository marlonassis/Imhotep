package br.com.imhotep.comparador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DataMesAnoComparador implements Comparator<String>  {  
	@Override
	public int compare(String arg0, String arg1) {
		Date data1;
		try {
			data1 = new SimpleDateFormat("dd/MM/yyyy").parse("01/"+arg0);
			Date data2 = new SimpleDateFormat("dd/MM/yyyy").parse("01/"+arg1);
			return data1.compareTo(data2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
}  
