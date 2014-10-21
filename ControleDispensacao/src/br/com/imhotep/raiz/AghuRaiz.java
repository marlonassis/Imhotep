package br.com.imhotep.raiz;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.auxiliar.Utilitarios;

@ManagedBean
@SessionScoped
public class AghuRaiz {
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private List<String> arquivosList = new ArrayList<String>();
	private Date dataIni;
	private Date dataFim;
	private String chave;
	
	public AghuRaiz() {
		carregarDocumentosConsultasAGHU();
	}

	public void carregarDocumentosConsultasAGHU() {
		arquivosList = new ArrayList<String>();
		File diretorio = new File(Parametro.diretorioArquivosConsultasAGHU());
		diretorio.mkdirs();
        if (diretorio.isDirectory()) {
            for (File arquivo : diretorio.listFiles()) {
            	if(arquivo.isFile() && !arquivo.isHidden())
            		getArquivosList().add(arquivo.getName());
            }
        }
	}
	
	public void carregarItemDownload(String nome){
		String path = Parametro.diretorioArquivosConsultasAGHU();
		Utilitarios.downloadArquivo(path, "text/plain", nome);
	}	
	public void gerarArquivoConsultas(){
		if(dataIni != null && dataFim != null){
			Runtime run = Runtime.getRuntime();
			try {
				String path = Parametro.diretorioArquivosConsultasAGHU();
				String pathApp = path+"App/webServiceConsultasAGHU.jar";
				String ini = sdf.format(getDataIni());
				String fim = sdf.format(getDataFim());
				String comm = "java -jar "
						+ pathApp
						+ " -di "+ini
						+ " -df "+fim
						+ ((getChave() != null && !chave.isEmpty()) ?
						" -ch "+ getChave() : "");
				Process saida = run.exec(comm);
				saida.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			carregarDocumentosConsultasAGHU();
		}
	}
	
	public Date getDataIni() {
		return dataIni;
	}
	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public List<String> getArquivosList() {
		return arquivosList;
	}

	public void setArquivosList(List<String> arquivosList) {
		this.arquivosList = arquivosList;
	}

}
