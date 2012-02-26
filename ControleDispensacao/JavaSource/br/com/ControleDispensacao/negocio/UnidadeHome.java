package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Unidade;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="unidadeHome")
@SessionScoped
public class UnidadeHome extends PadraoHome<Unidade>{
	
	/**
	 * Método que retorna uma lista de Unidade
	 * @param String sql
	 * @return Collection Unidade
	 */
	public Collection<Unidade> getListaUnidadeAutoComplete(String nomeSigla){
		return super.getBusca("select o from Unidade as o where o.nome like '%"+nomeSigla+"%' or o.sigla like '%"+nomeSigla+"%'  ");
	}
	
}
