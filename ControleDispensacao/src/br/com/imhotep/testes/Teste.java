package br.com.imhotep.testes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			InputStream site = new URL("http://192.168.1.2/hospital/comum/login.php").openStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(site, "ISO-8859-1"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
