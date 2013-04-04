package br.com.Imhotep.auxiliar;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 * @author marlonassis
 *
 */
@ManagedBean(name="util")
@RequestScoped
public class Utilities extends br.com.remendo.utilidades.Utilities{

	public static Object[] addElemento(Object[] array, Object elemento) {
		Object[] result = Arrays.copyOf(array, array.length+1);
	    result[array.length] = elemento;
	    return result;
	}
	
	public static String doubleFormatadoBr(Double valor){
		Locale BRASIL = new Locale ("pt", "BR");
		NumberFormat nf = NumberFormat.getInstance(BRASIL);
		nf.setMinimumFractionDigits(2);
		return nf.format(valor);
	}
	
	public String formatarDataHoraBrasil(Date date){
		if(date != null)
			return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
		return null;
	}
	
	public static Date ajustarUltimaHoraDia(Date dataFim) {
		Calendar df = Calendar.getInstance();
		df.setTime(dataFim);
		df.set(Calendar.HOUR, 23);
		df.set(Calendar.MINUTE, 59);
		df.set(Calendar.SECOND, 59);
		return df.getTime();
	}
	
	public static Date ajustarDiaMes(Date dataFim) {
		Calendar df = Calendar.getInstance();
		df.setTime(dataFim);
		df.set(Calendar.DAY_OF_MONTH, df.getActualMaximum(Calendar.DAY_OF_MONTH));
		return df.getTime();
	}
	
	public static Date ajustarDiaMesHoraMaximo(Date dataFim) {
		dataFim = Utilities.ajustarDiaMes(dataFim);
		dataFim = Utilities.ajustarDiaMes(dataFim);
		return dataFim;
	}
	
	public static boolean isNumero(String texto) {  
	    Pattern pat = Pattern.compile("[0-9]+");        
	    Matcher mat = pat.matcher(texto);  
	    return mat.matches();  
	} 
	
}
