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
import br.com.ControleDispensacao.entidade.Unidade;
import br.com.ControleDispensacao.entidade.Usuario;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;
import br.com.nucleo.utilidades.Utilities;

@ManagedBean(name="profissionalHome")
@SessionScoped
public class ProfissionalHome extends PadraoHome<Profissional>{
	
	private Unidade unidade;
	
	public ProfissionalHome() {
		novaInstancia();
	}
	
	public List<Especialidade> getListaEspecialidade(){
		if(getInstancia().getEspecialidade() !=  null && getInstancia().getEspecialidade().getTipoConselho() != null){
			return new EspecialidadeHome().listaEspecialidadePorTipoConselho(getInstancia().getEspecialidade().getTipoConselho().getIdTipoConselho());
		}else{
			return new EspecialidadeHome().listaEspecialidadePorTipoConselho(null);
		}
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		getInstancia().setUsuario(new Usuario());
		getInstancia().setEspecialidade(new Especialidade());
	}
	
	/**
	 * Método que retorna uma lista de Profissional
	 * @param String sql
	 * @return Collection Profissional
	 */
	public Collection<Profissional> getListaProfissionalAutoComplete(String consulta){
		return super.getBusca("select o from Profissional as o where lower(to_ascii(o.nome)) like lower(to_ascii('%"+consulta+"%')) ");
	}
	
	public boolean atualizar(Profissional profissional) {
		setInstancia(profissional);
		return super.atualizar();
	}
	
	@Override
	public boolean enviar() {
		if(new UsuarioHome().procurarUsuario(getInstancia().getUsuario().getLogin()) == null){
			getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			getInstancia().setDataInclusao(new Date());
			getInstancia().getUsuario().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			getInstancia().getUsuario().setDataInclusao(new Date());
			getInstancia().getUsuario().setSenha(Utilities.encriptaParaMd5(String.valueOf(getInstancia().getMatricula())));
			if(super.enviar()){
				boolean exibeMensagemInsercao = false;
				new AutorizaUnidadeProfissionalHome().enviar(getInstancia(), getUnidade(), exibeMensagemInsercao);
				return true;
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Este usuário já foi escolhido. informe outro login.", "Inserção não efetuada."));
		}
		return false;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

}
