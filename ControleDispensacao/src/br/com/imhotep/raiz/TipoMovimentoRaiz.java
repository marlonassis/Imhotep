package br.com.imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.TipoMovimento;
import br.com.remendo.PadraoRaiz;

@ManagedBean(name="tipoMovimentoRaiz")
@SessionScoped
public class TipoMovimentoRaiz extends PadraoRaiz<TipoMovimento>{
	
	/**
	 * MŽtodo que retorna uma lista de TipoMovimento
	 * @param String sql
	 * @return Collection TipoMovimento
	 */
	public Collection<TipoMovimento> getListaTipoMovimentoAutoComplete(String sql){
		return super.getBusca("select o from TipoMovimento as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}
	
}
