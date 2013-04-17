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
import br.com.imhotep.auxiliar.Utilities;
import br.com.imhotep.controle.ControleMenu;
import br.com.imhotep.controle.ControleSenha;
import br.com.imhotep.excecoes.ExcecaoAcessoNaoAutorizado;
import br.com.imhotep.excecoes.ExcecaoSistemaManutencao;
import br.com.imhotep.excecoes.ExcessaoAcessoNaoAutorizado;
import br.com.imhotep.excecoes.ExcessaoPaginaForaPadrao;
import br.com.imhotep.excecoes.ExcessaoUsuarioTrocaSenha;

@ManagedBean
@RequestScoped
public class GerenciadorRequisicao implements PhaseListener{

	private static final long serialVersionUID = 1L;

	public GerenciadorRequisicao() {
	}
	
	private void acessoPaginaNaoAutorizado(String pagina) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, ExcecaoAcessoNaoAutorizado{
		ControleMenu controleMenu = (ControleMenu) Utilities.procuraInstancia(ControleMenu.class);
		if(existeUsuarioLogado() && !paginaAcessoGeral(pagina) && !controleMenu.urlAutorizada(pagina)){
			FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_HOME);
			throw new ExcecaoAcessoNaoAutorizado(pagina);
		}
	}
	
	private void sistemaEmManutencao(String paginaAtual) throws IOException, ExcecaoSistemaManutencao{
		if(Parametro.isManutencao()){
			boolean paginaManutencao = paginaAtual.indexOf(Constantes.PAGINA_MANUTENCAO) == 0;
			if(!paginaManutencao)
				FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_MANUTENCAO);
			throw new ExcecaoSistemaManutencao();
		}
	}
	
	private void verificacaoPaginaLogin(String pagina) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, ExcessaoAcessoNaoAutorizado {
		boolean paginaLogin = pagina.indexOf(Constantes.PAGINA_LOGIN) == 0;
		if(!paginaLogin && !existeUsuarioLogado()){
			FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_LOGIN);
			throw new ExcessaoAcessoNaoAutorizado();
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
			verificarPadraoPagina(pagina);
			acessoPaginaNaoAutorizado(pagina);
			sistemaEmManutencao(pagina);
			verificacaoPaginaLogin(pagina);			
			verificaUsuarioNovaSenha(pagina);
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
		} catch (ExcessaoAcessoNaoAutorizado e) {
			e.printStackTrace();
		} catch (ExcessaoUsuarioTrocaSenha e) {
			e.printStackTrace();
		} catch (ExcessaoPaginaForaPadrao e) {
			e.printStackTrace();
		}

	}

	private void verificarPadraoPagina(String pagina) throws IOException, ExcessaoPaginaForaPadrao {
		if(pagina.indexOf("jsf") > 0){
			FacesContext.getCurrentInstance().getExternalContext().redirect(pagina.replaceAll("jsf", "hu"));
			throw new ExcessaoPaginaForaPadrao();
		}
	}

	private void verificaUsuarioNovaSenha(String pagina) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ExcessaoUsuarioTrocaSenha, IOException {
		boolean paginaLogin = pagina.indexOf(Constantes.PAGINA_LOGIN) == 0;
		boolean paginaTrocaSenha = pagina.indexOf(Constantes.PAGINA_TROCA_SENHA) == 0;
		if(!paginaLogin && !paginaTrocaSenha && Autenticador.getInstancia() != null && (new ControleSenha().senhaIgualMatricula() || new ControleSenha().senhaResetada())){
			FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_TROCA_SENHA);
			throw new ExcessaoUsuarioTrocaSenha();
		}
	}

	private boolean paginaAcessoGeral(String pagina){
		boolean paginaHome = pagina.equals(Constantes.PAGINA_HOME);
		boolean paginaLogin = pagina.equals(Constantes.PAGINA_LOGIN);
		boolean paginaManutencao = pagina.equals(Constantes.PAGINA_MANUTENCAO);
		boolean paginaTrocaSenha = pagina.equals(Constantes.PAGINA_TROCA_SENHA);
		return paginaHome || paginaLogin || paginaManutencao || paginaTrocaSenha;
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

