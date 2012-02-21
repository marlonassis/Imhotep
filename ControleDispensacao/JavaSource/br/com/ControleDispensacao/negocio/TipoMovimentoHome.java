package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TipoMovimento;
import br.com.ControleDispensacao.seguranca.Autenticador;
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
		return super.getBusca("select o from TipoMovimento as o where o.descricao like '%"+sql+"%' ");
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
