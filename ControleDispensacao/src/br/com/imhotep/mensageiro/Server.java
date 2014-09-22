package br.com.imhotep.mensageiro;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private Integer port;
    private Socket client;
    private ServerSocket server;
    private List<PrintStream> clients;
    public static final Integer PORT = 5000;

    public Server(Integer port) {
        this.port = port;
        this.clients = new ArrayList<PrintStream>();
    }

    private void execute() {
        System.out.println("[Server] Inicializando servidor...");
        try {
            this.server = new ServerSocket(this.port);
            System.out.println("[Server] Servidor aguardando conex›es...");
        } catch (IOException e) {
            System.out.println("[Server] A porta " + this.port + "ocupada. Tente mais tarde.");
        }
        while (true) {
            try {
                this.client = this.server.accept();
                System.out
                        .println("[Server] Nova conex‹o com o cliente: " + this.client.getInetAddress().getHostName());
                PrintStream output = new PrintStream(this.client.getOutputStream(), true);
                this.clients.add(output);
                ServerHelper clientHelper = new ServerHelper(this.client.getInputStream(), this);
                new Thread(clientHelper).start();
            } catch (IOException e) {
                System.out.println("[Server] N‹o foi poss’vel estabeler conex‹o com o cliente.");
            }
        }
    }

    public void broadcast(String message) {
        for (PrintStream client : this.clients) {
            client.println(message);
        }
    }

    public static void main(String[] args) {
        new Server(Server.PORT).execute();
    }
}