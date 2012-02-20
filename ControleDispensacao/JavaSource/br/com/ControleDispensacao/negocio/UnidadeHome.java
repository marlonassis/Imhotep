package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Unidade;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="unidadeHome")
@SessionScoped
public class UnidadeHome extends PadraoHome<Unidade>{
	
	/**
	 * Método que retorna uma lista de Unidade
	 * @param String sql
	 * @return Collection Unidade
	 */
	public Collection<Unidade> getListaUnidadeAutoComplete(String nomeSigla){
		return super.getBusca("select o from Unidade as o where o.nome like '%"+nomeSigla+"%' or o.sigla like '%"+nomeSigla+"%'  ");
	}
	
	@Override
	public boolean atualizar() {
		getInstancia().setUsuarioAlteracao(Autenticador.getUsuarioAtual());
		getInstancia().setDataAlteracao(new Date());
		return super.atualizar();
	}
	
	@Override
	public boolean enviar() {
		getInstancia().setUsuarioInclusao(Autenticador.getUsuarioAtual());
		getInstancia().setDataInclusao(new Date());
		return super.enviar();
	}
	
}
