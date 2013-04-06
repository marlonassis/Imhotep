package br.com.imhotep.testes;

public class Time implements Runnable {
	String nome;
	int priority;
	public Time(String nome, int priority) {
		this.nome=nome;
		this.priority=priority;
	}
	public void run() {
		Thread.currentThread().setPriority(priority);
		for (int i = 0; i < 100; i++) {
			System.out.println(i + "R " + nome);
			try {
				Thread.sleep((long)(Math.random() * 300));
			}
			catch (InterruptedException e) {}
		}
		System.out.println("TERMINOU " + nome+"!");
	}
}
