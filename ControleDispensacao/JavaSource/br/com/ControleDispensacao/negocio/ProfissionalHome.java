package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.entidade.TipoProfissional;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="profissionalHome")
@SessionScoped
public class ProfissionalHome extends PadraoHome<Profissional>{
	private List<TipoProfissional> tipoProfissionalList;
	
	/**
	 * Método usando para carregar o tipo do profissional informado pelo usuário de acordo com o conselho
	 */
	public void carregaTipoConselhoList(){
		if(getInstancia().getTipoConselho() != null){
			setTipoProfissionalList((List<TipoProfissional>) new TipoProfissionalHome().getListaTipoProfissionalConselho(getInstancia().getTipoConselho().getIdTipoConselho()));
		}
	}
	
	@Override
	public void setInstancia(Profissional instancia) {
		super.setInstancia(instancia);
		carregaTipoConselhoList();
	}
	
	/**
	 * Método que retorna uma lista de Profissional
	 * @param String sql
	 * @return Collection Profissional
	 */
	public Collection<Profissional> getListaProfissionalAutoComplete(String consulta){
		return super.getBusca("select o from Profissional as o where o.nome like '%"+consulta+"%' ");
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

	public List<TipoProfissional> getTipoProfissionalList() {
		return tipoProfissionalList;
	}

	public void setTipoProfissionalList(List<TipoProfissional> tipoProfissionalList) {
		this.tipoProfissionalList = tipoProfissionalList;
	}
	
}
