package br.com.imhotep.raiz;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.imhotep.consulta.raiz.EspecialidadeConsultaRaiz;
import br.com.imhotep.entidade.Especialidade;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.entidade.Usuario;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;
import br.com.remendo.utilidades.Utilities;

@ManagedBean
@SessionScoped
public class ProfissionalRaiz extends PadraoHome<Profissional>{
	
	private Unidade unidade;
	
	public ProfissionalRaiz() {
		novaInstancia();
	}
	
	public List<Especialidade> getListaEspecialidade(){
		if(getInstancia().getEspecialidade() !=  null && getInstancia().getEspecialidade().getTipoConselho() != null){
			return new EspecialidadeConsultaRaiz().listaEspecialidadePorTipoConselho(getInstancia().getEspecialidade().getTipoConselho().getIdTipoConselho());
		}else{
			return new EspecialidadeConsultaRaiz().listaEspecialidadePorTipoConselho(null);
		}
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		getInstancia().setUsuario(new Usuario());
		getInstancia().setEspecialidade(new Especialidade());
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
				super.mensagem("Erro ao acessar o autenticador.", null, FacesMessage.SEVERITY_ERROR);
				System.out.print("Erro em ProfissionalHome");
			}
			getInstancia().setDataInclusao(new Date());
			getInstancia().getUsuario().setDataInclusao(new Date());
			getInstancia().getUsuario().setSenha(Utilities.encriptaParaMd5(String.valueOf("123456")));
			if(super.enviar()){
				boolean exibeMensagemInsercao = false;
				boolean res = new AutorizaUnidadeProfissionalRaiz().enviar(getInstancia(), getUnidade(), exibeMensagemInsercao);
				return res;
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
