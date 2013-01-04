package br.com.ControleDispensacao.controle;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.ControleDispensacao.auxiliar.Constantes;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.utilidades.Utilities;

public class ControleSenha {	
	public static ControleSenha getInstancia(){
		try {
			return (ControleSenha) Utilities.procuraInstancia(ControleSenha.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void redirecionaPaginaConformeSenha() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		try {
			if(!senhaIgualMatricula()){
				FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_HOME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean senhaIgualMatricula() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Autenticador autenticador = Autenticador.getInstancia();
		if(autenticador.getProfissionalAtual() != null){
			String senha = autenticador.getUsuarioAtual().getSenha();
			String matriculaCriptografada = Utilities.encriptaParaMd5(String.valueOf(autenticador.getProfissionalAtual().getMatricula()));
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
			if(senhaIgualMatricula() && autenticador.getUnidadeAtual() != null){
				boolean paginaTrocaSenha = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI().indexOf(Constantes.PAGINA_TROCA_SENHA) == 0;
				if(!paginaTrocaSenha){
					FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_TROCA_SENHA);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Vocẽ ainda não trocou sua senha. Para sua segurança troque agora a sua senha.", ""));
				}
			}
		}
	}
	
}
