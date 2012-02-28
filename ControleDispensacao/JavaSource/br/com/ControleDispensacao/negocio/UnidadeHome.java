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
		return super.getBusca("select o from Unidade as o where lower(o.nome) like lower('%"+nomeSigla+"%') or lower(o.sigla) like lower('%"+nomeSigla+"%')  ");
	}
	
}
