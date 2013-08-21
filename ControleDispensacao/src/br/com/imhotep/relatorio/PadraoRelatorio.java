package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;

public class PadraoRelatorio implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
    protected void geraRelatorio(String caminho, String nomeRelatorio, List list, Map<String, Object> map) throws ClassNotFoundException, IOException, JRException, SQLException {
		map.put("LOGO_HU", Utilitarios.getImagemLogoHU());
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void geraRelatorio(String caminho, String nomeRelatorio, Object obj, Map<String, Object> map) throws ClassNotFoundException, IOException, JRException, SQLException {
		map.put("LOGO_HU", Utilitarios.getImagemLogoHU());
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
        
		List list = new ArrayList();
        list.add(obj);
		JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, map, (JRDataSource) new JRBeanCollectionDataSource(list));
        
        con.close();
        servletOutputStream.flush();
        servletOutputStream.close();

    }
	
}
