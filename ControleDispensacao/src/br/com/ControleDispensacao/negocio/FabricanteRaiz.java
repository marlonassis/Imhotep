package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Fabricante;
import br.com.remendo.PadraoHome;

@ManagedBean(name="fabricanteRaiz")
@SessionScoped
public class FabricanteRaiz extends PadraoHome<Fabricante>{
	
	/**
	 * Método que retorna uma lista de Fabricante
	 * @param String sql
	 * @return Collection Fabricante
	 */
	public Collection<Fabricante> getListaFabricanteAutoComplete(String expressaoConsulta){
		return super.getBusca("select o from Fabricante as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+expressaoConsulta+"%')) ");
	}
	
}
