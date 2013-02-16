package br.com.Imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Estado;
import br.com.remendo.PadraoHome;

@ManagedBean(name="estadoRaiz")
@SessionScoped
public class EstadoRaiz extends PadraoHome<Estado>{
	
	/**
	 * Método que retorna uma lista de Estado
	 * @param String sql
	 * @return Collection Estado
	 */
	public Collection<Estado> getListaCidadeAutoComplete(String busca){
		return super.getBusca("select o from Estado as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+busca+"%')) ");
	}
	
}
