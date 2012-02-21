package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.entidade.TipoProfissional;
import br.com.ControleDispensacao.entidade.Usuario;
import br.com.ControleDispensacao.enums.TipoSituacaoEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;
import br.com.nucleo.utilidades.Utilities;

@ManagedBean(name="profissionalHome")
@SessionScoped
public class ProfissionalHome extends PadraoHome<Profissional>{
	private List<TipoProfissional> tipoProfissionalList;
	
	public ProfissionalHome() {
		getInstancia().setUsuario(new Usuario());
	}
	
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
		getInstancia().setUsuarioAlteracao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataAlteracao(new Date());
		return super.atualizar();
	}
	
	@Override
	public boolean enviar() {
		carregaDadosUsuario();
		if(new UsuarioHome().procurarUsuario(getInstancia().getUsuario().getLogin()) == null){
			getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			getInstancia().setDataInclusao(new Date());
			return super.enviar();
		}
		return false;
	}

	private void carregaDadosUsuario() {
		getInstancia().getUsuario().setDataInclusao(new Date());
		getInstancia().getUsuario().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().getUsuario().setSituacao(TipoSituacaoEnum.A);
		getInstancia().getUsuario().setSenha(Utilities.md5(getInstancia().getUsuario().getMatricula()));
	}

	public List<TipoProfissional> getTipoProfissionalList() {
		return tipoProfissionalList;
	}

	public void setTipoProfissionalList(List<TipoProfissional> tipoProfissionalList) {
		this.tipoProfissionalList = tipoProfissionalList;
	}
	
}
