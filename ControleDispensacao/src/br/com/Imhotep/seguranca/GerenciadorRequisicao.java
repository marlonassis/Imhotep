package br.com.Imhotep.seguranca;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.auxiliar.Parametro;
import br.com.Imhotep.controle.ControleSenha;

@ManagedBean(name="gerenciadorRequisicao")
@RequestScoped
public class GerenciadorRequisicao implements PhaseListener{

	private static final long serialVersionUID = 1L;

	public GerenciadorRequisicao() {
	}
	
	@Override
	public void beforePhase(PhaseEvent event) {
		try {
			FacesContext facesContext = event.getFacesContext();

			String pagina = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRequestURI();
			if(pagina.indexOf("jsf") > 0){
				FacesContext.getCurrentInstance().getExternalContext().redirect(pagina.replaceAll("jsf", "hu"));
			}
			
			if(Parametro.isManutencao()){
				boolean paginaManutencao = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRequestURI().indexOf(Constantes.PAGINA_MANUTENCAO) == 0;
				if(!paginaManutencao)
					FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_MANUTENCAO);
				return;
			}
			
			boolean paginaLogin = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRequestURI().indexOf(Constantes.PAGINA_LOGIN) == 0;
			if(!paginaLogin && (Autenticador.getInstancia() == null || Autenticador.getInstancia().getUsuarioAtual() == null)){
				facesContext.getExternalContext().redirect(Constantes.PAGINA_LOGIN);
				return;
			}else{
				if(paginaLogin && Autenticador.getInstancia() != null && Autenticador.getInstancia().getUsuarioAtual() != null)
					facesContext.getExternalContext().redirect(Constantes.PAGINA_HOME);
			}
			
			boolean paginaTrocaSenha = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRequestURI().indexOf(Constantes.PAGINA_TROCA_SENHA) == 0;
			if(!paginaLogin && !paginaTrocaSenha && Autenticador.getInstancia() != null && (new ControleSenha().senhaIgualMatricula() || new ControleSenha().senhaResetada())){
				FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_TROCA_SENHA);
				return;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}  

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	public void login(){
		
	}

	@Override
	public void afterPhase(PhaseEvent arg0) {
		
	}
	
}

