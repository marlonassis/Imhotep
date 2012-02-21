package br.com.nucleo.gerenciador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

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

	private Usuario usuario;
	
	public GerenciadorRequisicao() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void afterPhase(PhaseEvent event) {
		try {
			FacesContext facesContext = event.getFacesContext(); 
			
			if(Autenticador.getUsuarioAtual() == null || Autenticador.getUsuarioAtual().getIdUsuario() == 0){
				facesContext.getExternalContext().getSessionMap().put("usuario", null);
			}
			
			Boolean usuarioSemAcesso = facesContext.getExternalContext().getSessionMap().get("usuario") == null && ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRequestURI().indexOf(PAGINA_LOGIN) != 0;
			if(usuarioSemAcesso){
				Autenticador.setUsuarioAtual(null);
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
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}

