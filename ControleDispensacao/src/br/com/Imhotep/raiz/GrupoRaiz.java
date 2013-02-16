package br.com.Imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Grupo;
import br.com.remendo.PadraoHome;

@ManagedBean(name="grupoRaiz")
@SessionScoped
public class GrupoRaiz extends PadraoHome<Grupo>{
	
	/**
	 * Método que retorna uma lista de Grupo
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<Grupo> getListaGrupoAutoComplete(String sql){
		return super.getBusca("select o from Grupo as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
