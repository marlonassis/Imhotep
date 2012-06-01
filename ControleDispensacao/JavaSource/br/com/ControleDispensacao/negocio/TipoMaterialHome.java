package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TipoMaterial;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="tipoMaterialHome")
@SessionScoped
public class TipoMaterialHome extends PadraoHome<TipoMaterial>{
	
	/**
	 * Método que retorna uma lista de TipoMaterial
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<TipoMaterial> getListaFabricanteSuggest(String sql){
		return super.getBusca("select o from TipoMaterial as o where o.descricao like '%"+sql+"%' ");
	}
	
}
