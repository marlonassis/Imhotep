package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TipoConselho;
import br.com.remendo.PadraoHome;

@ManagedBean(name="tipoConselhoRaiz")
@SessionScoped
public class TipoConselhoRaiz extends PadraoHome<TipoConselho>{
	
	/**
	 * Método que retorna uma lista de TipoConselho de acordo com a sigla do conselho ou com a descricao
	 * @param String expressao
	 * @return Collection Menu
	 */
	public Collection<TipoConselho> getListaTipoConselhoAutoComplete(String expressao){
		return super.getBusca("select o from TipoConselho as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+expressao+"%')) or lower(o.sigla) like lower('%"+expressao+"%') ");
	}
	
}
