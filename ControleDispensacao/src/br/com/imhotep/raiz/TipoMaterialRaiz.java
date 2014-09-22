package br.com.imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.TipoMaterial;
import br.com.remendo.PadraoRaiz;

@ManagedBean(name="tipoMaterialRaiz")
@SessionScoped
public class TipoMaterialRaiz extends PadraoRaiz<TipoMaterial>{
	
	/**
	 * MŽtodo que retorna uma lista de TipoMaterial
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<TipoMaterial> getListaFabricanteSuggest(String sql){
		return super.getBusca("select o from TipoMaterial as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
