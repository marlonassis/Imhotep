package br.com.imhotep.consulta.raiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="estoqueMaterialConsultaRaiz")
@SessionScoped
public class EstoqueMaterialConsultaRaiz extends ConsultaGeral<Estoque> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<Estoque> listaEstoque = new ArrayList<Estoque>();
	private Material material;
	
	public void consultar(){
		String sql = "select o from Estoque o where o.material.idMaterial = "+material.getIdMaterial();
		StringBuilder stringB = new StringBuilder(sql);
		setListaEstoque(new ArrayList<Estoque>(super.consulta(stringB, null)));
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
