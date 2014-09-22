package br.com.imhotep.mensageiro;
	import java.io.DataInputStream;
	import java.io.DataOutputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.OutputStream;
	import java.lang.String;
	import java.net.InetSocketAddress;
	import java.net.ServerSocket;
	import java.net.Socket;
	import java.net.UnknownHostException;
	import java.util.Scanner;
	import javax.swing.JOptionPane;

	public class SimpleChat2
	{
	    public static void main(String[] args)
	    {
	        String hostt = null;
	        int porrt;
	        String portt = null;
	        String a;
	        Socket socket = new Socket();
	      
	      a = JOptionPane.showInputDialog(null,"Iniciar Como Cliente?");
	      if (a.equals("sim"))
	      {
	          hostt = JOptionPane.showInputDialog(null,"Insira o host");
	          portt = JOptionPane.showInputDialog(null,"Insira a porta");
	          
	          porrt = Integer.parseInt(portt);
	          
	          startClient(hostt,porrt);
	          //non-static method startClient(int) cannot be referenced from a static context          
	      }
	      
	      else 
	      {
	          
	          portt = JOptionPane.showInputDialog(null,"Insira a porta");
	          porrt = Integer.parseInt(portt);
	          startServer(porrt);
	          //
	          //non-static method startServer(int) cannot be referenced from a static context
	          //
	      }
	      
	      
	    }

	    private static void  startClient(String host, int port)
	    {
	        
	        
	        try
	        {
	            Socket socket = new Socket();
	            socket.connect(new InetSocketAddress(host, port));
	            chat(socket);
	        }
	        catch (UnknownHostException e)
	        {
	            System.out.println("Host desconhecido");
	            System.out.println("Host: " + host);
	            System.out.println("Porta: " + port);
	            System.exit(2);
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	            System.exit(1);
	        }
	    }

	    private static void startServer(int porta) 
	    {
	        try
	        {
	            ServerSocket socket = new ServerSocket(porta);
	            System.out.println("Aguardando conexao...");
	            Socket s = socket.accept();
	            
	            chat(s);
	        } catch (IOException e)
	        {
	            System.out.println("Problemas na comunicação: ");
	            e.printStackTrace();
	            System.exit(1);
	        }
	    }

	    private static void chat(Socket s) throws IOException
	    {
	        System.out.println("Conectado com " + s.getRemoteSocketAddress());
	        listen(s.getInputStream());
	        talk(s.getOutputStream());
	    }

	    /**
	     * @param outputStream
	     */
	    private static void talk(OutputStream outputStream) throws IOException
	    {
	        DataOutputStream output = new DataOutputStream(outputStream);
	        String line = "";
	        while (!line.equals("EXIT"))
	        {
	            Scanner scan = new Scanner(System.in);        
	            System.out.print(": ");
	            line = scan.nextLine();
	            output.writeInt(line.length());
	            for (char ch : line.toCharArray())
	                output.writeChar(ch);
	            output.flush();
	        }
	    }

	    /**
	     * @param inputStream
	     */
	    private static  void listen(final InputStream inputStream)
	    {        
	        new Thread(new Runnable() {
	            DataInputStream ds = new DataInputStream(inputStream);
	            public void run() 
	            {                
	                try
	                {
	                    while (true)
	                    {
	                        int size = ds.readInt();
	                        int cont = 0;
	                        char[] chars = new char[size];
	                        while (cont < size)
	                        {
	                            chars[cont] = ds.readChar();
	                            cont = cont + 1;
	                        }
	                        
	                        String str = new String(chars);
	                        if (str.equals("EXIT"))
	                        {
	                            System.out.println("Conversa terminada.");
	                            System.exit(0);
	                        }
	                        
	                        System.out.println(str);
	                    }
	                } catch (IOException e)
	                {
	                }
	            }
	        }).start();
	    }
	}
