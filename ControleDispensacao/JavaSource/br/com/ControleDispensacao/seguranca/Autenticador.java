package br.com.ControleDispensacao.seguranca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.ControleDispensacao.auxiliar.ControleMenu;
import br.com.ControleDispensacao.entidade.Menu;
import br.com.ControleDispensacao.entidade.Painel;
import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.entidade.Unidade;
import br.com.ControleDispensacao.entidade.Usuario;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.utilidades.Utilities;

@ManagedBean(name="autenticador")
@SessionScoped
public class Autenticador {
	
	private Usuario usuarioAtual;
	private Usuario usuario = new Usuario();
	private Unidade unidadeAtual;
	private Profissional profissionalAtual;
	private boolean mostraComboUnidade;
	private Collection<Unidade> unidades;
	private static final String PAGINA_LOGIN = "/ControleDispensacao/PaginasWeb/login.jsf";
	private static final String PAGINA_HOME = "/ControleDispensacao/PaginasWeb/home.jsf";
	private Collection<Painel> paineisUsuario;

	public static Autenticador getInstancia(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
		if(session != null){
			return (Autenticador) session.getAttribute("autenticador");
		}else{
			return null;
		}
	}
	
	private void carregaUnidadesUsuario(){
		ConsultaGeral<Unidade> cg = new ConsultaGeral<Unidade>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idUsuario", usuarioAtual.getIdUsuario());
		unidades = cg.consulta(new StringBuilder("select a.unidade from AutorizaUnidadeProfissional a where a.profissional.usuario.idUsuario = :idUsuario"), hm);
		carregaUnidadeAtual();
	}

	private void carregaUnidadeAtual() {
		if(unidades.size() == 1){
			unidadeAtual = unidades.iterator().next();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(PAGINA_HOME);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			mostraComboUnidade = true;
		}
	}

	private void carregaProfissional(){
		ConsultaGeral<Profissional> cg = new ConsultaGeral<Profissional>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idUsuario", usuarioAtual.getIdUsuario());
		profissionalAtual = cg.consultaUnica(new StringBuilder("select o from Profissional o where o.usuario.idUsuario = :idUsuario"), hm);
	}
	
	public void logout(){
		try{
			setUsuarioAtual(new Usuario());
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
			session.invalidate();
			setUsuarioAtual(null);
			setUnidadeAtual(null);
			unidades = null;
		}catch (Exception e) {
			e.printStackTrace();
			if(getUsuarioAtual() != null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao tentar sair! Tente sair novamente.", "Login não realizado!"));
			}
		}
	}
	
	public boolean isUsuarioAtivo(){
		return getUsuarioAtual() != null && getUsuarioAtual().getIdUsuario() != 0 ? true : false;
	}
	
	public Usuario procurarUsuario(String nome){
		ConsultaGeral<Usuario> cg = new ConsultaGeral<Usuario>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("login", nome.trim());
		Usuario resultado = cg.consultaUnica(new StringBuilder("select o from Usuario o where o.login = :login"), hm);
		if(resultado != null){
			return resultado;
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuário ou senha não confere!", "Usuário não encontrado!"));
			return null;
		}
	}

	public void continuaLogin(){
		if(unidadeAtual != null){
			try {
				mostraComboUnidade = false;
				FacesContext.getCurrentInstance().getExternalContext().redirect(PAGINA_HOME);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe uma unidade.", ""));
		}
	}
	
	public boolean verificaSenha(Usuario usuarioLogado, String senha){
		if(usuarioLogado.getSenha().equals(Utilities.md5(senha))){
			return true;
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuário ou senha não confere!", "Login não realizado!"));
			return false;
		}
	}
	
	public void logarUsuario(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			if(!getUsuario().getLogin().isEmpty()){
	    		Usuario usuarioLogado = procurarUsuario(getUsuario().getLogin());
	    		if(usuarioLogado != null && usuarioLogado.getIdUsuario() != 0){
	    			if(verificaSenha(usuarioLogado, getUsuario().getSenha())){
	    				setUsuarioAtual(usuarioLogado);
	    				facesContext.getExternalContext().getSessionMap().put("usuario", usuarioLogado);
	    				setUsuarioAtual(usuarioLogado);
	    				carregaUnidadesUsuario();
	    				carregaProfissional();
	    				carregaToolBarMenu();
	    				carregaPaineis();
	    			}
	    		}
	    	}
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro desconhecido!",  null));
			if(((Usuario)facesContext.getExternalContext().getSessionMap().get("usuario")) == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao logar! Você não está logado.", "Tente logar novamente!"));
			}else{
				try{ 
					facesContext.getExternalContext().redirect(PAGINA_HOME);
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		setUsuario(new Usuario());
	}
	
	private void carregaPaineis() {
		ConsultaGeral<Painel> cg = new ConsultaGeral<Painel>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idEspecialidade", Autenticador.getInstancia().getProfissionalAtual().getEspecialidade().getIdEspecialidade());
		StringBuilder sb = new StringBuilder("select o.painel from AutorizaPainel o where o.especialidade.idEspecialidade = :idEspecialidade)");
		
		paineisUsuario = cg.consulta(sb, hashMap);
	}

	/**
	 * Monta o menu de acordo com as permissões de cada usuário
	 */
	private void carregaToolBarMenu() {
		try {
			//carrega o menu que pertence ao usuário
			String hql = "select o.menu from AutorizaMenu o where o.especialidade.idEspecialidade = :idEspecialidade";
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idEspecialidade", getProfissionalAtual().getEspecialidade().getIdEspecialidade());
			ControleMenu controleMenu = new ControleMenu();
			controleMenu.setMenuAutorizadoList(new ArrayList<Menu>(new ConsultaGeral<Menu>().consulta(new StringBuilder(hql), hm)));
			//após carregar o menu é chamado o método converteMenuString para converter todo o menu em uma lista de string
			controleMenu.converteMenuString();
			Utilities.atualizaInstancia(controleMenu);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public Usuario getUsuarioAtual() {
		return usuarioAtual;
	}

	public void setUsuarioAtual(Usuario usuarioAtual) {
		this.usuarioAtual = usuarioAtual;
	}

	public Unidade getUnidadeAtual() {
		return unidadeAtual;
	}

	public void setUnidadeAtual(Unidade unidadeAtual) {
		this.unidadeAtual = unidadeAtual;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Collection<Unidade> getUnidades() {
		return unidades;
	}

	public Profissional getProfissionalAtual() {
		return profissionalAtual;
	}

	public void setProfissionalAtual(Profissional profissionalAtual) {
		this.profissionalAtual = profissionalAtual;
	}

	public boolean isMostraComboUnidade() {
		return mostraComboUnidade;
	}

	public void setMostraComboUnidade(boolean mostraComboUnidade) {
		this.mostraComboUnidade = mostraComboUnidade;
	}

	public List<Painel> getListaPaineisUsuario(){
		return paineisUsuario == null ? null : new ArrayList<Painel>(paineisUsuario);
	}
	
}
