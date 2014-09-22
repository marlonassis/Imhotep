package br.com.imhotep.raiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.entidade.LaboratorioResultadoPezinhoTemp;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;


@ManagedBean
@SessionScoped
public class LaboratorioResultadoPezinhoTempRaiz extends PadraoRaiz<LaboratorioResultadoPezinhoTemp>{
	
	private UploadedFile uploadedFile;
	private StreamedContent file;  
    
    public void downloadResultadoPezinhoTemp(String exameID) {          
    		Runtime run = Runtime.getRuntime();
    		try {
    			String pathApp = 
//    					"/Users/marlonassis/Programacao/examePezinhoTempLaboratorio.jar";
    					"/home/imhotep/App/ExamePezinhoTempLaboratorio.jar";
    			String comm = "java -jar " + pathApp + " -exame "+exameID;
    			Process saida = run.exec(comm);
    			saida.waitFor();
    			
    			InputStream stderr = saida.getErrorStream();
                InputStreamReader isr = new InputStreamReader(stderr);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                System.out.println("<ERROR>");
                while ( (line = br.readLine()) != null)
                    System.out.println(line);
                System.out.println("</ERROR>");
    			
    			
	    		File file = new File("/tmp/"+exameID+".pdf");
	    		InputStream input = new FileInputStream(file);
	    		HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	            res.setContentType("application/pdf");  
	            res.setHeader("Content-disposition", "inline;filename="+exameID+".pdf");  
	            OutputStream output = res.getOutputStream();
	            byte[] buffer = new byte[10240];
	            try {
	                for (int length = 0; (length = input.read(buffer)) > 0;) {
	                    output.write(buffer, 0, length);
	                }
	            }
	            finally {
	                try { output.close(); } catch (IOException ignore) {}
	                try { input.close(); } catch (IOException ignore) {}
	            }
	            FacesContext.getCurrentInstance().responseComplete();  
    		} catch (IOException e) {
    			e.printStackTrace();
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
        
    }
    
    public StreamedContent getFile() {  
        return file;  
    } 
	
	@Override
	public boolean atualizar() {
		String sql = "delete from tb_laboratorio_resultado_pezinho_temp where in_exame_identificacao = "+getInstancia().getExameIdentificacao();
		new LinhaMecanica("db_imhotep", "127.0.0.1").executarCUD(sql);
		getInstancia().setIdLaboratorioResultadoPezinhoTemp(0);
		return enviar();
	}
	
	@Override
	protected void preEnvio() {
		try {
			if(getInstancia().getPaginaWebByte() == null)
				getInstancia().setPaginaWebByte(getUploadedFile().getContents());
			getInstancia().setDataCadastro(new Date());
			getInstancia().setProfissionalCadastro(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} 
		super.preEnvio();
	}
	
	@Override
	protected void aposEnviar() {
		Runtime run = Runtime.getRuntime();
		try {
			String path = Parametro.diretorioArquivosConsultasAGHU();
			String pathApp = path+"/home/imhotep/App/examePezinhoTempLaboratorio.jar";
			String comm = "java -jar " + pathApp + " -exame "+getInstancia().getExameIdentificacao() + " -resultado "+getInstancia().getResultadoExame();
			Process saida = run.exec(comm);
			saida.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.aposEnviar();
	}
	
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		super.mensagem(event.getFile().getFileName() + " foi enviado com sucesso.", null, Constantes.INFO);
		setUploadedFile(event.getFile());
	}  
	
}
