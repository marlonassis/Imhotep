package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.ListaEspecial;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="listaEspecialHome")
@SessionScoped
public class ListaEspecialHome extends PadraoHome<ListaEspecial>{
	
	/**
	 * Método que retorna uma lista de Aplicacao
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<ListaEspecial> getListaFabricanteSuggest(String sql){
		return super.getBusca("select o from ListaEspecial as o where o.descricao like '%"+sql+"%' ");
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
