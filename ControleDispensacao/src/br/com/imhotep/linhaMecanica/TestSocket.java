package br.com.imhotep.linhaMecanica;

import java.net.Socket;

public class TestSocket {
	public static void main(String[] args) {
		int porta = 1;
		extracted(porta);
	}

	private static void extracted(int porta) {
		while(porta < 65536){
			try {
				Socket socket = new Socket("192.168.1.239", porta);
//				socket.
				System.out.println("Porta: "+porta);
			} catch (Exception e) {
			}
				porta++;
		}
		System.out.println("fim");
		System.exit(1);
	}
}
