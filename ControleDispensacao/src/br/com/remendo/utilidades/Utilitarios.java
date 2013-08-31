package br.com.remendo.utilidades;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.Id;
import javax.servlet.http.HttpSession;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.entidade.PacienteEntradaResponsavel;


/**
 * @author marlonassis
 *
 */
public class Utilitarios{
	
	private static Locale LOCALE_BRASIL = new Locale ("pt", "BR");

	public static MethodExpression contruirMethodExpression(String elExpression) {
		ExpressionFactory factory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
		MethodExpression methodExpression = factory.createMethodExpression(FacesContext.getCurrentInstance().getELContext(), elExpression, null, new Class[]{ActionEvent.class});
		return methodExpression;
	}
	
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
		if(valor != null && !valor.isEmpty()){
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
	
	public String dataHoraFormatada(Date data, String pattern){
		if(data !=  null)
			return new SimpleDateFormat(pattern).format(data);
		return null;		
	}
	
	public List converterSetList(Set sets){
		if(sets != null)
			return new ArrayList(sets);
		return null;
	}
	
	public static float getDifDia(java.util.Date dtMenor, java.util.Date dtMaior){  
        return (dtMaior.getTime() - dtMenor.getTime()) / (1000*60*60*24);
    }  
	
	public static float getDifAno(java.util.Date dtMenor, java.util.Date dtMaior){  
        return getDifDia(dtMenor, dtMaior) /365;
    }  
	
	public static String idadeAtual(Date nascimento){
		if(nascimento != null){
			float difAno = getDifAno(nascimento, Calendar.getInstance().getTime());
			int floor = (int) Math.floor(difAno);
			String idadeDes = floor + " ano(s)";
//			idadeDes = idadeDes.concat((int)Math.floor(12 * (difAno - floor)) + " meses");
			return idadeDes;
		}
		return "NI";
	}
	
	private static String primeiraLetraMinuscula(String palavra) {    
	      return palavra.substring(0,1).toLowerCase().concat(palavra.substring(1));    
	} 
	
	public static Object procuraInstancia(Class<?> classe) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
		if(session != null){
			Object attribute = criaInstancia(classe, session);
			return attribute;
		}else{
			return null;
		}
	}

	private static Object criaInstancia(Class<?> classe, HttpSession session) throws InstantiationException, IllegalAccessException {
		Object attribute = session.getAttribute(primeiraLetraMinuscula(classe.getSimpleName()));
		if (attribute == null){
			session.setAttribute(primeiraLetraMinuscula(classe.getSimpleName()), classe.newInstance());
			attribute = session.getAttribute(primeiraLetraMinuscula(classe.getSimpleName()));
		}
		return attribute;
	}
	
	public static void atualizaInstancia(Object classe) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
		if(session != null){
			criaInstancia(classe.getClass(), session);
			session.setAttribute(primeiraLetraMinuscula(classe.getClass().getSimpleName()), classe);
		}
	}

	
	public int converteString(String valor){
		return  valor.isEmpty() ? 0 : Integer.parseInt(valor);
	}
	
	public Date getDateAtual(){
		return Calendar.getInstance().getTime();
	}
	
	public String getDataHoraAtual(){
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
	}
	
	public String getDataAtual(){
		return new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
	}
	
	public String getHoraAtual(){
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	}
	
	/**
	 * Método que retorna o nome do Mês de acordo com o valor repassado por argumento
	 * @param int arg0
	 * @return nome do mês
	 */
	public static String mes(int arg0){
		String[] meses = DateFormatSymbols.getInstance().getMonths();
		return meses[arg0];
	}
	
	public static int qtdDiaMes(String mes, Integer ano){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, posMes(mes));
		c.set(Calendar.YEAR, ano);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Método que retorna a posição do Mês de acordo com o valor repassado por argumento
	 * @param int arg0
	 * @return número do mês
	 */
	public static Integer posMes(String arg0){
		String[] meses = DateFormatSymbols.getInstance().getMonths();
		int pos = 0;
		for(String mes : meses){
			if(arg0.equals(mes)){
				return pos;
			}
			pos++;
		}
		return null;
	}
	
	public static String encriptaParaMd5(String input) {  
	    String result = input;  
	    if(input != null) {  
	        MessageDigest md;
			try {
				md = MessageDigest.getInstance("MD5");
				md.update(input.getBytes());  
				BigInteger hash = new BigInteger(1, md.digest());  
				result = hash.toString(16);  
				while(result.length() < 32) {  
					result = "0" + result;  
				}  
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //or "SHA-1"  
	    }  
	    return result;  
	} 
}
