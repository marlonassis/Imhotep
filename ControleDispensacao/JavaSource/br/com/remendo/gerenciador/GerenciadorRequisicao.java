package br.com.remendo.gerenciador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import br.com.ControleDispensacao.controle.ControleSenha;
import br.com.ControleDispensacao.seguranca.Autenticador;

@ManagedBean(name="gerenciadorRequisicao")
@RequestScoped
public class GerenciadorRequisicao implements PhaseListener{

	private static final String PAGINA_LOGIN = "/ControleDispensacao/PaginasWeb/login.jsf";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		try {
			FacesContext facesContext = event.getFacesContext();
			Autenticador autenticador = Autenticador.getInstancia();
			boolean paginaLogin = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRequestURI().indexOf(PAGINA_LOGIN) == 0;
			if(!paginaLogin && (autenticador == null || autenticador.getUsuarioAtual() == null)){
				facesContext.getExternalContext().redirect(PAGINA_LOGIN);
			}else{
				new ControleSenha().verificaSenhaPadrao();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}  

	}

	@Override
	public void beforePhase(PhaseEvent event) {
    }  
		
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	public void login(){
		
	}
	
}

