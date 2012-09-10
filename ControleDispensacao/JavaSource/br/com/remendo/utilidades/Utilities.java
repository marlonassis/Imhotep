package br.com.remendo.utilidades;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


/**
 * @author marlonassis
 *
 */
@ManagedBean(name="util")
@RequestScoped
public class Utilities{
	
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
			session.setAttribute(classe.getSimpleName(), classe.newInstance());
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
