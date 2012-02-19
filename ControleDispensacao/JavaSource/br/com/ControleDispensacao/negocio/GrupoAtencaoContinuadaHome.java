package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.GrupoAtencaoContinuada;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="grupoAtencaoContinuadaHome")
@SessionScoped
public class GrupoAtencaoContinuadaHome extends PadraoHome<GrupoAtencaoContinuada>{
	
	/**
	 * Método que retorna uma lista de Aplicacao
	 * @param String sql
	 * @return Collection Menu
	 */
	public Collection<GrupoAtencaoContinuada> getListaGrupoAtencaoContinuadaAutoComplete(String stringBusca){
		return super.getBusca("select o from GrupoAtencaoContinuada as o where o.descricao like '%"+stringBusca+"%' ");
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
