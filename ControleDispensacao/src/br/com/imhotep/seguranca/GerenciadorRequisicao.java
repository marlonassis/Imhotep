package br.com.imhotep.seguranca;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.controle.ControleMenu;
import br.com.imhotep.controle.ControleSenha;
import br.com.imhotep.excecoes.ExcecaoAcessoNaoAutorizado;
import br.com.imhotep.excecoes.ExcecaoPaginaForaPadrao;
import br.com.imhotep.excecoes.ExcecaoSistemaManutencao;
import br.com.imhotep.excecoes.ExcecaoUsuarioTrocaSenha;

@ManagedBean
@RequestScoped
public class GerenciadorRequisicao implements PhaseListener{

	private static final long serialVersionUID = 1L;

	public GerenciadorRequisicao() {
	}
	
	public void redirecionarPagina(String pagina) throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect(pagina);
	}
	
	private void acessoPaginaNaoAutorizado(String pagina) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, ExcecaoAcessoNaoAutorizado{
		ControleMenu controleMenu = (ControleMenu) Utilitarios.procuraInstancia(ControleMenu.class);
		boolean administrador = new Parametro().isProfissionalAdministrador();
		if(!administrador && existeUsuarioLogado() && !paginaAcessoGeral(pagina) && !controleMenu.urlAutorizada(pagina)){
			controleMenu.urlAutorizada(pagina);
			FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_HOME);
			throw new ExcecaoAcessoNaoAutorizado(pagina);
		}
	}
	
	private void sistemaEmManutencao(String paginaAtual) throws IOException, ExcecaoSistemaManutencao{
		boolean paginaManutencao = paginaAtual.indexOf(Constantes.PAGINA_MANUTENCAO) == 0;
		if(Parametro.isManutencao()){
			if(!paginaManutencao)
				FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_MANUTENCAO);
			throw new ExcecaoSistemaManutencao();
		}else{
			if(paginaManutencao)
				FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_HOME);
		}
	}
	
	private void verificacaoPaginaLogin(String pagina) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoAcessoNaoAutorizado {
		boolean paginaLogin = pagina.indexOf(Constantes.PAGINA_LOGIN) == 0;
		boolean paginaRecusaIExplorer = pagina.equals(Constantes.PAGINA_RECUSA_IEXPLORER);
		if(!paginaLogin && !existeUsuarioLogado() && !paginaRecusaIExplorer){
			FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_LOGIN);
			throw new ExcecaoAcessoNaoAutorizado();
		}else{
			if(paginaLogin && existeUsuarioLogado())
				FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_HOME);
		}
	}
	
	@Override
	public void beforePhase(PhaseEvent event) {
		
		try {
			FacesContext facesContext = event.getFacesContext();
			String pagina = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRequestURI();
			boolean paginaPublica = pagina.contains("/PaginasWeb/Publico/");
			if(!paginaPublica){
				verificarPadraoPagina(pagina);
				acessoPaginaNaoAutorizado(pagina);
				sistemaEmManutencao(pagina);
				verificacaoPaginaLogin(pagina);			
				verificaUsuarioNovaSenha(pagina);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ExcecaoAcessoNaoAutorizado e) {
			e.printStackTrace();
		} catch (ExcecaoSistemaManutencao e) {
			e.printStackTrace();
		} catch (ExcecaoUsuarioTrocaSenha e) {
			e.printStackTrace();
		} catch (ExcecaoPaginaForaPadrao e) {
			e.printStackTrace();
		}

	}

	private void verificarPadraoPagina(String pagina) throws IOException, ExcecaoPaginaForaPadrao {
		if(pagina.indexOf("jsf") > 0){
			FacesContext.getCurrentInstance().getExternalContext().redirect(pagina.replaceAll("jsf", "hu"));
			throw new ExcecaoPaginaForaPadrao();
		}
	}

	private void verificaUsuarioNovaSenha(String pagina) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ExcecaoUsuarioTrocaSenha, IOException {
		boolean paginaHome = pagina.indexOf(Constantes.PAGINA_HOME) == 0;
		if(!paginaHome && Autenticador.getInstancia() != null && (new ControleSenha().senhaIgualMatricula() || new ControleSenha().senhaResetada())){
			FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_HOME);
			throw new ExcecaoUsuarioTrocaSenha();
		}
	}

	private boolean paginaAcessoGeral(String pagina){
		boolean paginaHome = pagina.equals(Constantes.PAGINA_HOME);
		boolean paginaLogin = pagina.equals(Constantes.PAGINA_LOGIN);
		boolean paginaManutencao = pagina.equals(Constantes.PAGINA_MANUTENCAO);
		boolean paginaTrocaSenha = pagina.equals(Constantes.PAGINA_TROCA_SENHA);
		boolean paginaRecusaIExplorer = pagina.equals(Constantes.PAGINA_RECUSA_IEXPLORER);
		return paginaHome || paginaLogin || paginaManutencao || paginaTrocaSenha || paginaRecusaIExplorer;
	}
	
	
	private boolean existeUsuarioLogado() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return Autenticador.getInstancia() != null && Autenticador.getInstancia().getUsuarioAtual() != null;
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

