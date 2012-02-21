package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Aplicacao;
import br.com.ControleDispensacao.seguranca.Autenticador;
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
		return super.getBusca("select o from Aplicacao as o where o.descricao like '%"+sql+"%' ");
	}
	
	@Override
	public boolean atualizar() {
		getInstancia().setUsuarioAlteracao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataAlteracao(new Date());
		return super.atualizar();
	}
	
	@Override
	public boolean enviar() {
		getInstancia().setMostrarRespostaOperacao(true);
		getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataInclusao(new Date());
		return super.enviar();
	}
	
}
