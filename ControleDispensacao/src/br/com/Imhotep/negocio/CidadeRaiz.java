package br.com.Imhotep.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Cidade;
import br.com.remendo.PadraoHome;

@ManagedBean(name="cidadeRaiz")
@SessionScoped
public class CidadeRaiz extends PadraoHome<Cidade>{
	
	/**
	 * Método que retorna uma lista de Cidade
	 * @param String sql
	 * @return Collection Cidade
	 */
	public Collection<Cidade> getListaCidadeAutoComplete(String busca){
		return super.getBusca("select o from Cidade as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+busca+"%')) ");
	}
	
}
