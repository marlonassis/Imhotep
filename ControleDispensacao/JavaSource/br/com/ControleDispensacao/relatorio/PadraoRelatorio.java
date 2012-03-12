package br.com.ControleDispensacao.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class PadraoRelatorio {

    protected void geraRelatorio(String caminho) throws ClassNotFoundException, IOException, JRException, SQLException {
    	String url = "jdbc:postgresql://127.0.0.1:5432/db_farmacia";
		String usuario = "postgres";
		String senha = "postgres";
		Class.forName("org.postgresql.Driver"); 
		Connection con = DriverManager.getConnection(url, usuario, senha);


        FacesContext ctx = FacesContext.getCurrentInstance();

        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();

		InputStream reportStream = ctx.getExternalContext().getResourceAsStream(caminho);

        ServletOutputStream servletOutputStream = response.getOutputStream();

        ctx.responseComplete();
        response.setContentType("application/pdf");

        JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, new HashMap<String, Object>(), con);

        con.close();
        servletOutputStream.flush();
        servletOutputStream.close();

    }

	
}
