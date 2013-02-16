package br.com.Imhotep.raiz;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.Imhotep.entidade.Usuario;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;
import br.com.remendo.utilidades.Utilities;


@ManagedBean(name="usuarioRaiz")
@SessionScoped
public class UsuarioRaiz extends PadraoHome<Usuario> {

	private Usuario usuario = new Usuario();
	private String senhaConfirmacao;
	private boolean trocaSenha;
	private String senhaAntiga;
	private String senhaNova;
	private String senhaNovaConfirmacao;
	
	public void resetarSenha(){
		getInstancia().setSenha(Utilities.encriptaParaMd5("123456"));
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
			super.mensagem("Erro ao pegar o usuário atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em EstoqueCentroCirurgico");
		}
		String senhaCriptografada = Utilities.encriptaParaMd5(senhaAntiga);
		if(senhaNova.equals(senhaNovaConfirmacao)){
			if(usuario.getSenha().equals(senhaCriptografada)){
				String senha = Utilities.encriptaParaMd5(senhaNova);
				usuario.setSenha(senha);
				setInstancia(usuario);
				super.atualizar();
				novaInstancia();
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"A senha antiga está errada!", ""));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senhas não conferem!", "Informe duas senhas iguais."));
		}
	}
	
	/**
	 * Método que retorna uma lista de Usuarios de acordo com o nome informado
	 * @param String sql
	 * @return Collection Usuario
	 */
	public Collection<Usuario> getListaUsuarioAutoComplete(String sql){
		return super.getBusca("select o from Usuario as o where lower(to_ascii(o.login)) like lower(to_ascii('%"+sql+"%')) ");
	}

	
	@Override
	public boolean atualizar() {
		//procura se existe algum usuário com o mesmo login
		//se o usuário informou a senha de confirmação então devemos validar a senha
		if(trocaSenha && !getSenhaConfirmacao().equals("") && getSenhaConfirmacao() != null){
			if (getInstancia().getSenha().equals(getSenhaConfirmacao())){
				getInstancia().setSenha(Utilities.encriptaParaMd5(getInstancia().getSenha()));
				return super.atualizar();
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senhas não conferem!", "Informe duas senhas iguais."));
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
	
	@Override
	public boolean enviar() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			if(getInstancia().getSenha().equals(getSenhaConfirmacao())){
				if(procurarUsuario(getInstancia().getLogin()) == null){
					getInstancia().setSenha(Utilities.encriptaParaMd5(getInstancia().getSenha()));
					getInstancia().setDataInclusao(new Date());
					getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
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
	

}
