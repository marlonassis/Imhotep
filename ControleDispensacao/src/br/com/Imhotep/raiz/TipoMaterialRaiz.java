package br.com.Imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.TipoMaterial;
import br.com.remendo.PadraoHome;

@ManagedBean(name="tipoMaterialRaiz")
@SessionScoped
public class TipoMaterialRaiz extends PadraoHome<TipoMaterial>{
	
	/**
	 * Método que retorna uma lista de TipoMaterial
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<TipoMaterial> getListaFabricanteSuggest(String sql){
		return super.getBusca("select o from TipoMaterial as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
