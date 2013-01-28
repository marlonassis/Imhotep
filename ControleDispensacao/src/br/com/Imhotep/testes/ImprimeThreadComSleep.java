package br.com.Imhotep.testes;

public class ImprimeThreadComSleep extends Thread{
	private int raiz;
	public ImprimeThreadComSleep(int raiz){
		this.raiz = raiz;
	}
	public void run(){
		int i=1;
		int n=raiz;
		while(true){
			n= raiz*i;
			System.out.println(n);
			i++;
			if(Thread.interrupted()){
				System.out.println("terminada");
				return;
			}else{
				System.out.println("rolando");
			}
		}
	}
}