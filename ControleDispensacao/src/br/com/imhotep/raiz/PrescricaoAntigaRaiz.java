package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.PrescricaoAntiga;
import br.com.imhotep.entidade.PrescricaoAntigaArquivo;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class PrescricaoAntigaRaiz extends PadraoHome<PrescricaoAntiga> {
	
	private List<UploadedFile> files = new ArrayList<UploadedFile>();
	
	public void removeItemFiles(UploadedFile item){
		getFiles().remove(item);
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		super.mensagem(event.getFile().getFileName() + " foi enviado com sucesso.", null, Constantes.INFO);
        getFiles().add(event.getFile());
	}  
	
	@Override
	public boolean enviar() {
		try {
			getFiles().get(0).getContents().equals(getFiles().get(1).getContents());
			getInstancia().setDataInsercao(new Date());
			getInstancia().setProfissionalInsercao(Autenticador.getInstancia().getProfissionalAtual());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(super.enviar()){
			for(UploadedFile file : files){
				gravarArquivo(file);
			}
			setFiles(new ArrayList<UploadedFile>());
		}
		return true;
	}

	private void gravarArquivo(UploadedFile file) {
		PrescricaoAntigaArquivo paa = new PrescricaoAntigaArquivo();
		paa.setArquivo(file.getContents());
		paa.setNomeArquivo(file.getFileName());
		paa.setPrescricaoAntiga(getInstancia());
		paa.setTamanho(file.getSize());
		new PrescricaoAntigaArquivoRaiz(paa).enviar();
	}

	public List<UploadedFile> getFiles() {
		return files;
	}

	public void setFiles(List<UploadedFile> files) {
		this.files = files;
	}

}
