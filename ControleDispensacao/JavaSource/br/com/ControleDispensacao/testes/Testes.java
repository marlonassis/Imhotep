package br.com.ControleDispensacao.testes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Testes {

	
	public static void main(String args[]) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd-hh-MM-ss");
		sdf.format(new Date());
		
    }

	
	public static String md5(String input) throws NoSuchAlgorithmException {  
	    String result = input;  
	    if(input != null) {  
	        MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"  
	        md.update(input.getBytes());  
	        BigInteger hash = new BigInteger(1, md.digest());  
	        result = hash.toString(16);  
	        while(result.length() < 32) {  
	            result = "0" + result;  
	        }  
	    }  
	    return result;  
	} 
	
	
	 @SuppressWarnings("unused")
	private static char[] hexCodes(byte[] text) {  
		 char[] hexOutput = new char[text.length * 2];  
		 String hexString;  
		 for (int i = 0; i < text.length; i++) {  
            hexString = "00" + Integer.toHexString(text[i]);  
            hexString.toUpperCase().getChars(hexString.length() - 2, hexString.length(), hexOutput, i * 2);  
		 }  
		 return hexOutput;  
	 }
	
}
