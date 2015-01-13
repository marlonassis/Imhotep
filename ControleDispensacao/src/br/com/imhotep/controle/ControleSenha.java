package br.com.imhotep.controle;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.seguranca.Autenticador;

public class ControleSenha {	
	public static ControleSenha getInstancia(){
		try {
			return (ControleSenha) Utilitarios.procuraInstancia(ControleSenha.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean senhaResetada() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Autenticador autenticador = Autenticador.getInstancia();
		if(autenticador.getProfissionalAtual() != null){
			String senha = autenticador.getUsuarioAtual().getSenha();
			String senhaPadraoReset = Utilitarios.encriptaParaMd5("123456");
			if(senha.equals(senhaPadraoReset)){
				return true;
			}
		}
		return false;
	}
	
	public boolean senhaIgualMatricula() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Autenticador autenticador = Autenticador.getInstancia();
		if(autenticador.getProfissionalAtual() != null){
			String senha = autenticador.getUsuarioAtual().getSenha();
			String matriculaCriptografada = Utilitarios.encriptaParaMd5(String.valueOf(autenticador.getProfissionalAtual().getMatricula()));
			if(senha.equals(matriculaCriptografada)){
				return true;
			}
		}
		return false;
	}
	
	public void verificaSenhaPadrao() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		verificaSenhaPadrao(Autenticador.getInstancia());
	}
	
	public void verificaSenhaPadrao(Autenticador autenticador) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		if(autenticador != null && autenticador.getUsuarioAtual() != null){
			if(senhaIgualMatricula()){
				boolean paginaTrocaSenha = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI().indexOf(Constantes.PAGINA_TROCA_SENHA) == 0;
				if(!paginaTrocaSenha){
					FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_HOME);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Você ainda não trocou sua senha. Para sua segurança troque agora a sua senha.", ""));
				}
			}
		}
	}
	
}
