package br.com.imhotep.raiz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.entidade.Usuario;
import br.com.imhotep.enums.TipoUsuarioLogEnum;
import br.com.imhotep.excecoes.ExcecaoUsuarioDuplicado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;


@ManagedBean
@SessionScoped
public class UsuarioRaiz extends PadraoRaiz<Usuario> {

	private Usuario usuario = new Usuario();
	private String senhaConfirmacao;
	private boolean trocaSenha;
	private String senhaAntiga;
	private String senhaNova;
	private String senhaNovaConfirmacao;
	private String login;
	private boolean exibeCampoSenhaAntiga = true;
	
	public boolean usuarioBloqueado(Usuario linha){
		return linha.getQuantidadeErroLogin() >= Constantes.QUANTIDADE_BLOQUEIO_USUARIO;
	}
	
	public void apagarChave(){
		getInstancia().getProfissional().setChaveVerificacao(null);
		new ProfissionalRaiz().atualizar(getInstancia().getProfissional());
	}
	
	public static UsuarioRaiz getInstanciaAtual(){
		try {
			return (UsuarioRaiz) Utilitarios.procuraInstancia(UsuarioRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void procuraLogin() throws ExcecaoUsuarioDuplicado{
		List<Usuario> usuarios = super.getBusca("select o from Usuario o where o.login = '"+getInstancia().getLogin()+"'");
		if(usuarios.size() > 0){
			throw new ExcecaoUsuarioDuplicado();
		}
	}
	
	public void trocaLogin(){
		try {
			procuraLogin();
			setInstancia(Autenticador.getInstancia().getUsuarioAtual());
			getInstancia().setLogin(getLogin());
			super.atualizar();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ExcecaoUsuarioDuplicado e) {
			e.printStackTrace();
		}
		
	}
	
	public void resetarSenha(){
		getInstancia().setSenha(Utilitarios.encriptaParaMd5(Constantes.SENHA_PADRAO));
		super.atualizar();
	}
	
	public void resetarErro(){
		getInstancia().setQuantidadeErroLogin(0);
		new UsuarioAcessoLogRaiz().gerarLog(getInstancia(), TipoUsuarioLogEnum.D);
		super.atualizar();
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		senhaAntiga = null;
		senhaNova = null;
		senhaNovaConfirmacao = null;
	}
	
	public void trocaSenha(){
		Usuario usuario = null;
		try {
			usuario = Autenticador.getInstancia().getUsuarioAtual();
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o usu�rio atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em EstoqueCentroCirurgico");
		}
		String senhaCriptografada = Utilitarios.encriptaParaMd5(senhaAntiga);
		if(senhaNova.equals(senhaNovaConfirmacao)){
			if(new Autenticador().getUsuarioTrocarSenha() || usuario.getSenha().equals(senhaCriptografada)){
				String senha = Utilitarios.encriptaParaMd5(senhaNova);
				usuario.setSenha(senha);
				setInstancia(usuario);
				super.atualizar();
				novaInstancia();
				setExibeCampoSenhaAntiga(true);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"A senha antiga est� errada!", ""));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senhas n�o conferem!", "Informe duas senhas iguais."));
		}
	}
	
	@Override
	public boolean atualizar() {
		//procura se existe algum usu�rio com o mesmo login
		//se o usu�rio informou a senha de confirma��o ent�o devemos validar a senha
		if(trocaSenha && !getSenhaConfirmacao().equals("") && getSenhaConfirmacao() != null){
			if (getInstancia().getSenha().equals(getSenhaConfirmacao())){
				getInstancia().setSenha(Utilitarios.encriptaParaMd5(getInstancia().getSenha()));
				return super.atualizar();
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senhas n�o conferem!", "Informe duas senhas iguais."));
			}
		}else{
			return super.atualizar();
		}
		return false;
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
	
	public boolean enviar(Usuario usuario) {
		setInstancia(usuario);
		return super.enviar();
	}
	
	private Profissional getProfissionalAtual(){
		try {
			return Autenticador.getInstancia().getProfissionalAtual();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean enviarUsuarioPadrao(){
		getInstancia().setSenha(Utilitarios.encriptaParaMd5(Constantes.SENHA_PADRAO));
		getInstancia().setDataInclusao(new Date());
		getInstancia().setExpiraSessao(true);
		getInstancia().setProfissionalInclusao(getProfissionalAtual());
		if(super.enviar()){
			getInstancia().getProfissional().setUsuario(getInstancia());
			ProfissionalRaiz pr = new ProfissionalRaiz();
			pr.atualizar(getInstancia().getProfissional());
			return true;
		}
		return false;
	}
	
	@Override
	public boolean enviar() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			if(getInstancia().getSenha().equals(getSenhaConfirmacao())){
				if(procurarUsuario(getInstancia().getLogin()) == null){
					getInstancia().setSenha(Utilitarios.encriptaParaMd5(getInstancia().getSenha()));
					getInstancia().setDataInclusao(new Date());
					getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
					getInstancia().setQuantidadeErroLogin(0);
					if(super.enviar()){
						novaInstancia();
						return true;
					}else{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"N�o foi poss�vel cadastrar! Tente novamente.", "Cadastro n�o realizado!"));
					}
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Este login j� foi escolhido.", "Cadastro n�o realizado!"));
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senhas n�o conferem!", "Informe duas senhas iguais."));
			}
		}catch (Exception e) {
			e.printStackTrace();
			if(((Usuario)facesContext.getExternalContext().getSessionMap().get("usuario")) == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar!", "Cadastro n�o realizado!"));
				getInstancia().setIdUsuario(0);
			}
		}
		return false;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Usuario getUsuario() {
		return usuario;
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


	public String getSenhaAntiga() {
		return senhaAntiga;
	}


	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}


	public String getSenhaNova() {
		return senhaNova;
	}


	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}


	public String getSenhaNovaConfirmacao() {
		return senhaNovaConfirmacao;
	}


	public void setSenhaNovaConfirmacao(String senhaNovaConfirmacao) {
		this.senhaNovaConfirmacao = senhaNovaConfirmacao;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean getExibeCampoSenhaAntiga() {
		return exibeCampoSenhaAntiga;
	}

	public void setExibeCampoSenhaAntiga(boolean exibeCampoSenhaAntiga) {
		this.exibeCampoSenhaAntiga = exibeCampoSenhaAntiga;
	}
	

}
