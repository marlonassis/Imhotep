package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.LaboratorioExame;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class LaboratorioExameRaiz extends PadraoRaiz<LaboratorioExame>{
	private boolean exibirModalExame;
	private LaboratorioExame exameEdi = new LaboratorioExame();
	private LaboratorioExame exameCad = new LaboratorioExame();
	
	public void verificarExameEdicaoNull(){
		if(getExameEdi() != null){
			setExameCad(getExameEdi());
		}else{
			setExameCad(new LaboratorioExame());
		}
	}
	
	public void apagarExame(){
		super.apagarGenerico(getExameCad());
	}
	
	public void cadastrarAtualizarExame(){
		if(getExameCad().getIdLaboratorioExame() == 0){
			adicionarProfissionalDataCadastro();
			if(super.enviarGenerico(getExameCad())){
				setExameCad(new LaboratorioExame());
			}
		}else{
			if(super.atualizarGenerico(getExameCad()) != null){
				setExameEdi(getExameCad());
			}
		}
	}

	private void adicionarProfissionalDataCadastro() {
		try {
			getExameCad().setProfissionalCadastro(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		getExameCad().setDataCadastro(new Date());
	}
	
	public void exibirModalExame(){
		setExibirModalExame(true);
	}
	
	public void ocultarModalExame(){
		setExibirModalExame(false);
	}
	
	public boolean isExibirModalExame() {
		return exibirModalExame;
	}

	public void setExibirModalExame(boolean exibirModalExame) {
		this.exibirModalExame = exibirModalExame;
	}

	public LaboratorioExame getExameEdi() {
		return exameEdi;
	}

	public void setExameEdi(LaboratorioExame exameEdi) {
		this.exameEdi = exameEdi;
	}

	public LaboratorioExame getExameCad() {
		return exameCad;
	}

	public void setExameCad(LaboratorioExame exameCad) {
		this.exameCad = exameCad;
	}
	
	
	
}
