package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.UnidadeMaterial;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="unidadeMaterialHome")
@SessionScoped
public class UnidadeMaterialHome extends PadraoHome<UnidadeMaterial>{
	
	/**
	 * Método que retorna uma lista de Aplicacao
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<UnidadeMaterial> getListaAplicacaoSuggest(String sql){
		return super.getBusca("select o from UnidadeMaterial as o where o.descricao like '%"+sql+"%' ");
	}
	
}
