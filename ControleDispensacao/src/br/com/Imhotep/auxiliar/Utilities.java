package br.com.Imhotep.auxiliar;


import java.util.Calendar;
import java.util.Date;
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

	public static Calendar ajustarUltimaHoraDia(Date dataFim) {
		Calendar df = Calendar.getInstance();
		df.setTime(dataFim);
		df.set(Calendar.HOUR, 23);
		df.set(Calendar.MINUTE, 59);
		df.set(Calendar.SECOND, 59);
		return df;
	}
	
	public static boolean isNumero(String texto) {  
	    Pattern pat = Pattern.compile("[0-9]+");        
	    Matcher mat = pat.matcher(texto);  
	    return mat.matches();  
	} 
	
}
