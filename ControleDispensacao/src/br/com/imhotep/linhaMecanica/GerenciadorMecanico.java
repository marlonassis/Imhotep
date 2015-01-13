package br.com.imhotep.linhaMecanica;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import br.com.imhotep.auxiliar.Constantes;

public class GerenciadorMecanico {
	private String driver = "org.postgresql.Driver";
	private String ip = Constantes.IP_LOCAL;
	private String porta = "5432";
	private String url = "jdbc:postgresql://{ip}:{porta}/{banco}";
	private String nomeBanco = Constantes.NOME_BANCO_IMHOTEP;
	private String usuarioBanco = Constantes.USUARIO_BANCO;
	private String senhaBanco = Constantes.SENHA_BANCO;

	private Connection c = null;
	private Statement s = null;
	
	public GerenciadorMecanico() {
		// Register the native JDBC driver. If the driver cannot 
		// be registered, the test cannot continue.
		try {
			Class.forName(driver);
		} catch (Exception e) {
			System.out.println("Driver failed to register.");
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	protected void mensagem(String msg, String msg2, Severity tipoMensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipoMensagem,msg, msg2));
	}
	
	public String ascii_to_latin1(String str) {
		if(str != null){
			try{
				InputStream is = new ByteArrayInputStream(str.getBytes());
			    String text = "";
	
			    // setup readers with Latin-1 (ISO 8859-1) encoding
			    BufferedReader i = new BufferedReader(new InputStreamReader(is, "8859_1"));
	
			    int numBytes;
			    CharBuffer buf = CharBuffer.allocate(512);
			    while ((numBytes = i.read(buf)) != -1) {
			        text += String.copyValueOf(buf.array(), 0, numBytes);
			        buf.clear();
			    }
	
			    return text;
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public String utf8_to_latin1(String str){
		if(str != null){
	       try{
	          String stringToConvert = str;
	          byte[] convertStringToByte = stringToConvert.getBytes("UTF-8");
	          return new String(convertStringToByte, "ISO-8859-1");
	       }catch(Exception e){
	    	   System.out.println(e.getStackTrace());
	       }
		}
		return null;
	}
	
	private Connection createConnection(String nomeBanco) throws SQLException {
		// Create the connection properties.
		Properties properties = new Properties ();
		properties.put ("user", getUsuarioBanco());
		properties.put ("password", getSenhaBanco());

		// Connect to the local database.
		String urlCompleta = url.replace("{banco}", nomeBanco).replace("{ip}", ip).replace("{porta}", porta);
		return DriverManager.getConnection(urlCompleta, properties);
	}
	
	public ResultSet fastConsulta(String sql){
		 try {
		    	PreparedStatement ps = c.prepareStatement(sql);
		    	return ps.executeQuery();
	        } catch (SQLException e) {
	        	e.printStackTrace();
	        }
		 return null;
	}
	
	public ResultSet consultar(String sql){
		ResultSet rs = null;
        try {
			criarConexao();
	    	PreparedStatement ps = c.prepareStatement(sql);
	    	rs = ps.executeQuery();
        } catch (SQLException e) {
        	e.printStackTrace();
        }finally{
        	fecharConexoes();
        }
        
        return rs;
	}

	public void criarConexao() throws SQLException {
		c = createConnection((getNomeBanco() == null || getNomeBanco().isEmpty()) ? "postgres" : getNomeBanco());
		s = c.createStatement();
	}

	public void fecharConexoes() {
		try {
            if (c != null) {
                c.close();
            }
            if (s != null) {
                s.close();
            }
        } catch (SQLException e) {
            System.out.println("Cleanup failed to close Connection.");
        }
	}

	private HashMap<String, String> fluxo = new HashMap<String, String>(); 
	
	public boolean executarQueryFluxo() {
		boolean ret = true;
		String chaveAtual = null;
        try {
        	criarConexao();
        	
        	Set<String> chaves = getFluxo().keySet();
        	for(String chave : chaves){
        		chaveAtual = chave;
        		s.executeUpdate(getFluxo().get(chave));
        	}
        } catch (SQLException sqle) {
        	System.out.println("Erro durante a execu��o do fluxo: "+chaveAtual);
            System.out.println("Database processing has failed.");
            System.out.println("Reason: " + sqle.getMessage());
            ret = false;
        } finally {
            try {
                if (s != null) {
                    s.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                System.out.println("Cleanup failed to close Statement.");
            }
        }
        return ret;
	}
	
	protected boolean fastExecutarQuery(String query) {
		boolean ret = true;
        try {
			s.executeUpdate(query);
        } catch (SQLException sqle) {
            System.out.println("Database processing has failed.");
            System.out.println("Reason: " + sqle.getMessage());
            ret = false;
        }
        return ret;
	}
	
	protected boolean executarQuery(String query) {
		boolean ret = true;
        try {
        	criarConexao();
			s.executeUpdate(query);
        } catch (SQLException sqle) {
            System.out.println("Database processing has failed.");
            System.out.println("Reason: " + sqle.getMessage());
            ret = false;
        } finally {
            try {
                if (s != null) {
                    s.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                System.out.println("Cleanup failed to close Statement.");
            }
        }
        return ret;
	}

	protected static String adicionaAspas(String valor){
		if(valor == null){
			return null;
		}
		
		return "'".concat(valor).concat("'");
	}
	
	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}


	public HashMap<String, String> getFluxo() {
		return fluxo;
	}


	public void setFluxo(HashMap<String, String> fluxo) {
		this.fluxo = fluxo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getUsuarioBanco() {
		return usuarioBanco;
	}

	public void setUsuarioBanco(String usuarioBanco) {
		this.usuarioBanco = usuarioBanco;
	}

	public String getSenhaBanco() {
		return senhaBanco;
	}

	public void setSenhaBanco(String senhaBanco) {
		this.senhaBanco = senhaBanco;
	}
}
