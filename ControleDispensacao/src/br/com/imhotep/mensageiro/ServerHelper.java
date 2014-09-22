package br.com.imhotep.mensageiro;

import java.io.InputStream;
import java.util.Scanner;

public class ServerHelper implements Runnable {
    private InputStream client;
    private Server server;

    public ServerHelper(InputStream client, Server server) {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
        Scanner buffer = new Scanner(this.client);
        while (buffer.hasNextLine()) {
            String message = buffer.nextLine().toString();
            System.out.println("[Server] Mensagem recebida do cliente: " + message);
            this.server.broadcast(message);
        }
        buffer.close();
    }
}