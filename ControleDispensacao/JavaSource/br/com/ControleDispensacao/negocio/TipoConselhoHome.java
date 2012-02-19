package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TipoConselho;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="tipoConselhoHome")
@SessionScoped
public class TipoConselhoHome extends PadraoHome<TipoConselho>{
	
	/**
	 * Método que retorna uma lista de TipoConselho de acordo com a sigla do conselho ou com a descricao
	 * @param String expressao
	 * @return Collection Menu
	 */
	public Collection<TipoConselho> getListaTipoConselhoAutoComplete(String expressao){
		return super.getBusca("select o from TipoConselho as o where o.descricao like lower('%"+expressao+"%') or o.sigla like lower('%"+expressao+"%') ");
	}
	
	@Override
	public boolean atualizar() {
		getInstancia().setUsuarioAlteracao(UsuarioHome.getUsuarioAtual());
		getInstancia().setDataAlteracao(new Date());
		return super.atualizar();
	}
	
	@Override
	public boolean enviar() {
		getInstancia().setUsuarioInclusao(UsuarioHome.getUsuarioAtual());
		getInstancia().setDataInclusao(new Date());
		return super.enviar();
	}
	
}
