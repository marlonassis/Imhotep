package br.com.imhotep.auxiliar;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Id;

import br.com.imhotep.entidade.PacienteEntradaResponsavel;


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
	
	public static InputStream getImagemLogoHU(){
		byte[] logoTipoHU = Parametro.logoTipoHU();
		InputStream logoTipoHUInput = new ByteArrayInputStream(logoTipoHU);
		return logoTipoHUInput;
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
	
	public static String utf8_to_latin1(String str){
		if(str != null){
	       try{
	          String stringToConvert = str;
	          byte[] convertStringToByte = stringToConvert.getBytes("UTF-8");
	          return new String(convertStringToByte, "ISO-8859-1");
	       }catch(Exception e){
	    	   System.out.println(e.getStackTrace());
	       }
		}
		return null;
	}
	
	public static Double StringParaDoubleDiv100(String valor){
		if(valor != null){
			return StringParaDouble(valor) /100.00d;
		}
		return null;
	}
	
	public static Double StringParaDouble(String valor){
		if(valor != null){
			NumberFormat nf = NumberFormat.getInstance(LOCALE_BRASIL);
			nf.setMinimumFractionDigits(2);
			try {
				return (nf.parse(valor).doubleValue());
			} catch (ParseException e) {
				e.printStackTrace();
			}
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
	
	public static String getNomePropriedadeId(Object obj){
		String nomeMetodo = getNomeMetodoId(obj);
		String nomePropriedade = nomeMetodo.replace("get", "");
		char[] nome = nomePropriedade.toCharArray();
		nome[0] = String.valueOf(nome[0]).toLowerCase().toCharArray()[0];
		return String.copyValueOf(nome);
	}
	
	public static String getNomeMetodoId(Object obj){
		Class<?> clazz;
		try {
			clazz = Class.forName(obj.getClass().getName());
			for(Method method : clazz.getMethods()){
				if(method.isAnnotationPresent(Id.class)){
					return method.getName();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * Esse método retornará o valor formatado de acordo com a máscara informada. Cada caracter a 
	 * ser substituído na máscara deve ser simbolizado por '#'.
	 * @param valor
	 * @param mascara
	 * @return String com o valor formatado de acordo com a máscara
	 */
	public static String formatarValorMascara(String valor, String mascara){
		if(valor != null){
			for(char c : valor.toCharArray()){
				mascara = mascara.replaceFirst("#", String.valueOf(c));
			}
			return mascara;
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		
		Class<?> clazz;
		try {
			clazz = Class.forName(PacienteEntradaResponsavel.class.getName());
			for(Field field : clazz.getDeclaredFields()){
				System.out.println("<field name=\""+field.getName()+"\" class=\""+field.getType().getName()+"\"/>");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static Object getValorPropriedadeId(Object obj){
		Class<?> clazz;
		try {
			clazz = Class.forName(obj.getClass().getName());
			String metodo = getNomeMetodoId(obj);
			if(metodo != null){
	            Method meth = clazz.getMethod(metodo, null);  
	        	Object valor = meth.invoke(obj, null);
				return valor;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String nomeSimplesClasse(Class<?> class1) {
		String[] split = class1.getName().split("\\.");
		String classe = split[split.length-1];
		return classe;
	}
	
}
