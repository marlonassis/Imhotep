package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Cidade;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="cidadeHome")
@SessionScoped
public class CidadeHome extends PadraoHome<Cidade>{
	
	/**
	 * Método que retorna uma lista de Cidade
	 * @param String sql
	 * @return Collection Cidade
	 */
	public Collection<Cidade> getListaCidadeAutoComplete(String busca){
		return super.getBusca("select o from Cidade as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+busca+"%')) ");
	}
	
}
