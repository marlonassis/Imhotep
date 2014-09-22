package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Funcao;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class FuncaoRaiz extends PadraoRaiz<Funcao>{
	private String funcaoFilho;
	private Funcao funcaoDelete;
	private List<Funcao> funcoesFilho = new ArrayList<Funcao>();
	
	public void cadastrarFuncaoFilho(){
		Funcao funcaoFilho = new Funcao();
		funcaoFilho.setFuncaoPai(getInstancia());
		funcaoFilho.setNome(getFuncaoFilho());
		if(super.enviarGenerico(funcaoFilho)){
			getFuncoesFilho().add(funcaoFilho);
			setFuncaoFilho(null);
		}
	}
	
	public void removerFuncaoFilho(){
		if(super.apagarGenerico(funcaoDelete)){
			getFuncoesFilho().remove(funcaoDelete);
		}
		setFuncaoDelete(null);
	}
	
	@Override
	protected void aposEnviar() {
		carregarFuncoesFilho();
		super.aposEnviar();
	}
	
	@Override
	public void setInstancia(Funcao instancia) {
		super.setInstancia(instancia);
		carregarFuncoesFilho();
	}

	private void carregarFuncoesFilho() {
		setFuncoesFilho(super.getBusca("select o from Funcao o where o.funcaoPai.idFuncao = " + getInstancia().getIdFuncao()));
	}
	
	public String getFuncaoFilho() {
		return funcaoFilho;
	}

	public void setFuncaoFilho(String funcaoFilho) {
		this.funcaoFilho = funcaoFilho;
	}

	public List<Funcao> getFuncoesFilho() {
		return funcoesFilho;
	}

	public void setFuncoesFilho(List<Funcao> funcoesFilho) {
		this.funcoesFilho = funcoesFilho;
	}

	public Funcao getFuncaoDelete() {
		return funcaoDelete;
	}

	public void setFuncaoDelete(Funcao funcaoDelete) {
		this.funcaoDelete = funcaoDelete;
	}
	
	
}
