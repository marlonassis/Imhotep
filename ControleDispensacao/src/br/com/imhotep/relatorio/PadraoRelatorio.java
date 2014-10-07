package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;

public class PadraoRelatorio implements Serializable {

	private static final long serialVersionUID = 1L;


    protected void geraRelatorioExcel( String nomeRelatorio, HSSFWorkbook wb)throws ClassNotFoundException, IOException, JRException, SQLException {
		
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
		
        ServletOutputStream servletOutputStream = response.getOutputStream();
        
        ctx.responseComplete();
        response.addHeader("Content-disposition", "filename="+nomeRelatorio);        
        
        wb.write(servletOutputStream);
      
        response.setContentType("application/vnd.ms-excel");       
        servletOutputStream.flush();
        servletOutputStream.close();
    }
	
	@SuppressWarnings("rawtypes")
    protected void geraRelatorio(String caminho, String nomeRelatorio, List list, Map<String, Object> map) throws ClassNotFoundException, IOException, JRException, SQLException {
		if(map == null){
			map = new HashMap<String, Object>();
		}
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
        
		JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, map, (JRDataSource) new JRBeanCollectionDataSource(list));
        
        con.close();
        servletOutputStream.flush();
        servletOutputStream.close();
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void geraRelatorio(String caminho, String nomeRelatorio, Object obj, Map<String, Object> map) throws ClassNotFoundException, IOException, JRException, SQLException {
		if(map == null){
			map = new HashMap<String, Object>();
		}
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
        
		List<Object> list = new ArrayList<Object>();
        list.add(obj);
		JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, map, (JRDataSource) new JRBeanCollectionDataSource(list));
        
        con.close();
        servletOutputStream.flush();
        servletOutputStream.close();

    }
	
}
