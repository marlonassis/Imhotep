package br.com.Imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.UnidadeMaterial;
import br.com.remendo.PadraoHome;

@ManagedBean(name="unidadeMaterialRaiz")
@SessionScoped
public class UnidadeMaterialRaiz extends PadraoHome<UnidadeMaterial>{
	
	/**
	 * Método que retorna uma lista de UnidadeMaterial
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<UnidadeMaterial> getListaAplicacaoSuggest(String sql){
		return super.getBusca("select o from UnidadeMaterial as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
