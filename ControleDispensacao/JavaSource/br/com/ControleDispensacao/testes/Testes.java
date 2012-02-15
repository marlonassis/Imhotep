package br.com.ControleDispensacao.testes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.ControleDispensacao.negocio.UsuarioHome;
import br.com.nucleo.utilidades.Utilities;


public class Testes {

	
	public static void main(String args[]) {
		
			try {
				System.out.println(Testes.md5("123456"));
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
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
