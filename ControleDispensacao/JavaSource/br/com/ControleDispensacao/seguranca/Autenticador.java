package br.com.ControleDispensacao.seguranca;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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
	private Collection<Unidade> unidades;
	private static final String PAGINA_LOGIN = "/ControleDispensacao/PaginasWeb/login.jsf";
	private static final String PAGINA_HOME = "/ControleDispensacao/PaginasWeb/home.jsf";

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
	}
	
	public void logout(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			setUsuarioAtual(new Usuario());
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);  
			session.invalidate();
			setUsuarioAtual(null);
			setUnidadeAtual(null);
			unidades = null;
			facesContext.getExternalContext().redirect(PAGINA_LOGIN);
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
		hm.put("login", nome);
		List<Usuario> resultado = (List<Usuario>) cg.consulta(new StringBuilder("select o from Usuario o where o.login = :login"), hm);
		if(resultado.size() > 0){
			return resultado.get(0);
		}
		return null;
	}
	
	public void logarUsuario(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			if(!getUsuario().getLogin().isEmpty()){
	    		Usuario usuarioLogado = procurarUsuario(getUsuario().getLogin());
	    		if(usuarioLogado != null && usuarioLogado.getIdUsuario() != 0){
	    			if(usuarioLogado.getSenha().equals(Utilities.md5(getUsuario().getSenha()))){
	    				setUsuarioAtual(usuarioLogado);
	    				facesContext.getExternalContext().getSessionMap().put("usuario", usuarioLogado);
	    				setUsuarioAtual(usuarioLogado);
	    				carregaUnidadesUsuario();
	    				facesContext.getExternalContext().redirect(PAGINA_HOME);
	    			}else{
	    				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuário ou senha não confere!", "Login não realizado!"));
	    			}
	    		}else{
	    			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuário ou senha não confere!", "Login não realizado!"));
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

}
