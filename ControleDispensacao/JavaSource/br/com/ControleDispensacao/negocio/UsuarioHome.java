package br.com.ControleDispensacao.negocio;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
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
//	private String senhaAntiga;
//	private String senhaNova;
//	private String senhaNovaConfirmacao;
//	private String perguntaSecreta;
//	private String respostaSecreta;
//	private boolean achouUsuario = false;
//	private boolean respostaConfere = false;
//	private Usuario usuarioTemp;
	private static Usuario usuarioAtual = new Usuario();
//	
//	public void limpaRecuperacaoSenha(){
//		usuario = new Usuario();
//		senhaAntiga = null;
//		senhaNova = null;
//		senhaNovaConfirmacao = null;
//		perguntaSecreta = null;
//		respostaSecreta = null;
//		achouUsuario = false;
//		respostaConfere = false;
//		usuarioTemp = new Usuario();
//	}
//	
//	public void verificaRespostaSecreta(){
//		respostaConfere = Utilities.encripta(respostaSecreta.trim()).equals(usuarioTemp.getRespostaSecreta().trim());
//		if(!respostaConfere){
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Resposta secreta não confere.", "Não confere!"));
//		}
//	}
//	
//	public void procuraUsuario(){
//		List<Usuario> usuList = getBusca("select o from Usuario o where o.nome = '"+usuario.getNome()+"'");
//		achouUsuario = !usuList.isEmpty();
//		if(achouUsuario){
//			usuarioTemp = usuList.get(0);
//			perguntaSecreta = usuarioTemp.getPerguntaSecreta();
//		}else{
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuário não encontrado.", "Não encontrado!"));
//		}
//	}
//	
//	public boolean atualizarRespostaSecreta() {
//		if(getInstancia().getSenha().equals(Utilities.encripta(senhaAntiga))){
//			getInstancia().setRespostaSecreta(Utilities.encripta(respostaSecreta));
//			respostaSecreta = null;
//			return super.atualizar();
//		}else{
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"A senha informada não confere com a senha de cadastro do usuário.", "Senha não alterada!"));
//			return false;
//		}
//	}
//	
//	public void trocaSenhaRecuperacao(){
//		setInstancia(usuarioTemp);
//		trocaSenha();
//		limpaRecuperacaoSenha();
//		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sua senha foi alterada.", "Senha alterada!"));
//	}
//	
//	public void trocaSenha(){
//		if(senhaNova != null && senhaNovaConfirmacao != null && senhaNova.equals(senhaNovaConfirmacao)){
//			if(respostaConfere || getInstancia().getSenha().equals(Utilities.encripta(senhaAntiga))){
//				FacesContext facesContext = FacesContext.getCurrentInstance();
//				getInstancia().setSenha(Utilities.encripta(senhaNova));
//				atualizar();
//				facesContext.getExternalContext().getSessionMap().put("usuario", getInstancia());
//				setInstancia(new Usuario());
//				senhaAntiga = null;
//				senhaNova = null;
//				senhaNovaConfirmacao = null;
//			}else{
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"A senha informada não confere com a senha de cadastro do usuário.", "Senha não alterada!"));
//			}
//		}else{
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"As novas senhas não conferem.", "Troca de senha não realizada!"));
//		}
//	}
	
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
	
//	private boolean achouUsuario() {
//		return getBusca("select o from Usuario o where o.nome = '"+usuario.getNome()+"'").size() > 0;
//	}

	public void logout(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			setInstancia(new Usuario());
			facesContext.getExternalContext().getSessionMap().put("usuario", null);
			UsuarioHome.setUsuarioAtual(null);
			
//			FacesContext.getCurrentInstance().getExternalContext().redirect(PAGINA_LOGIN);
			
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
	
//	/**
//	 * Método que retorna uma lista de clientes de acordo com o cpf ou cnpj
//	 * @param String sql
//	 * @return Collection Cliente
//	 */
//	public Collection<Usuario> getListaUsuarioSuggest(String sql){
//		return super.getBusca("select o from Usuario as o where lower(o.nome) like lower('%"+sql+"%') ");
//	}
//	
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
//
//	public void setSenhaNova(String senhaNova) {
//		this.senhaNova = senhaNova;
//	}
//
//	public String getSenhaNova() {
//		return senhaNova;
//	}
//
//	public void setSenhaNovaConfirmacao(String senhaNovaConfirmacao) {
//		this.senhaNovaConfirmacao = senhaNovaConfirmacao;
//	}
//
//	public String getSenhaNovaConfirmacao() {
//		return senhaNovaConfirmacao;
//	}
//
//	public void setSenhaAntiga(String senhaAntiga) {
//		this.senhaAntiga = senhaAntiga;
//	}
//
//	public String getSenhaAntiga() {
//		return senhaAntiga;
//	}
//
//	public void setRespostaSecreta(String respostaSecreta) {
//		this.respostaSecreta = respostaSecreta;
//	}
//
//	public String getRespostaSecreta() {
//		return respostaSecreta;
//	}
//
//	public void setAchouUsuario(boolean achouUsuario) {
//		this.achouUsuario = achouUsuario;
//	}
//
//	public boolean isAchouUsuario() {
//		return achouUsuario;
//	}
//
//	public void setPerguntaSecreta(String perguntaSecreta) {
//		this.perguntaSecreta = perguntaSecreta;
//	}
//
//	public String getPerguntaSecreta() {
//		return perguntaSecreta;
//	}
//
//	public void setRespostaConfere(boolean respostaConfere) {
//		this.respostaConfere = respostaConfere;
//	}
//
//	public boolean isRespostaConfere() {
//		return respostaConfere;
//	}

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
