package br.com.Imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.TipoMovimento;
import br.com.remendo.PadraoHome;

@ManagedBean(name="tipoMovimentoRaiz")
@SessionScoped
public class TipoMovimentoRaiz extends PadraoHome<TipoMovimento>{
	
	/**
	 * Método que retorna uma lista de TipoMovimento
	 * @param String sql
	 * @return Collection TipoMovimento
	 */
	public Collection<TipoMovimento> getListaTipoMovimentoAutoComplete(String sql){
		return super.getBusca("select o from TipoMovimento as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
