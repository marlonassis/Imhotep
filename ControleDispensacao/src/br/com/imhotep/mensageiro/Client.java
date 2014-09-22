package br.com.imhotep.mensageiro;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
    private String host;
    private Integer port;
    private Socket client;

    public Client(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    private void execute() {
        try {
            this.client = new Socket(this.host, this.port);
            try {
                ClientHelper serverHelper = new ClientHelper(this.client.getInputStream());
                new Thread(serverHelper).start();
                Scanner input = new Scanner(System.in);
                PrintStream output = new PrintStream(this.client.getOutputStream());
                while (input.hasNextLine()) {
                    output.println(input.nextLine());
                }
                output.close();
                input.close();
                client.close();
            } catch (IOException e) {
                System.out.println("[Client] N‹o foi poss’vel mater uma conex‹o segura com o servidor.");
            }
        } catch (UnknownHostException e) {
            System.out.println("[Client] O servidor " + this.host + ":" + this.port + " n‹o pode ser encontrado.");
        } catch (IOException e) {
            System.out.println("[Client] Erro ao estabelecer conex‹o com o servidor.");
        }
    }

    public static void main(String[] args) {
        new Client("localhost", Server.PORT).execute();
    }
}