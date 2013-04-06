package br.com.imhotep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Painel;
import br.com.remendo.utilidades.Utilities;

@ManagedBean(name="controlePainel")
@SessionScoped
public class ControlePainel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<String> painelAutorizadoStringList = new ArrayList<String>();
	private List<Painel> painelAutorizadoList = new ArrayList<Painel>();
	
	public static ControlePainel getInstancia(){
		try {
			return (ControlePainel) Utilities.procuraInstancia(ControlePainel.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void convertePainelString(){
		setPainelAutorizadoStringList(new ArrayList<String>());
		for(Painel painel : painelAutorizadoList){
			getPainelAutorizadoStringList().add(painel.getDescricao());
		}
	}

	public List<String> getPainelAutorizadoStringList() {
		return painelAutorizadoStringList;
	}

	public void setPainelAutorizadoStringList(
			List<String> painelAutorizadoStringList) {
		this.painelAutorizadoStringList = painelAutorizadoStringList;
	}

	public List<Painel> getPainelAutorizadoList() {
		return painelAutorizadoList;
	}

	public void setPainelAutorizadoList(List<Painel> painelAutorizadoList) {
		this.painelAutorizadoList = painelAutorizadoList;
	}
	
	
}
