package br.com.Imhotep.negocio;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.Imhotep.entidade.Especialidade;
import br.com.Imhotep.entidade.Profissional;
import br.com.Imhotep.entidade.Unidade;
import br.com.Imhotep.entidade.Usuario;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;
import br.com.remendo.utilidades.Utilities;

@ManagedBean(name="profissionalRaiz")
@SessionScoped
public class ProfissionalRaiz extends PadraoHome<Profissional>{
	
	private Unidade unidade;
	
	public ProfissionalRaiz() {
		novaInstancia();
	}
	
	public List<Especialidade> getListaEspecialidade(){
		if(getInstancia().getEspecialidade() !=  null && getInstancia().getEspecialidade().getTipoConselho() != null){
			return new EspecialidadeRaiz().listaEspecialidadePorTipoConselho(getInstancia().getEspecialidade().getTipoConselho().getIdTipoConselho());
		}else{
			return new EspecialidadeRaiz().listaEspecialidadePorTipoConselho(null);
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
		if(new UsuarioRaiz().procurarUsuario(getInstancia().getUsuario().getLogin()) == null){
			try{
				getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
				getInstancia().getUsuario().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			} catch (Exception e) {
				e.printStackTrace();
				super.mensagem("Erro ao pegar ao acessar o autenticador.", null, FacesMessage.SEVERITY_ERROR);
				System.out.print("Erro em ProfissionalHome");
			}
			getInstancia().setDataInclusao(new Date());
			getInstancia().getUsuario().setDataInclusao(new Date());
			getInstancia().getUsuario().setSenha(Utilities.encriptaParaMd5(String.valueOf(getInstancia().getMatricula())));
			if(super.enviar()){
				boolean exibeMensagemInsercao = false;
				new AutorizaUnidadeProfissionalRaiz().enviar(getInstancia(), getUnidade(), exibeMensagemInsercao);
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
