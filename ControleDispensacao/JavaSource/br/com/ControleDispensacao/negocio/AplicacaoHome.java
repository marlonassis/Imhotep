package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Aplicacao;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="aplicacaoHome")
@SessionScoped
public class AplicacaoHome extends PadraoHome<Aplicacao>{
	
	/**
	 * Método que retorna uma lista de Aplicacao
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<Aplicacao> getListaAplicacaoSuggest(String sql){
		return super.getBusca("select o from Aplicacao as o where lower(o.descricao) like lower('%"+sql+"%') ");
	}
	
}
