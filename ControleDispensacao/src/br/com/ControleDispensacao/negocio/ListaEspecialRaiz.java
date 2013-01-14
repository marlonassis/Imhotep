package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.ListaEspecial;
import br.com.remendo.PadraoHome;

@ManagedBean(name="listaEspecialRaiz")
@SessionScoped
public class ListaEspecialRaiz extends PadraoHome<ListaEspecial>{
	
	/**
	 * Método que retorna uma lista de ListaEspecial
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<ListaEspecial> getListaFabricanteSuggest(String sql){
		return super.getBusca("select o from ListaEspecial as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
