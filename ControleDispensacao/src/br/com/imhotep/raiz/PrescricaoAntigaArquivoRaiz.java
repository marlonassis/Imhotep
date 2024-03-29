package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.com.imhotep.entidade.PrescricaoAntigaArquivo;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class PrescricaoAntigaArquivoRaiz extends PadraoRaiz<PrescricaoAntigaArquivo> {
	
	public PrescricaoAntigaArquivoRaiz(){
	}
	
	public PrescricaoAntigaArquivoRaiz(PrescricaoAntigaArquivo paa){
		setInstancia(paa);
		setExibeMensagemInsercao(false);
		
		inserirDataProfissional();
	}

	private void inserirDataProfissional() {
		try {
			getInstancia().setDataInsercao(new Date());
			getInstancia().setProfissionalInsercao(Autenticador.getInstancia().getProfissionalAtual());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private PrescricaoAntigaArquivo getArquivo(Integer id){
		return super.getBusca("select o from PrescricaoAntigaArquivo o where o.idPrescricaoAntigaArquivo = "+id).get(0);
	}
	
	public void vizualizarArquivo(String id){
		PrescricaoAntigaArquivo arquivo = getArquivo(Integer.valueOf(id));
		HttpServletResponse response=(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition", "filename="+arquivo.getNomeArquivo());
        response.setContentType("application/pdf");  
        ServletOutputStream ouputStream = null;                 
        try{                           
             byte[] bytes = arquivo.getArquivo();
             response.setContentLength(bytes.length);
             ouputStream = response.getOutputStream();
             ouputStream.write(bytes, 0, bytes.length);
             ouputStream.flush();
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
}
