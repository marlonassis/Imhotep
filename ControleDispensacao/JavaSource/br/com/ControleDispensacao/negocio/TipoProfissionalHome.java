package br.com.ControleDispensacao.negocio;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TipoProfissional;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="tipoProfissionalHome")
@SessionScoped
public class TipoProfissionalHome extends PadraoHome<TipoProfissional>{
	
	/**
	 * Método que retorna uma lista de TipoProfissional
	 * @param String sql
	 * @return Collection TipoProfissional
	 */
	public Collection<TipoProfissional> getListaTipoProfissionalAutoComplete(String sql){
		return super.getBusca("select o from TipoProfissional as o where o.descricao like '%"+sql+"%' ");
	}
	
	/**
	 * Método que retorna uma lista de TipoProfissional de acordo com o conselho do profissional
	 * @param String sql
	 * @return Collection TipoProfissional
	 */
	public Collection<TipoProfissional> getListaTipoProfissionalConselho(Integer id){
		return super.getBusca("select o from TipoProfissional as o where o.tipoConselho.idTipoConselho = "+id);
	}

	/**
	 * Método que retorna uma lista de TipoProfissional para os profissionais que não possuem conselho
	 * @param String sql
	 * @return Collection TipoProfissional
	 */
	public Collection<TipoProfissional> getListaTipoProfissionalConselho(){
		return super.getBusca("select o from TipoProfissional as o where o.tipoConselho is null ");
	}
	
}
