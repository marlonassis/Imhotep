package br.com.ControleDispensacao.negocio;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.ControleDispensacao.entidade.Usuario;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;
import br.com.nucleo.utilidades.Utilities;


@ManagedBean(name="usuarioHome")
@SessionScoped
public class UsuarioHome extends PadraoHome<Usuario> {

	private Usuario usuario = new Usuario();
	private String senhaConfirmacao;
	private boolean trocaSenha;
	
	
	/**
	 * Método que retorna uma lista de Usuarios de acordo com o nome informado
	 * @param String sql
	 * @return Collection Usuario
	 */
	public Collection<Usuario> getListaUsuarioSuggest(String sql){
		return super.getBusca("select o from Usuario as o where o.nome like '%"+sql+"%' ");
	}

	
	@Override
	public boolean atualizar() {
		//procura se existe algum usuário com o mesmo login
		//se o usuário informou a senha de confirmação então devemos validar a senha
		if(trocaSenha && !getSenhaConfirmacao().equals("") && getSenhaConfirmacao() != null){
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
	
	@Override
	public boolean enviar() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			if(getInstancia().getSenha().equals(getSenhaConfirmacao())){
				if(procurarUsuario(getInstancia().getLogin()) == null){
					getInstancia().setSenha(Utilities.md5(getInstancia().getSenha()));
					getInstancia().setDataInclusao(new Date());
					getInstancia().setUsuarioInclusao(Autenticador.getUsuarioAtual());
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
	

}
