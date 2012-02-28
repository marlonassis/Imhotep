package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.ControleDispensacao.entidade.Especialidade;
import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.entidade.TipoConselho;
import br.com.ControleDispensacao.entidade.TipoProfissional;
import br.com.ControleDispensacao.entidade.Usuario;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;
import br.com.nucleo.utilidades.Utilities;

@ManagedBean(name="profissionalHome")
@SessionScoped
public class ProfissionalHome extends PadraoHome<Profissional>{
	private List<TipoProfissional> tipoProfissionalList;
	private List<Especialidade> especialidadeList;
	
	public ProfissionalHome() {
		getInstancia().setUsuario(new Usuario());
		getInstancia().setEspecialidade(new Especialidade());
		getInstancia().getEspecialidade().setTipoProfissional(new TipoProfissional());
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		getInstancia().setUsuario(new Usuario());
		getInstancia().setEspecialidade(new Especialidade());
		getInstancia().getEspecialidade().setTipoProfissional(new TipoProfissional());
	}
	
	/**
	 * Método usando para carregar o tipo do profissional informado pelo usuário de acordo com o conselho
	 */
	public void carregaTipoConselhoList(){
		TipoConselho tipoConselho = getInstancia().getEspecialidade().getTipoProfissional().getTipoConselho();
		if(tipoConselho != null){
			setTipoProfissionalList((List<TipoProfissional>) new TipoProfissionalHome().getListaTipoProfissionalConselho(tipoConselho.getIdTipoConselho()));
		}
	}
	
	public void carregaEspecialidadeList(){
		TipoProfissional tipoProfissional = getInstancia().getEspecialidade().getTipoProfissional();
		if(tipoProfissional != null ){
			setEspecialidadeList((List<Especialidade>) new EspecialidadeHome().getListaEspecialidadeTipoConselho(tipoProfissional.getIdTipoProfissional()));
		}
	}
	
	@Override
	public void setInstancia(Profissional instancia) {
		super.setInstancia(instancia);
		carregaTipoConselhoList();
		carregaEspecialidadeList();
	}
	
	/**
	 * Método que retorna uma lista de Profissional
	 * @param String sql
	 * @return Collection Profissional
	 */
	public Collection<Profissional> getListaProfissionalAutoComplete(String consulta){
		return super.getBusca("select o from Profissional as o where lower(o.nome) like lower('%"+consulta+"%') ");
	}
	
	@Override
	public boolean enviar() {
		carregaDadosUsuario();
		if(new UsuarioHome().procurarUsuario(getInstancia().getUsuario().getLogin()) == null){
			getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			getInstancia().setDataInclusao(new Date());
			return super.enviar();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Este usuário já foi escolhido. informe outro login.", "Inserção não efetuada."));
		}
		return false;
	}

	private void carregaDadosUsuario() {
		getInstancia().getUsuario().setDataInclusao(new Date());
		getInstancia().getUsuario().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().getUsuario().setSenha(Utilities.md5(getInstancia().getUsuario().getMatricula()));
	}

	public List<TipoProfissional> getTipoProfissionalList() {
		return tipoProfissionalList;
	}

	public void setTipoProfissionalList(List<TipoProfissional> tipoProfissionalList) {
		this.tipoProfissionalList = tipoProfissionalList;
	}

	public List<Especialidade> getEspecialidadeList() {
		return especialidadeList;
	}

	public void setEspecialidadeList(List<Especialidade> especialidadeList) {
		this.especialidadeList = especialidadeList;
	}
	
}
