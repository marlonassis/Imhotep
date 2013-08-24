package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.DevolucaoMedicamentoItem;
import br.com.imhotep.enums.TipoStatusDevolucaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoDevolucaoItemInseridaDuasVezes;
import br.com.imhotep.excecoes.ExcecaoDevolucaoMedicamentoSemItens;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class DevolucaoMedicamentoItemRaiz extends PadraoHome<DevolucaoMedicamentoItem>{
	
	private List<DevolucaoMedicamentoItem> itens = new ArrayList<DevolucaoMedicamentoItem>();
	private boolean exibirDialogAlterarQuantidade;
	
	public static DevolucaoMedicamentoItemRaiz getInstanciaAtual(){
		try {
			return (DevolucaoMedicamentoItemRaiz) Utilitarios.procuraInstancia(DevolucaoMedicamentoItemRaiz.class);
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
		itens = new ArrayList<DevolucaoMedicamentoItem>();
		exibirDialogAlterarQuantidade = false;
		super.novaInstancia();
	}
	
	public DevolucaoMedicamentoItemRaiz(DevolucaoMedicamentoItem item){
		super();
		setInstancia(item);
	}
	
	public DevolucaoMedicamentoItemRaiz(){
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
		getInstancia().setDevolucaoMedicamento(DevolucaoMedicamentoRaiz.getInstanciaAtual().getInstancia());
		super.preEnvio();
	}
	
	private void verificaItemInseridoDuasVezes() throws ExcecaoDevolucaoItemInseridaDuasVezes {
		for(DevolucaoMedicamentoItem item : getItens()){
			if(item.getMaterial().equals(getInstancia().getMaterial())){
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
				throw new ExcecaoDevolucaoMedicamentoSemItens();
			}
			getInstancia().setStatus(TipoStatusDevolucaoItemEnum.P);
			if(super.enviar()){
				getItens().add(getInstancia());
				super.novaInstancia();
				return true;
			}
		} catch (ExcecaoDevolucaoMedicamentoSemItens e) {
			e.printStackTrace();
		} catch (ExcecaoDevolucaoItemInseridaDuasVezes e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean apagar() {
		DevolucaoMedicamentoItem instancia = getInstancia();
		if(super.apagar()){
			getItens().remove(instancia);
			return true;
		}
		return false;
	}
	
	public List<DevolucaoMedicamentoItem> getItens() {
		return itens;
	}

	public void setItens(List<DevolucaoMedicamentoItem> itens) {
		this.itens = itens;
	}

	public boolean getExibirDialogAlterarQuantidade() {
		return exibirDialogAlterarQuantidade;
	}

	public void setExibirDialogAlterarQuantidade(boolean exibirDialogAlterarQuantidade) {
		this.exibirDialogAlterarQuantidade = exibirDialogAlterarQuantidade;
	}

}
