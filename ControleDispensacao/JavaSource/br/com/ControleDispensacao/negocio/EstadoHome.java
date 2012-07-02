package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Estado;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="estadoHome")
@SessionScoped
public class EstadoHome extends PadraoHome<Estado>{
	
	/**
	 * Método que retorna uma lista de Estado
	 * @param String sql
	 * @return Collection Estado
	 */
	public Collection<Estado> getListaCidadeAutoComplete(String busca){
		return super.getBusca("select o from Estado as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+busca+"%')) ");
	}
	
}
