package br.com.nucleo.gerenciador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.ControleDispensacao.entidade.Usuario;
import br.com.ControleDispensacao.negocio.UsuarioHome;
import br.com.ControleDispensacao.seguranca.Autenticador;

@ManagedBean(name="gerenciadorRequisicao")
@RequestScoped
public class GerenciadorRequisicao implements PhaseListener{

	private static final String PAGINA_LOGIN = "/ControleDispensacao/PaginasWeb/login.jsf";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GerenciadorRequisicao() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void afterPhase(PhaseEvent event) {
		try {
			FacesContext facesContext = event.getFacesContext();
			Autenticador autenticador = Autenticador.getInstancia();
			boolean paginaLogin = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRequestURI().indexOf(PAGINA_LOGIN) == 0;
			if(!paginaLogin && (autenticador == null || autenticador.getUsuarioAtual() == null)){
				facesContext.getExternalContext().redirect(PAGINA_LOGIN);
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
		// TODO Auto-generated method stub
		return PhaseId.ANY_PHASE;
	}

	public void login(){
		
	}
	
}

