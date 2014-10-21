package br.com.imhotep.mensageiro;

import java.io.InputStream;
import java.util.Scanner;

public class ClientHelper implements Runnable {
    private InputStream server;
    private Scanner buffer;

    public ClientHelper(InputStream server) {
        this.server = server;
    }

    public void run() {
        buffer = new Scanner(this.server);
        while (true) {
            System.out.print("[Client] Entre com a mensagem que deseja enviar: \n> ");
            System.out.println("[Client] Mensagem recebida do servidor: " + this.server.toString());
        }
    }
}