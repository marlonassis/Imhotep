package br.com.ControleDispensacao.negocio;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.ControleDispensacao.entidade.Usuario;
import br.com.nucleo.PadraoHome;
import br.com.nucleo.utilidades.Utilities;


@ManagedBean(name="usuarioHome")
@SessionScoped
public class UsuarioHome extends PadraoHome<Usuario> {

	private static final String PAGINA_LOGIN = "/ControleDispensacao/PaginasWeb/login.jsf";
	private static final String PAGINA_HOME = "/ControleDispensacao/PaginasWeb/home.jsf";
	private Usuario usuario = new Usuario();
	private String senhaConfirmacao;
	private boolean trocaSenha;
	private static Usuario usuarioAtual = new Usuario();
	
	@Override
	public boolean atualizar() {
		//procura se existe algum usuário com o mesmo login
		//se o usuário informou a senha de confirmação então devemos validar a senha
		if(!getSenhaConfirmacao().equals("") && getSenhaConfirmacao() != null){
			if (getInstancia().getSenha().equals(getSenhaConfirmacao())){
				getInstancia().setSenha(Utilities.md5(getInstancia().getSenha()));
				return super.atualizar();
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senhas não conferem!", "Informe duas senhas iguais."));
			}
		}else{
			return super.atualizar();
		}
		return false;
	}
	
	@Override
	public boolean enviar() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			if(getInstancia().getSenha().equals(getSenhaConfirmacao())){
				if(procurarUsuario(getInstancia().getLogin()) == null){
					getInstancia().setSenha(Utilities.md5(getInstancia().getSenha()));
					getInstancia().setDataInclusao(new Date());
					getInstancia().setUsuarioInclusao(UsuarioHome.getUsuarioAtual());
					if(super.enviar()){
						novaInstancia();
						return true;
					}else{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não foi possível cadastrar! Tente novamente.", "Cadastro não realizado!"));
					}
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Este login já foi escolhido.", "Cadastro não realizado!"));
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senhas não conferem!", "Informe duas senhas iguais."));
			}
		}catch (Exception e) {
			e.printStackTrace();
			if(((Usuario)facesContext.getExternalContext().getSessionMap().get("usuario")) == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar!", "Cadastro não realizado!"));
				getInstancia().setIdUsuario(0);
			}
		}
		return false;
	}

	public void logout(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			setInstancia(new Usuario());
			facesContext.getExternalContext().getSessionMap().put("usuario", null);
			UsuarioHome.setUsuarioAtual(null);
			
			facesContext.getExternalContext().redirect(PAGINA_LOGIN);
		}catch (Exception e) {
			e.printStackTrace();
			if(((Usuario) facesContext.getExternalContext().getSessionMap().get("usuario")) != null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao tentar sair! Tente sair novamente.", "Login não realizado!"));
			}
		}
	}
	
	public boolean isUsuarioAtivo(){
		return UsuarioHome.getUsuarioAtual() != null && UsuarioHome.getUsuarioAtual().getIdUsuario() != 0 ? true : false;
	}
	
	public void logarUsuario(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			if(!usuario.getNome().isEmpty()){
	    		Usuario usuarioLogado = procurarUsuario(usuario.getNome());
	    		if(usuarioLogado != null && usuarioLogado.getIdUsuario() != 0){
	    			if(usuarioLogado.getSenha().equals(Utilities.md5(usuario.getSenha()))){
	    				setInstancia(usuarioLogado);
	    				facesContext.getExternalContext().getSessionMap().put("usuario", usuarioLogado);
	    				UsuarioHome.setUsuarioAtual(usuarioLogado);
	    				facesContext.getExternalContext().redirect(PAGINA_HOME);
	    				novaInstancia();
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
		usuario= new Usuario();
	}
	
	public Usuario procurarUsuario(String nome){
		List<Usuario> resultado = getBusca("from Usuario o where o.nome = '"+nome+"'");
		if(resultado.size() > 0){
			return resultado.get(0);
		}
		return null;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Usuario getUsuario() {
		return usuario;
	}
	
	public static void setUsuarioAtual(Usuario usuarioAtual) {
		UsuarioHome.usuarioAtual = usuarioAtual;
	}

	public static Usuario getUsuarioAtual() {
		return usuarioAtual;
	}

	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}

	public boolean isTrocaSenha() {
		return trocaSenha;
	}

	public void setTrocaSenha(boolean trocaSenha) {
		this.trocaSenha = trocaSenha;
	}
	

}
