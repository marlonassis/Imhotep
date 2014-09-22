package br.com.imhotep.raiz;

import java.util.HashSet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.LaboratorioExameAnaliseFormulario;
import br.com.imhotep.entidade.LaboratorioExameAnaliseGrupo;
import br.com.imhotep.entidade.LaboratorioExameAnaliseItem;
import br.com.imhotep.entidade.LaboratorioFormularioItem;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class LaboratorioFormularioRaiz extends PadraoRaiz<LaboratorioExameAnaliseFormulario>{
	
	private boolean exibirDialogGrupo;
	private boolean exibirDialogItem;
	private LaboratorioExameAnaliseGrupo grupo = null;
	private LaboratorioExameAnaliseGrupo grupoCadastro = new LaboratorioExameAnaliseGrupo();
	private LaboratorioExameAnaliseItem item = new LaboratorioExameAnaliseItem();
	private LaboratorioExameAnaliseItem itemCadastro = new LaboratorioExameAnaliseItem();
	private LaboratorioFormularioItem formularioItem = new LaboratorioFormularioItem();
	
	public void cadastrarGrupo(){
		if(super.enviarGenerico(getGrupoCadastro())){
			setGrupoCadastro(new LaboratorioExameAnaliseGrupo());
		}
	}
	
	public void exibirDialogItem(){
		setExibirDialogItem(true);
	}
	
	public void ocultarDialogItem(){
		setExibirDialogItem(false);
	}
	
	public void exibirDialogGrupo(){
		setExibirDialogGrupo(true);
	}
	
	public void ocultarDialogGrupo(){
		setExibirDialogGrupo(false);
	}
	
	public void cadastrarFormulario(){
		super.enviar();
	}
	
	@Override
	protected void aposEnviar() {
		getInstancia().setItens(new HashSet<LaboratorioFormularioItem>());
		super.aposEnviar();
	}
	
	public void apagarItem(){
		if(super.apagarGenerico(getFormularioItem())){
			getInstancia().getItens().remove(getFormularioItem());
			setFormularioItem(new LaboratorioFormularioItem());
		}
	}
	
	public void cadastrarItem(){
		if(super.enviarGenerico(getItemCadastro())){
			setItemCadastro(new LaboratorioExameAnaliseItem());
		}
	}
	
	public void cadastrarItemFormulario(){
		LaboratorioFormularioItem formItem = new LaboratorioFormularioItem();
		formItem.setFormulario(getInstancia());
		formItem.setGrupo(getGrupo());
		formItem.setItem(getItem());
		if(super.enviarGenerico(formItem)){
			getInstancia().getItens().add(formItem);
			setItem(new LaboratorioExameAnaliseItem());
			setGrupo(new LaboratorioExameAnaliseGrupo());
		}
	}
	
	public LaboratorioExameAnaliseGrupo getGrupo() {
		return grupo;
	}
	public void setGrupo(LaboratorioExameAnaliseGrupo grupo) {
		this.grupo = grupo;
	}
	public LaboratorioExameAnaliseItem getItem() {
		return item;
	}
	public void setItem(LaboratorioExameAnaliseItem item) {
		this.item = item;
	}
	public LaboratorioFormularioItem getFormularioItem() {
		return formularioItem;
	}
	public void setFormularioItem(LaboratorioFormularioItem formularioItem) {
		this.formularioItem = formularioItem;
	}

	public boolean isExibirDialogGrupo() {
		return exibirDialogGrupo;
	}

	public void setExibirDialogGrupo(boolean exibirDialogGrupo) {
		this.exibirDialogGrupo = exibirDialogGrupo;
	}

	public LaboratorioExameAnaliseGrupo getGrupoCadastro() {
		return grupoCadastro;
	}

	public void setGrupoCadastro(LaboratorioExameAnaliseGrupo grupoCadastro) {
		this.grupoCadastro = grupoCadastro;
	}

	public boolean isExibirDialogItem() {
		return exibirDialogItem;
	}

	public void setExibirDialogItem(boolean exibirDialogItem) {
		this.exibirDialogItem = exibirDialogItem;
	}

	public LaboratorioExameAnaliseItem getItemCadastro() {
		return itemCadastro;
	}

	public void setItemCadastro(LaboratorioExameAnaliseItem itemCadastro) {
		this.itemCadastro = itemCadastro;
	}
	
}
