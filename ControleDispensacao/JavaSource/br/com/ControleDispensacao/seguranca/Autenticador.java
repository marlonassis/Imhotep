package br.com.ControleDispensacao.seguranca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.ControleDispensacao.auxiliar.ControleInstancia;
import br.com.ControleDispensacao.auxiliar.ControleMenu;
import br.com.ControleDispensacao.auxiliar.ControlePainel;
import br.com.ControleDispensacao.auxiliar.ControleSenha;
import br.com.ControleDispensacao.entidade.Menu;
import br.com.ControleDispensacao.entidade.Painel;
import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.entidade.Unidade;
import br.com.ControleDispensacao.entidade.Usuario;
import br.com.ControleDispensacao.negocio.PrescricaoHome;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.utilidades.Utilities;

@ManagedBean(name="autenticador")
@SessionScoped
public class Autenticador {
	
	private Usuario usuarioAtual;
	private Usuario usuario = new Usuario();
	private Unidade unidadeAtual;
	private Profissional profissionalAtual;
	private boolean mostraComboUnidade;
	private Collection<Unidade> unidades;

	public static Autenticador getInstancia(){
		return new ControleInstancia<Autenticador>().instancia("autenticador");
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
		}else{
			mostraComboUnidade = true;
		}
	}

	//////////////////////////////////////Nova forma de pesquisar o usuario. A busca deve ser feita apenas para trazer o profissional e não o usuário
	
	private Object buscaGenerica(String sql, HashMap<Object, Object> hm){
		ConsultaGeral<Object> cg = new ConsultaGeral<Object>();
		return cg.consultaUnica(new StringBuilder(sql), hm);
	}
	
	public Profissional profissionalPeloNomeUsuario(String nome, String senha){
		senha = Utilities.encriptaParaMd5(senha);
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		String sqlSenha = "Select o.senha from Usuario o where o.login = :login";
		hm.put("login", nome);
		String resultadoSenha = (String) buscaGenerica(sqlSenha, hm);
		if(senha.equals(resultadoSenha)){
			String sqlProfissional = "select o from Profissional o where o.usuario.login = :login";
			return (Profissional) buscaGenerica(sqlProfissional, hm);
		}
		return null;
	}
	
	
	//////////////////////////////////////////////////////
	
	public Profissional profissionalPeloUsuario(Usuario usuario){
		ConsultaGeral<Profissional> cg = new ConsultaGeral<Profissional>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idUsuario", usuario.getIdUsuario());
		return cg.consultaUnica(new StringBuilder("select o from Profissional o where o.usuario.idUsuario = :idUsuario"), hm);
	}
	
	private void carregaProfissional(){
		ConsultaGeral<Profissional> cg = new ConsultaGeral<Profissional>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idUsuario", usuarioAtual.getIdUsuario());
		profissionalAtual = cg.consultaUnica(new StringBuilder("select o from Profissional o where o.usuario.idUsuario = :idUsuario"), hm);
	}
	
	public void logout(){
		try{
			setUsuarioAtual(new Usuario());
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
			session.invalidate();
			setUsuarioAtual(null);
			setUnidadeAtual(null);
			unidades = null;
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuário e/ou senha não confere!", "Usuário não encontrado!"));
			return null;
		}
	}

	public void continuaLogin(){
		if(unidadeAtual != null){
			mostraComboUnidade = false;
			new ControleSenha().redirecionaPaginaConformeSenha();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe uma unidade.", ""));
		}
	}
	
	public boolean verificaSenha(Usuario usuarioLogado, String senha){
		if(usuarioLogado.getSenha().equals(Utilities.encriptaParaMd5(senha))){
			return true;
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuário e/ou senha não confere!", "Login não realizado!"));
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
	    				new ControleSenha().redirecionaPaginaConformeSenha();
	    			}
	    		}
	    	}
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro desconhecido!",  null));
			if(((Usuario)facesContext.getExternalContext().getSessionMap().get("usuario")) == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao logar! Você não está logado.", "Tente logar novamente!"));
			}
		}
		setUsuario(new Usuario());
	}
	
	private void carregaPaineis() {
		try {
			//carrega o menu que pertence ao usuário
			HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
			hashMap.put("idEspecialidade", Autenticador.getInstancia().getProfissionalAtual().getEspecialidade().getIdEspecialidade());
			StringBuilder hql = new StringBuilder("select o.painel from AutorizaPainel o where o.especialidade.idEspecialidade = :idEspecialidade)");
			ControlePainel controlePainel = new ControlePainel();
			controlePainel.setPainelAutorizadoList(new ArrayList<Painel>(new ConsultaGeral<Painel>().consulta(new StringBuilder(hql), hashMap)));
			//após carregar o menu é chamado o método converteMenuString para converter todo o menu em uma lista de string
			controlePainel.convertePainelString();
			Utilities.atualizaInstancia(controlePainel);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Monta o menu de acordo com as permissões de cada usuário
	 */
	private void carregaToolBarMenu() {
		try {
			//carrega o menu que pertence ao usuário
			String hql = "select o.menu from AutorizaMenu o where o.especialidade.idEspecialidade = :idEspecialidade order by to_ascii(o.menu.descricao)";
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idEspecialidade", getProfissionalAtual().getEspecialidade().getIdEspecialidade());
			ControleMenu controleMenu = new ControleMenu();
			controleMenu.setMenuAutorizadoList(new ArrayList<Menu>(new ConsultaGeral<Menu>().consulta(new StringBuilder(hql), hm)));
			//após carregar o menu é chamado o método converteMenuString para converter todo o menu em uma lista de string
			controleMenu.converteMenuString();
			Utilities.atualizaInstancia(controleMenu);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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

}
