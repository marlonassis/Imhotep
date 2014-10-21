package br.com.imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.TipoConselho;
import br.com.remendo.PadraoRaiz;

@ManagedBean(name="tipoConselhoRaiz")
@SessionScoped
public class TipoConselhoRaiz extends PadraoRaiz<TipoConselho>{
	
	/**
	 * MŽtodo que retorna uma lista de TipoConselho de acordo com a sigla do conselho ou com a descricao
	 * @param String expressao
	 * @return Collection Menu
	 */
	public Collection<TipoConselho> getListaTipoConselhoAutoComplete(String expressao){
		return super.getBusca("select o from TipoConselho as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+expressao+"%')) or lower(o.sigla) like lower('%"+expressao+"%') ");
	}
	
}
