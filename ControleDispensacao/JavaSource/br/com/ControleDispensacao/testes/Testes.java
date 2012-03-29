package br.com.ControleDispensacao.testes;




public class Testes {

	
	public static void main (String[] args) {
//		Time mengo =new Time("FLAMENGO",7);
		Time bota = new Time("Teste",1);
		Thread a = new Thread(bota);
//		new Thread(mengo).start();
		a.start();
		
		a.interrupt();
		
		System.out.println("Main terminado!");
	}
	
}
