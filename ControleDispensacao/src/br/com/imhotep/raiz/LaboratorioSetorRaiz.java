package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.LaboratorioSetor;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class LaboratorioSetorRaiz extends PadraoRaiz<LaboratorioSetor>{
	
	private boolean exibirModalSetor;
	
	private LaboratorioSetor setorCadastro = new LaboratorioSetor();
	private LaboratorioSetor setorEdicao = new LaboratorioSetor();
	
	public void ocultarModalSetor(){
		setExibirModalSetor(false);
	}
	
	public void exibirModalSetor(){
		setExibirModalSetor(true);
	}
	
	public void verificarSetorEdicaoNull(){
		if(getSetorEdicao() != null)
			setSetorCadastro(getSetorEdicao());
		else
			setSetorCadastro(new LaboratorioSetor());
	}
	
	public void apagarSetor(){
		setInstancia(getSetorCadastro());
		if(apagar()){
			novaInstancia();
			setSetorCadastro(new LaboratorioSetor());
			setSetorEdicao(new LaboratorioSetor());
		}
	}
	
	public void cadastrarAtualizarSetor(){
		if(getSetorEdicao() != null){
			if(atualizarGenerico(getSetorCadastro()) != null){
				setSetorEdicao(getSetorCadastro());
			}
		}else{
			try {
				getSetorCadastro().setDataCadastro(new Date());
				getSetorCadastro().setProfissionalCadastro(Autenticador.getProfissionalLogado());
				if(enviarGenerico(getSetorCadastro()))
					setSetorCadastro(new LaboratorioSetor());
			} catch (ExcecaoProfissionalLogado e) {
				e.printStackTrace();
			}
		}
	}

	public LaboratorioSetor getSetorCadastro() {
		return setorCadastro;
	}

	public void setSetorCadastro(LaboratorioSetor setorCadastro) {
		this.setorCadastro = setorCadastro;
	}

	public LaboratorioSetor getSetorEdicao() {
		return setorEdicao;
	}

	public void setSetorEdicao(LaboratorioSetor setorEdicao) {
		this.setorEdicao = setorEdicao;
	}

	public boolean isExibirModalSetor() {
		return exibirModalSetor;
	}

	public void setExibirModalSetor(boolean exibirModalSetor) {
		this.exibirModalSetor = exibirModalSetor;
	}
	
}
