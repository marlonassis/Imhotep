package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TipoMovimento;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="tipoMovimentoHome")
@SessionScoped
public class TipoMovimentoHome extends PadraoHome<TipoMovimento>{
	
	/**
	 * Método que retorna uma lista de TipoMovimento
	 * @param String sql
	 * @return Collection TipoMovimento
	 */
	public Collection<TipoMovimento> getListaTipoMovimentoAutoComplete(String sql){
		return super.getBusca("select o from TipoMovimento as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
