package br.com.imhotep.consulta.raiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@SessionScoped
public class EstoqueMaterialConsultaRaiz extends ConsultaGeral<Estoque> implements Serializable{
	
	private static final long serialVersionUID = -5136486263527574062L;

	private List<Estoque> listaEstoque = new ArrayList<Estoque>();
	private Material material;
	
	public void consultar(){
		setListaEstoque(new EstoqueConsultaRaiz().consultarEstoquesMaterialTodos(getMaterial()));
	}

	public List<Estoque> getListaEstoque() {
		return listaEstoque;
	}

	public void setListaEstoque(List<Estoque> listaEstoque) {
		this.listaEstoque = listaEstoque;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
}
