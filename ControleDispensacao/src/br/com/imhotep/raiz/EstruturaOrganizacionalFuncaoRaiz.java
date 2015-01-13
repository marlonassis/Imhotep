package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalFuncao;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EstruturaOrganizacionalFuncaoRaiz extends PadraoRaiz<EstruturaOrganizacionalFuncao>{
	private EstruturaOrganizacional estruturaOrganizacional;
	private List<EstruturaOrganizacionalFuncao> funcoes = new ArrayList<EstruturaOrganizacionalFuncao>();
	
	public void carregarDados(EstruturaOrganizacional estruturaOrganizacional){
		setEstruturaOrganizacional(estruturaOrganizacional);
		atualizarFuncoes();
	}
	
	public void cadastrarFuncao(){
		getInstancia().setEstruturaOrganizacional(getEstruturaOrganizacional());
		if(super.enviar()){
			atualizarFuncoes();
			super.novaInstancia();
		}
	}
	
	@Override
	public boolean apagarInstancia() {
		if(super.apagarInstancia()){
			atualizarFuncoes();
			return true;
		}
		return false;
	}
	
	private void atualizarFuncoes() {
		int id = getEstruturaOrganizacional().getIdEstruturaOrganizacional();
		String hql = "select o from EstruturaOrganizacionalFuncao o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id;
		setFuncoes(super.getBusca(hql));
	}

	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}

	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}

	public List<EstruturaOrganizacionalFuncao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<EstruturaOrganizacionalFuncao> funcoes) {
		this.funcoes = funcoes;
	}
}
