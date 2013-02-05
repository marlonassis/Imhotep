package br.com.Imhotep.seguranca;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.controle.ControleSenha;

@ManagedBean(name="gerenciadorRequisicao")
@RequestScoped
public class GerenciadorRequisicao implements PhaseListener{

	private static final long serialVersionUID = 1L;

	public GerenciadorRequisicao() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void afterPhase(PhaseEvent event) {
		try {
			FacesContext facesContext = event.getFacesContext();
			Autenticador autenticador = Autenticador.getInstancia();
			boolean paginaLogin = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRequestURI().indexOf(Constantes.PAGINA_LOGIN) == 0;
			if(!paginaLogin && (autenticador == null || autenticador.getUsuarioAtual() == null)){
				facesContext.getExternalContext().redirect(Constantes.PAGINA_LOGIN);
			}
			
			boolean paginaTrocaSenha = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRequestURI().indexOf(Constantes.PAGINA_TROCA_SENHA) == 0;
			if(!paginaLogin && !paginaTrocaSenha && autenticador != null && (new ControleSenha().senhaIgualMatricula() || new ControleSenha().senhaResetada())){
				FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_TROCA_SENHA);
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

