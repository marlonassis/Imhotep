package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Fabricante;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="fabricanteHome")
@SessionScoped
public class FabricanteHome extends PadraoHome<Fabricante>{
	
	/**
	 * Método que retorna uma lista de Fabricante
	 * @param String sql
	 * @return Collection Fabricante
	 */
	public Collection<Fabricante> getListaFabricanteAutoComplete(String expressaoConsulta){
		return super.getBusca("select o from Fabricante as o where o.descricao like '%"+expressaoConsulta+"%' ");
	}
	
	@Override
	public boolean atualizar() {
		getInstancia().setUsuarioAlteracao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataAlteracao(new Date());
		return super.atualizar();
	}
	
	@Override
	public boolean enviar() {
		getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataInclusao(new Date());
		return super.enviar();
	}
	
}
