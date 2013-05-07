package br.com.imhotep.auxiliar;


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
	
	private static Locale LOCALE_BRASIL = new Locale ("pt", "BR");

	public static String doubleFormatadoDuasCasasDecimais(Double valor){
		NumberFormat df = NumberFormat.getNumberInstance(LOCALE_BRASIL);
		df.setMaximumFractionDigits(2);
		return df.format(valor);   
	}
	
	public static Object[] addElemento(Object[] array, Object elemento) {
		if(array==null){
			Object[] array2 = {elemento};
			array = array2;
			return array;
		}
		Object[] result = Arrays.copyOf(array, array==null ? 1 : array.length+1);
	    result[array.length] = elemento;
	    return result;
	}
	
	public static String doubleFormatadoBr(Double valor){
		if(valor != null){
			NumberFormat nf = NumberFormat.getInstance(LOCALE_BRASIL);
			nf.setMinimumFractionDigits(2);
			return nf.format(valor);
		}
		return null;
	}
	
	public String formatarDataHoraSegundoBrasil(Date date){
		if(date != null)
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
		return null;
	}
	
	public String formatarDataHoraBrasil(Date date){
		if(date != null)
			return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
		return null;
	}
	
	public static Date ajustarZeroHoraDia(Date dataFim) {
		Calendar df = Calendar.getInstance();
		df.setTime(dataFim);
		df.set(Calendar.HOUR, 0);
		df.set(Calendar.MINUTE, 0);
		df.set(Calendar.SECOND, 0);
		return df.getTime();
	}
	
	public Date ajustarUltimaHoraDia(Date dataFim) {
		Calendar df = Calendar.getInstance();
		df.setTime(dataFim);
		df.set(Calendar.HOUR, 23);
		df.set(Calendar.MINUTE, 59);
		df.set(Calendar.SECOND, 59);
		return df.getTime();
	}
	
	public Date ajustarUltimoDiaMes(Date dataFim) {
		Calendar df = Calendar.getInstance();
		df.setTime(dataFim);
		df.set(Calendar.DAY_OF_MONTH, df.getActualMaximum(Calendar.DAY_OF_MONTH));
		return df.getTime();
	}
	
	public Date ajustarUltimoDiaMesHoraMaximo(Date dataFim) {
		dataFim = ajustarUltimoDiaMes(dataFim);
		dataFim = ajustarUltimaHoraDia(dataFim);
		return dataFim;
	}
	
	public static boolean isNumero(String texto) {  
	    Pattern pat = Pattern.compile("[0-9]+");        
	    Matcher mat = pat.matcher(texto);  
	    return mat.matches();  
	} 
	
}
