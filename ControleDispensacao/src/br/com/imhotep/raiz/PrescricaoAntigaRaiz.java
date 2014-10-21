package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.PrescricaoAntiga;
import br.com.imhotep.entidade.PrescricaoAntigaArquivo;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class PrescricaoAntigaRaiz extends PadraoRaiz<PrescricaoAntiga> {
	
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
			getInstancia().setDataInsercao(new Date());
			getInstancia().setProfissionalInsercao(Autenticador.getInstancia().getProfissionalAtual());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		super.setExibeMensagemInsercao(false);
		return super.enviar();
	}

	private void verificarExistencia() {
		if(getInstancia().getIdPrescricaoAntiga() == 0){
			String hql = "select o from PrescricaoAntiga o where o.paciente.idPaciente = :idPaciente and o.dataPrescricao = :dataPrescricao and o.massa = :massa and o.unidade = :unidade and o.leito = :leito";
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPaciente", getInstancia().getPaciente().getIdPaciente());
			hm.put("dataPrescricao", getInstancia().getDataPrescricao());
			hm.put("massa", getInstancia().getMassa());
			hm.put("unidade", getInstancia().getUnidade());
			hm.put("leito", getInstancia().getLeito());
			PrescricaoAntiga prescricaoAntiga = new ConsultaGeral<PrescricaoAntiga>().consultaUnica(new StringBuilder(hql), hm);
			if(prescricaoAntiga != null){
				setInstancia(prescricaoAntiga);
			}
		}
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		files = new ArrayList<UploadedFile>();
	}
	
	public void uploadFinal() {
		if(!files.isEmpty()){
			verificarExistencia();
			if((!isEdicao() && enviar()) || isEdicao() ){
				for(UploadedFile file : files){
					gravarArquivo(file);
				}
				setFiles(new ArrayList<UploadedFile>());
			}
			super.mensagem("Upload terminado.", null, Constantes.INFO);
		}else{
			super.mensagem("Adicione algum arquivo.", null, Constantes.WARN);
		}
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
