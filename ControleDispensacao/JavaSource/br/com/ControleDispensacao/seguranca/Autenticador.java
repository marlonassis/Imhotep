package br.com.ControleDispensacao.seguranca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import br.com.ControleDispensacao.auxiliar.Parametro;
import br.com.ControleDispensacao.entidade.Menu;
import br.com.ControleDispensacao.entidade.Painel;
import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.entidade.Unidade;
import br.com.ControleDispensacao.entidade.Usuario;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.utilidades.Utilities;

@ManagedBean(name="autenticador")
@SessionScoped
public class Autenticador {
	
	private Usuario usuarioAtual;
	private Usuario usuario = new Usuario();
	private Unidade unidadeAtual;
	private Profissional profissionalAtual;
	private MenuModel menuModel;
	private boolean mostraComboUnidade;
	private Collection<Unidade> unidades;
	private static final String PAGINA_LOGIN = "/ControleDispensacao/PaginasWeb/login.jsf";
	private static final String PAGINA_HOME = "/ControleDispensacao/PaginasWeb/home.jsf";
	private Collection<Painel> paineisUsuario;

	public static Autenticador getInstancia(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
		if(session != null){
			return (Autenticador) session.getAttribute("autenticador");
		}else{
			return null;
		}
	}
	
	private void carregaUnidadesUsuario(){
		ConsultaGeral<Unidade> cg = new ConsultaGeral<Unidade>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idUsuario", usuarioAtual.getIdUsuario());
		unidades = cg.consulta(new StringBuilder("select a.unidade from AutorizaUnidadeProfissional a where a.profissional.usuario.idUsuario = :idUsuario"), hm);
		carregaUnidadeAtual();
	}

	private void carregaUnidadeAtual() {
		if(unidades.size() == 1){
			unidadeAtual = unidades.iterator().next();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(PAGINA_HOME);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			mostraComboUnidade = true;
		}
	}

	private void carregaProfissional(){
		ConsultaGeral<Profissional> cg = new ConsultaGeral<Profissional>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idUsuario", usuarioAtual.getIdUsuario());
		profissionalAtual = cg.consultaUnica(new StringBuilder("select o from Profissional o where o.usuario.idUsuario = :idUsuario"), hm);
	}
	
	public void home(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			facesContext.getExternalContext().redirect(PAGINA_HOME);
		}catch (Exception e) {
			e.printStackTrace();
			if(getUsuarioAtual() != null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao tentar sair! Tente sair novamente.", "Login não realizado!"));
			}
		}
	}
	
	public void logout(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			setUsuarioAtual(new Usuario());
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);  
			session.invalidate();
			setUsuarioAtual(null);
			setUnidadeAtual(null);
			unidades = null;
			facesContext.getExternalContext().redirect(PAGINA_LOGIN);
		}catch (Exception e) {
			e.printStackTrace();
			if(getUsuarioAtual() != null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao tentar sair! Tente sair novamente.", "Login não realizado!"));
			}
		}
	}
	
	public boolean isUsuarioAtivo(){
		return getUsuarioAtual() != null && getUsuarioAtual().getIdUsuario() != 0 ? true : false;
	}
	
	public Usuario procurarUsuario(String nome){
		ConsultaGeral<Usuario> cg = new ConsultaGeral<Usuario>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("login", nome.trim());
		Usuario resultado = cg.consultaUnica(new StringBuilder("select o from Usuario o where o.login = :login"), hm);
		if(resultado != null){
			return resultado;
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuário ou senha não confere!", "Usuário não encontrado!"));
			return null;
		}
	}

	public void continuaLogin(){
		if(unidadeAtual != null){
			try {
				mostraComboUnidade = false;
				FacesContext.getCurrentInstance().getExternalContext().redirect(PAGINA_HOME);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe uma unidade.", ""));
		}
	}
	
	public boolean verificaSenha(Usuario usuarioLogado, String senha){
		if(usuarioLogado.getSenha().equals(Utilities.md5(senha))){
			return true;
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuário ou senha não confere!", "Login não realizado!"));
			return false;
		}
	}
	
	public void logarUsuario(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			if(!getUsuario().getLogin().isEmpty()){
	    		Usuario usuarioLogado = procurarUsuario(getUsuario().getLogin());
	    		if(usuarioLogado != null && usuarioLogado.getIdUsuario() != 0){
	    			if(verificaSenha(usuarioLogado, getUsuario().getSenha())){
	    				setUsuarioAtual(usuarioLogado);
	    				facesContext.getExternalContext().getSessionMap().put("usuario", usuarioLogado);
	    				setUsuarioAtual(usuarioLogado);
	    				carregaUnidadesUsuario();
	    				carregaProfissional();
	    				carregaToolBarMenu();
	    				carregaPaineis();
	    			}
	    		}
	    	}
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro desconhecido!",  null));
			if(((Usuario)facesContext.getExternalContext().getSessionMap().get("usuario")) == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao logar! Você não está logado.", "Tente logar novamente!"));
			}else{
				try{ 
					facesContext.getExternalContext().redirect(PAGINA_HOME);
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		setUsuario(new Usuario());
	}
	
	private void carregaPaineis() {
		ConsultaGeral<Painel> cg = new ConsultaGeral<Painel>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idEspecialidade", Autenticador.getInstancia().getProfissionalAtual().getEspecialidade().getIdEspecialidade());
		StringBuilder sb = new StringBuilder("select o.painel from AutorizaPainel o where o.especialidade.idEspecialidade = :idEspecialidade)");
		
		paineisUsuario = cg.consulta(sb, hashMap);
	}

	/**
	 * Monta o menu de acordo com as permissões de cada usuário
	 */
	private void carregaToolBarMenu() {
		//TODO tornar o menu recursivo
		menuModel = new DefaultMenuModel();

		MenuItem mi = new MenuItem();
		mi.setValue("Home");
		String action = "#{autenticador.home()}";
		MethodExpression methodExpression = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
													createMethodExpression(FacesContext.getCurrentInstance().getELContext(), action, null, new Class[0]);
		mi.setActionExpression(methodExpression);
		
		menuModel.addMenuItem(mi);
		
		ConsultaGeral<Menu> cg = new ConsultaGeral<Menu>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		StringBuilder sb;
		if(Parametro.isUsuarioTeste() || Parametro.isUsuarioAdministrador()){
			sb = new StringBuilder("select o from Menu o where o.menuPai is null order by o.descricao");
		}else{
			hashMap.put("idEspecialidade", Autenticador.getInstancia().getProfissionalAtual().getEspecialidade().getIdEspecialidade());
			sb = new StringBuilder("select o.menu from AutorizaMenu o where o.menu.menuPai is null and o.especialidade.idEspecialidade = :idEspecialidade order by o.menu.descricao");
		}
		
		Collection<Menu> listaPrimeiroNivel = cg.consulta(sb, hashMap);
		for (Menu menuPrimeiroNivel : listaPrimeiroNivel) {
			Submenu primeiroNivel = new Submenu();
			primeiroNivel.setLabel(menuPrimeiroNivel.getDescricao());
			primeiroNivel.setId(menuPrimeiroNivel.getDescricao().trim().substring(0, 2).concat(String.valueOf(menuPrimeiroNivel.getIdMenu())));
			
			hashMap.put("idMenuPai", menuPrimeiroNivel.getIdMenu());
			if(Parametro.isUsuarioTeste() || Parametro.isUsuarioAdministrador()){
				sb = new StringBuilder("select o from Menu o where ");
				sb.append(" o.menuPai.idMenu = :idMenuPai order by o.descricao ");
			}else{
				sb = new StringBuilder("select o.menu from AutorizaMenu o where ");
				sb.append(" o.menu.menuPai.idMenu = :idMenuPai ");
				sb.append(" and o.especialidade.idEspecialidade = :idEspecialidade order by o.menu.descricao ");
			}
			Collection<Menu> listaSegundoNivel = cg.consulta(sb, hashMap);
			for (Menu menuSegundoNivel : listaSegundoNivel) {
				hashMap.put("idMenuPai", menuSegundoNivel.getIdMenu());
				Collection<Menu> listaTerceiroNivel = cg.consulta(sb, hashMap);
				if(listaTerceiroNivel.size() > 0){
					Submenu segundoNivel = new Submenu();
					segundoNivel.setLabel(menuSegundoNivel.getDescricao());
					for (Menu menuTerceiroNivel : listaTerceiroNivel) {
						MenuItem mm = new MenuItem();
						mm.setValue(menuTerceiroNivel.getDescricao());
						mm.setUrl(menuTerceiroNivel.getUrl());
						segundoNivel.getChildren().add(mm);
					}
					primeiroNivel.getChildren().add(segundoNivel);
				}else{
					MenuItem mm = new MenuItem();
					mm.setValue(menuSegundoNivel.getDescricao());
					mm.setUrl(menuSegundoNivel.getUrl());
					primeiroNivel.getChildren().add(mm);
				}
			}
			
			menuModel.addSubmenu(primeiroNivel);
		}
		
		mi = new MenuItem();
		mi.setValue("Sair");

		action = "#{autenticador.logout()}";
		methodExpression = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
									createMethodExpression(FacesContext.getCurrentInstance().getELContext(), action, null, new Class[0]);
		
		mi.setActionExpression(methodExpression);
		mi.setOncomplete("window.location.reload();");
		menuModel.addMenuItem(mi);
	}

	
	
	public Usuario getUsuarioAtual() {
		return usuarioAtual;
	}

	public void setUsuarioAtual(Usuario usuarioAtual) {
		this.usuarioAtual = usuarioAtual;
	}

	public Unidade getUnidadeAtual() {
		return unidadeAtual;
	}

	public void setUnidadeAtual(Unidade unidadeAtual) {
		this.unidadeAtual = unidadeAtual;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Collection<Unidade> getUnidades() {
		return unidades;
	}

	public MenuModel getMenuModel(){
		return menuModel;
	}

	public Profissional getProfissionalAtual() {
		return profissionalAtual;
	}

	public void setProfissionalAtual(Profissional profissionalAtual) {
		this.profissionalAtual = profissionalAtual;
	}

	public boolean isMostraComboUnidade() {
		return mostraComboUnidade;
	}

	public void setMostraComboUnidade(boolean mostraComboUnidade) {
		this.mostraComboUnidade = mostraComboUnidade;
	}

	public List<Painel> getListaPaineisUsuario(){
		return paineisUsuario == null ? null : new ArrayList<Painel>(paineisUsuario);
	}
	
}
