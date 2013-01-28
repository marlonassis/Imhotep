package br.com.Imhotep.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Unidade;
import br.com.remendo.PadraoHome;

@ManagedBean(name="unidadeRaiz")
@SessionScoped
public class UnidadeRaiz extends PadraoHome<Unidade>{
	
	/**
	 * Método que retorna uma lista de Unidade
	 * @param String sql
	 * @return Collection Unidade
	 */
	public Collection<Unidade> getListaUnidadeAutoComplete(String nomeSigla){
		return super.getBusca("select o from Unidade as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+nomeSigla+"%')) or lower(o.sigla) like lower('%"+nomeSigla+"%')  ");
	}
	
}
