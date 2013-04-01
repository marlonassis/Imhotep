package br.com.imhotep.linhaMecanica;

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

import br.com.Imhotep.auxiliar.Constantes;

public class GerenciadorMecanico {
	private String driver = "org.postgresql.Driver";
	private String url    = "jdbc:postgresql://127.0.0.1:5432/";
	private String nomeBanco;

	private Connection c = null;
	private Statement s = null;
	
	protected void mensagem(String msg, String msg2, Severity tipoMensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipoMensagem,msg, msg2));
	}
	
	public String utf8_to_latin1(String str){
       try{
          String stringToConvert = str;
          byte[] convertStringToByte = stringToConvert.getBytes("UTF-8");
          return new String(convertStringToByte, "ISO-8859-1");
       }catch(Exception e){
    	   System.out.println(e.getStackTrace());
       }
		return null;
	}
	
	private void registrarDriver() {
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
	
	private Connection createConnection(String nomeBanco) throws SQLException {
		// Create the connection properties.
		Properties properties = new Properties ();
		properties.put ("user", Constantes.USUARIO_BANCO);
		properties.put ("password", Constantes.SENHA_BANCO);

		// Connect to the local database.
		return DriverManager.getConnection(url.concat(nomeBanco), properties);
	}
	
	public ResultSet consultar(String sql){
		registrarDriver();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
			c = createConnection((getNomeBanco() == null || getNomeBanco().isEmpty()) ? "postgres" : getNomeBanco());
	    	s = c.createStatement();
	        ps = c.prepareStatement(sql);
	        rs = ps.executeQuery();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        
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
        
        return rs;
	}

	private HashMap<String, String> fluxo = new HashMap<String, String>(); 
	
	public boolean executarQueryFluxo() {
		registrarDriver();
		boolean ret = true;
		String chaveAtual = null;
        try {
        	c = createConnection((getNomeBanco() == null || getNomeBanco().isEmpty()) ? "postgres" : getNomeBanco());
        	s = c.createStatement();
        	Set<String> chaves = getFluxo().keySet();
        	for(String chave : chaves){
        		chaveAtual = chave;
        		s.executeUpdate(getFluxo().get(chave));
        	}
        } catch (SQLException sqle) {
        	System.out.println("Erro durante a execução do fluxo: "+chaveAtual);
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
	
	protected boolean executarQuery(String query) {
		registrarDriver();
		boolean ret = true;
        try {
        	c = createConnection((getNomeBanco() == null || getNomeBanco().isEmpty()) ? "postgres" : getNomeBanco());
        	s = c.createStatement();
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
}
