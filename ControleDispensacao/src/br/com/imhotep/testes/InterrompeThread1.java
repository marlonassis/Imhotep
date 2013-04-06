package br.com.imhotep.testes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterrompeThread1 {
	public static void main(String[] args){
		ImprimeThreadComSleep t;
		try{
			int raiz = Integer.parseInt("2");
			t = new ImprimeThreadComSleep(raiz);
			t.start();
			t.setName("teste");
		}catch(NumberFormatException nf){
			System.out.println("Este programa necessita de um argumento inteiro");
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			br.readLine(); // aguarda digitar ENTER
		}
		catch (IOException e) {}
		t.interrupt();
//		new Thread("teste").interrupt();
	} // main termina
}