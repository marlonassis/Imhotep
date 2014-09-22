package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.Configuracao;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class ConfiguracaoRaiz extends PadraoRaiz<Configuracao>{
	
	private UploadedFile uploadedFile;

	@Override
	public boolean atualizar() {
		if(getUploadedFile() != null)
			getInstancia().setValorByte(getUploadedFile().getContents());
		return super.atualizar();
	}
	
	@Override
	protected void preEnvio() {
		getInstancia().setValorByte(getUploadedFile().getContents());
		super.preEnvio();
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
