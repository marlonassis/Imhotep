package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Grupo;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="grupoHome")
@SessionScoped
public class GrupoHome extends PadraoHome<Grupo>{
	
	/**
	 * Método que retorna uma lista de Grupo
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<Grupo> getListaGrupoAutoComplete(String sql){
		return super.getBusca("select o from Grupo as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
