package br.com.Imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.com.Imhotep.auxiliar.Constantes;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PadraoRelatorio implements Serializable {

	private static final long serialVersionUID = 1L;

    protected void geraRelatorio(String caminho, String nomeRelatorio) throws ClassNotFoundException, IOException, JRException, SQLException {
		geraRelatorio(caminho, nomeRelatorio, null, null);
	}
	
	@SuppressWarnings("rawtypes")
    protected void geraRelatorio(String caminho, String nomeRelatorio, List list, Map<String, Object> map) throws ClassNotFoundException, IOException, JRException, SQLException {
    	String url = Constantes.URL_BANCO;
		String usuario = Constantes.USUARIO_BANCO;
		String senha = Constantes.SENHA_BANCO;
		Class.forName("org.postgresql.Driver"); 
		Connection con = DriverManager.getConnection(url, usuario, senha);
		
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        
		InputStream reportStream = ctx.getExternalContext().getResourceAsStream(caminho);
		
        ServletOutputStream servletOutputStream = response.getOutputStream();
        
        ctx.responseComplete();
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "filename="+nomeRelatorio);
        
        if(list != null){
        	//caso exista valor na lista deve-se usar seus itens 
        	JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, map, (JRDataSource) new JRBeanCollectionDataSource(list));
        }else{
        	//caso a lista esteja nula os itens vem do próprio arquivo
        	JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, map, con);
        }
        
        con.close();
        servletOutputStream.flush();
        servletOutputStream.close();

    }
	
}
