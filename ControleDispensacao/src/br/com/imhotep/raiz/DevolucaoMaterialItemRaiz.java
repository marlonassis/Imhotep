package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.DevolucaoMaterialItem;
import br.com.imhotep.enums.TipoStatusDevolucaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoDevolucaoItemInseridaDuasVezes;
import br.com.imhotep.excecoes.ExcecaoDevolucaoMaterialSemItens;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class DevolucaoMaterialItemRaiz extends PadraoRaiz<DevolucaoMaterialItem>{
	
	private List<DevolucaoMaterialItem> itens = new ArrayList<DevolucaoMaterialItem>();
	private boolean exibirDialogAlterarQuantidade;
	
	public static DevolucaoMaterialItemRaiz getInstanciaAtual(){
		try {
			return (DevolucaoMaterialItemRaiz) Utilitarios.procuraInstancia(DevolucaoMaterialItemRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void novaInstancia() {
		itens = new ArrayList<DevolucaoMaterialItem>();
		exibirDialogAlterarQuantidade = false;
		super.novaInstancia();
	}
	
	public DevolucaoMaterialItemRaiz(DevolucaoMaterialItem item){
		super();
		setInstancia(item);
	}
	
	public DevolucaoMaterialItemRaiz(){
		super();
	}
	
	public void abrirDialogAlterarQuantidade(){
		setExibirDialogAlterarQuantidade(true);
	}
	
	public void cancelarAjusteQuantidade(){
		super.novaInstancia();
		setExibirDialogAlterarQuantidade(false);
	}
	
	public void alterarQuantidade(){
		if(super.atualizar()){
			setExibirDialogAlterarQuantidade(false);
			super.novaInstancia();
		}
	}

	@Override
	protected void preEnvio() {
		getInstancia().setDevolucaoMaterial(DevolucaoMaterialRaiz.getInstanciaAtual().getInstancia());
		super.preEnvio();
	}
	
	private void verificaItemInseridoDuasVezes() throws ExcecaoDevolucaoItemInseridaDuasVezes {
		for(DevolucaoMaterialItem item : getItens()){
			if(item.getMaterialAlmoxarifado().equals(getInstancia().getMaterialAlmoxarifado())){
				Integer total = item.getQuantidadeDevolvida() + getInstancia().getQuantidadeDevolvida();
				item.setQuantidadeDevolvida(total);
				setInstancia(item);
				if(super.atualizar()){
					getItens().remove(item);
					getItens().add(item);
					super.novaInstancia();
					throw new ExcecaoDevolucaoItemInseridaDuasVezes();
				}
			}
		}
	}
	
	@Override
	public boolean enviar() {
		try {
			verificaItemInseridoDuasVezes();
			if(getInstancia().getQuantidadeDevolvida() == null ||getInstancia().getQuantidadeDevolvida() == 0){
				throw new ExcecaoDevolucaoMaterialSemItens();
			}
			getInstancia().setStatus(TipoStatusDevolucaoItemEnum.P);
			if(super.enviar()){
				getItens().add(getInstancia());
				super.novaInstancia();
				return true;
			}
		} catch (ExcecaoDevolucaoMaterialSemItens e) {
			e.printStackTrace();
		} catch (ExcecaoDevolucaoItemInseridaDuasVezes e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean apagar() {
		DevolucaoMaterialItem instancia = getInstancia();
		if(super.apagar()){
			getItens().remove(instancia);
			return true;
		}
		return false;
	}
	
	public List<DevolucaoMaterialItem> getItens() {
		return itens;
	}

	public void setItens(List<DevolucaoMaterialItem> itens) {
		this.itens = itens;
	}

	public boolean getExibirDialogAlterarQuantidade() {
		return exibirDialogAlterarQuantidade;
	}

	public void setExibirDialogAlterarQuantidade(boolean exibirDialogAlterarQuantidade) {
		this.exibirDialogAlterarQuantidade = exibirDialogAlterarQuantidade;
	}

}
