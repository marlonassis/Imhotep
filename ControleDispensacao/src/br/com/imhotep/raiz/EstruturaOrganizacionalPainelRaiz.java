package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalPainel;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EstruturaOrganizacionalPainelRaiz extends PadraoRaiz<EstruturaOrganizacionalPainel>{
	private EstruturaOrganizacional estruturaOrganizacional;
	private List<EstruturaOrganizacionalPainel> paineis = new ArrayList<EstruturaOrganizacionalPainel>();
	
	public void carregarDados(EstruturaOrganizacional estruturaOrganizacional){
		setEstruturaOrganizacional(estruturaOrganizacional);
		atualizarPaineis();
	}
	
	public void cadastrarPainel(){
		getInstancia().setEstruturaOrganizacional(getEstruturaOrganizacional());
		if(super.enviar()){
			atualizarPaineis();
			super.novaInstancia();
		}
	}
	
	@Override
	public boolean apagarInstancia() {
		if(super.apagarInstancia()){
			atualizarPaineis();
			return true;
		}
		return false;
	}
	
	private void atualizarPaineis() {
		int id = getEstruturaOrganizacional().getIdEstruturaOrganizacional();
		String hql = "select o from EstruturaOrganizacionalPainel o where o.estruturaOrganizacional.idEstruturaOrganizacional = " + id;
		setPaineis(super.getBusca(hql));
	}

	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}

	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}

	public List<EstruturaOrganizacionalPainel> getPaineis() {
		return paineis;
	}

	public void setPaineis(List<EstruturaOrganizacionalPainel> paineis) {
		this.paineis = paineis;
	}
}
