package br.com.imhotep.seguranca;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.consulta.raiz.SolicitacaoMedicamentoUnidadeConsultaRaiz;
import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.controle.ControleMenu;
import br.com.imhotep.controle.ControlePainel;
import br.com.imhotep.controle.ControlePainelAviso;
import br.com.imhotep.controle.ControleSenha;
import br.com.imhotep.entidade.Menu;
import br.com.imhotep.entidade.Painel;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.entidade.Usuario;
import br.com.imhotep.enums.TipoSituacaoEnum;
import br.com.imhotep.enums.TipoUsuarioLogEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoUsuarioInativo;
import br.com.imhotep.excecoes.ExcecaoUsuarioLogin;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.raiz.ConfiguracaoRaiz;
import br.com.imhotep.raiz.EstoqueAlmoxarifadoRaiz;
import br.com.imhotep.raiz.EstoqueRaiz;
import br.com.imhotep.raiz.UsuarioAcessoLogRaiz;
import br.com.imhotep.raiz.UsuarioRaiz;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="autenticador")
@SessionScoped
public class Autenticador {
	
	private Usuario usuarioAtual;
	private Usuario usuario = new Usuario();
	private Profissional profissionalAtual;
	private Profissional profissionalRecuperacao = new Profissional();
	private boolean exibirFormularioAtual=true;
	private boolean exibirMensagemUsuarioBloqueado;
	
	private String getSqlCarregarPainel(){
		int idProfissional = getProfissionalAtual().getIdProfissional();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(sqlPainelPorLotacao(idProfissional));
		stringBuilder.append(" union ");
		stringBuilder.append(getPainelPorFuncao(idProfissional));
		return stringBuilder.toString();
	}

	private String getPainelPorFuncao(int idProfissional) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select a.id_painel, a.cv_url, a.cv_descricao from controle.tb_painel a ");
		stringBuilder.append("inner join administrativo.tb_estrutura_organizacional_painel b ");
		stringBuilder.append("	on a.id_painel = b.id_painel ");
		stringBuilder.append("inner join controle.tb_acesso_funcao_painel c ");
		stringBuilder.append("	on c.id_estrutura_organizacional_painel = b.id_estrutura_organizacional_painel ");
		stringBuilder.append("inner join administrativo.tb_estrutura_organizacional_funcao d ");
		stringBuilder.append("	on d.id_estrutura_organizacional_funcao = c.id_estrutura_organizacional_funcao ");
		stringBuilder.append("inner join administrativo.tb_lotacao_profissional_funcao e ");
		stringBuilder.append("	on e.id_estrutura_organizacional_funcao = d.id_estrutura_organizacional_funcao ");
		stringBuilder.append("inner join administrativo.tb_lotacao_profissional f ");
		stringBuilder.append("	on f.id_lotacao_profissional = e.id_lotacao_profissional ");
		stringBuilder.append("where f.id_profissional = ");
		stringBuilder.append(idProfissional);
		return stringBuilder.toString();
	}

	private String sqlPainelPorLotacao(int idProfissional) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select a.id_painel, a.cv_url, a.cv_descricao from controle.tb_painel a ");
		stringBuilder.append("inner join administrativo.tb_estrutura_organizacional_painel b ");
		stringBuilder.append("	on a.id_painel = b.id_painel ");
		stringBuilder.append("inner join controle.tb_acesso_lotacao_painel c ");
		stringBuilder.append("	on c.id_estrutura_organizacional_painel = b.id_estrutura_organizacional_painel ");
		stringBuilder.append("inner join administrativo.tb_lotacao_profissional d ");
		stringBuilder.append("	on d.id_lotacao_profissional = c.id_lotacao_profissional ");
		stringBuilder.append("where d.id_profissional = ");
		stringBuilder.append(idProfissional);
		return stringBuilder.toString();
	}
	
	private ArrayList<Painel> carregarPainelLotacao() {
		String sql = getSqlCarregarPainel();
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		List<Object[]> listaResultado = lm.getListaResultado(sql, 3);
		ArrayList<Painel> paineis = new ArrayList<Painel>();
		for(Object[] obj : listaResultado){
			int idPainel = Integer.valueOf(String.valueOf(obj[0]));
			String url = String.valueOf(obj[1]);
			String descricao = String.valueOf(obj[2]);
			Painel painel = new Painel();
			painel.setIdPainel(idPainel);
			painel.setDescricao(descricao);
			painel.setUrl(url);
			paineis.add(painel);
		}
		return paineis;
	}
	
	private void carregaPaineis() {
		try {
			//carrega os paineis que pertencem ao profissional
			ArrayList<Painel> painelAutorizadoList = carregarPainelLotacao();
			ControlePainel controlePainel = new ControlePainel();
			controlePainel.setPainelAutorizadoList(painelAutorizadoList);
			//após carregar o menu, é chamado o método converteMenuString para converter todo o menu em uma lista de string
			controlePainel.convertePainelString();
			Utilitarios.atualizaInstancia(controlePainel);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private String getSqlCarregarMenu(){
		int idProfissional = getProfissionalAtual().getIdProfissional();
		StringBuilder stringBuilder = getSqlMenuPorFuncao(idProfissional);
		stringBuilder.append(" union ");
		getSqlMenuPorLotacao(idProfissional, stringBuilder);
		stringBuilder.append(" union ");
		getSqlMenuAdministrador(idProfissional, stringBuilder);
		stringBuilder.append(" union ");
		getSqlMenuControleAcessoChefia(idProfissional, stringBuilder);
		stringBuilder.append(" union ");
		getSqlMenuChefia(idProfissional, stringBuilder);
		return stringBuilder.toString();
	}

	private void getSqlMenuChefia(int idProfissional, StringBuilder stringBuilder) {
		stringBuilder.append("select distinct a.id_menu, a.id_menu_pai from controle.tb_menu a ");
		stringBuilder.append("	inner join administrativo.tb_estrutura_organizacional_menu b ");
		stringBuilder.append("		on a.id_menu = b.id_menu ");
		stringBuilder.append("	inner join administrativo.tb_lotacao_profissional c ");
		stringBuilder.append("		on c.id_estrutura_organizacional = b.id_estrutura_organizacional ");
		stringBuilder.append("	inner join administrativo.tb_estrutura_organizacional_funcao e ");
		stringBuilder.append("		on e.id_estrutura_organizacional = b.id_estrutura_organizacional ");
		stringBuilder.append("	inner join administrativo.tb_funcao f ");
		stringBuilder.append("		on f.id_funcao = e.id_funcao ");
		stringBuilder.append("	inner join administrativo.tb_lotacao_profissional_funcao d ");
		stringBuilder.append("		on d.id_lotacao_profissional = c.id_lotacao_profissional ");
		stringBuilder.append("		and d.id_estrutura_organizacional_funcao = e.id_estrutura_organizacional_funcao ");
		stringBuilder.append("where a.bl_bloqueado is false and f.bl_chefia is true and c.id_profissional = ");
		stringBuilder.append(idProfissional);
	}
	
	private void getSqlMenuControleAcessoChefia(int idProfissional, StringBuilder stringBuilder) {
		stringBuilder.append("select distinct a.id_menu, a.id_menu_pai from controle.tb_menu a ");
		stringBuilder.append("inner join administrativo.tb_lotacao_profissional f ");
		stringBuilder.append("	on f.id_profissional = ");
		stringBuilder.append(idProfissional);
		stringBuilder.append("inner join administrativo.tb_lotacao_profissional_funcao g ");
		stringBuilder.append("	on g.id_lotacao_profissional = f.id_lotacao_profissional ");
		stringBuilder.append("inner join administrativo.tb_estrutura_organizacional_funcao h ");
		stringBuilder.append("	on h.id_estrutura_organizacional_funcao = g.id_estrutura_organizacional_funcao ");
		stringBuilder.append("inner join administrativo.tb_funcao i ");
		stringBuilder.append("	on i.id_funcao = h.id_funcao ");
		stringBuilder.append("where a.bl_bloqueado is false and i.bl_chefia is true and a.id_menu in (");
		stringBuilder.append(Constantes.IDS_MENU_CHEFIA);
		stringBuilder.append(")");
	}
	
	private void getSqlMenuAdministrador(int idProfissional, StringBuilder stringBuilder) {
		stringBuilder.append("select distinct a.id_menu, a.id_menu_pai from controle.tb_menu a "); 
		stringBuilder.append("inner join administrativo.tb_lotacao_profissional f ");
		stringBuilder.append("	on f.id_profissional = ");
		stringBuilder.append(idProfissional);
		stringBuilder.append(" inner join administrativo.tb_lotacao_profissional_funcao g ");
		stringBuilder.append("	on g.id_lotacao_profissional = f.id_lotacao_profissional ");
		stringBuilder.append("inner join administrativo.tb_estrutura_organizacional_funcao h ");
		stringBuilder.append("	on h.id_estrutura_organizacional_funcao = g.id_estrutura_organizacional_funcao ");
		stringBuilder.append("where h.id_funcao = ");
		stringBuilder.append(Constantes.ID_FUNCAO_ADMINISTRADOR);
	}
	
	private void getSqlMenuPorLotacao(int idProfissional, StringBuilder stringBuilder) {
		stringBuilder.append("select distinct e.id_menu, e.id_menu_pai from controle.tb_acesso_lotacao a ");
		stringBuilder.append("	inner join administrativo.tb_estrutura_organizacional_menu b ");
		stringBuilder.append("		on a.id_estrutura_organizacional_menu = b.id_estrutura_organizacional_menu ");
		stringBuilder.append("	inner join administrativo.tb_estrutura_organizacional c ");
		stringBuilder.append("		on b.id_estrutura_organizacional = c.id_estrutura_organizacional ");
		stringBuilder.append("	inner join administrativo.tb_lotacao_profissional d ");
		stringBuilder.append("		on d.id_estrutura_organizacional = c.id_estrutura_organizacional ");
		stringBuilder.append("		and d.id_lotacao_profissional = d.id_lotacao_profissional ");
		stringBuilder.append("	inner join controle.tb_acesso_lotacao f ");
		stringBuilder.append("		on f.id_lotacao_profissional = d.id_lotacao_profissional ");
		stringBuilder.append("		and f.id_estrutura_organizacional_menu = b.id_estrutura_organizacional_menu ");
		stringBuilder.append("	inner join controle.tb_menu e on e.id_menu = b.id_menu ");
		stringBuilder.append("where e.bl_bloqueado is false and d.id_profissional = ");
		stringBuilder.append(idProfissional);
	}

	private StringBuilder getSqlMenuPorFuncao(int idProfissional) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select distinct a.id_menu, a.id_menu_pai from controle.tb_menu a ");
		stringBuilder.append("inner join administrativo.tb_estrutura_organizacional_menu b ");
		stringBuilder.append("	on a.id_menu = b.id_menu ");
		stringBuilder.append("inner join controle.tb_acesso_funcao c ");
		stringBuilder.append("	on c.id_estrutura_organizacional_menu = b.id_estrutura_organizacional_menu ");
		stringBuilder.append("inner join administrativo.tb_estrutura_organizacional_funcao d ");
		stringBuilder.append("	on d.id_estrutura_organizacional_funcao = c.id_estrutura_organizacional_funcao ");
		stringBuilder.append("inner join administrativo.tb_estrutura_organizacional e ");
		stringBuilder.append("	on e.id_estrutura_organizacional = d.id_estrutura_organizacional ");
		stringBuilder.append("inner join administrativo.tb_lotacao_profissional f ");
		stringBuilder.append("	on f.id_estrutura_organizacional = e.id_estrutura_organizacional ");
		stringBuilder.append("inner join administrativo.tb_lotacao_profissional_funcao g ");
		stringBuilder.append("	on g.id_lotacao_profissional = f.id_lotacao_profissional ");
		stringBuilder.append("	and d.id_estrutura_organizacional_funcao = g.id_estrutura_organizacional_funcao ");
		stringBuilder.append("where a.bl_bloqueado is false and f.id_profissional = ");
		stringBuilder.append(idProfissional);
		return stringBuilder;
	}
	
	private void carregaToolBarMenu() {
		try {
			Set<Menu> menuAutorizadoSet = new HashSet<Menu>(carregarMenuLotacao());
			ControleMenu controleMenu = new ControleMenu();
			controleMenu.setMenuAutorizadoList(new ArrayList<Menu>(menuAutorizadoSet));
			controleMenu.montarMenuModel();
			controleMenu.montarMenuPlanoString();
			Utilitarios.atualizaInstancia(controleMenu);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<Menu> carregarMenuLotacao() {
		String sql = getSqlCarregarMenu();
		
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		List<Object[]> listaResultado = lm.getListaResultado(sql);
		ArrayList<Menu> menus = new ArrayList<Menu>();
		for(Object[] obj : listaResultado){
			int idMenu = Integer.valueOf(String.valueOf(obj[0]));
			int idMenuPai = obj[1] == null ? 0 : Integer.valueOf(String.valueOf(obj[1]));
			
			Menu menu = carregarMenu(idMenu);
			menu.setMenuPai(carregarMenu(idMenuPai));
			
			menus.add(menu);
		}
		return menus;
	}

	private Menu carregarMenu(int idMenu){
		String sql = "select * from controle.tb_menu where id_menu = "+idMenu;
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		ResultSet rs = lm.consultar(sql);
		try {
			if(rs.next()){
				int id = rs.getInt("id_menu");
				String descricao = rs.getString("cv_descricao");
				String url = rs.getString("cv_url");
				String urlAjuda = rs.getString("cv_url_ajuda");
				Boolean bloqueado = rs.getBoolean("bl_bloqueado");
				Boolean interno = rs.getBoolean("bl_interno");
				Boolean construcao = rs.getBoolean("bl_construcao");
				Menu menu = new Menu();
				menu.setIdMenu(id);
				menu.setBloqueado(bloqueado);
				menu.setConstrucao(construcao);
				menu.setDescricao(descricao);
				menu.setInterno(interno);
				menu.setUrl(url);
				menu.setUrlAjuda(urlAjuda);
				return menu;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void logarUsuario(){
		setExibirMensagemUsuarioBloqueado(false);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try{
			if(!getUsuario().getLogin().isEmpty()){
				Usuario usuarioLogado = procurarUsuario(getUsuario().getLogin());
				if(usuarioLogado != null && usuarioLogado.getIdUsuario() != 0){
	    			if(verificaSenha(usuarioLogado, getUsuario().getSenha())){
	    				setUsuarioAtual(usuarioLogado);
	    				facesContext.getExternalContext().getSessionMap().put("usuario", usuarioLogado);
	    				setUsuarioAtual(usuarioLogado);
	    				new UsuarioAcessoLogRaiz().gerarLogLogin();
	    				carregaProfissional();
	    				carregaToolBarMenu();
	    				carregaPaineis();
	    				atualizarPaineis();
	    				ControlePainelAviso.getInstancia().atualizarAvisos();
	    			}
	    		}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			new UsuarioAcessoLogRaiz().gerarLog(TipoUsuarioLogEnum.E, getUsuario().getLogin());
		}
		
		setUsuario(new Usuario());
	}
	
	public void fecharFormularioAtual(){
		setExibirFormularioAtual(false);
	}
	
	public static Profissional getProfissionalLogado() throws ExcecaoProfissionalLogado{
		try {
			return Autenticador.getInstancia().getProfissionalAtual();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		throw new ExcecaoProfissionalLogado();
	}
	
	public static Autenticador getInstancia() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return (Autenticador) new ControleInstancia().procuraInstancia(Autenticador.class);
	}
	
	public void recuperarSenha(){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		String valor = Utilitarios.getPatternChaveUnicaPreparada(getProfissionalRecuperacao());
		valor = Utilitarios.encriptaParaMd5(valor);
		map.put("chaveVerificacao", valor);
		StringBuilder sb = new StringBuilder("select o from Profissional o where o.chaveVerificacao = :chaveVerificacao ");
		Profissional profRec = new ConsultaGeral<Profissional>(sb, map).consultaUnica();
		if(profRec != null && profRec.getIdProfissional() != 0){
			UsuarioRaiz ur = new UsuarioRaiz();
			profRec.getUsuario().setQuantidadeErroLogin(0);
			ur.setInstancia(profRec.getUsuario());
			ur.resetarSenha();
			setUsuario(profRec.getUsuario());
			getUsuario().setSenha(Constantes.SENHA_PADRAO);
			logarUsuario();
			UsuarioRaiz.getInstanciaAtual().setSenhaAntiga(Constantes.SENHA_PADRAO);
			UsuarioRaiz.getInstanciaAtual().setExibeCampoSenhaAntiga(false);
			setExibirMensagemUsuarioBloqueado(false);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_HOME);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Dados não conferem", ""));
		}
	}
	
	private Object buscaGenerica(String sql, HashMap<Object, Object> hm){
		ConsultaGeral<Object> cg = new ConsultaGeral<Object>();
		return cg.consultaUnica(new StringBuilder(sql), hm);
	}
	
	public Profissional profissionalPeloNomeUsuario(String nome, String senha){
		senha = Utilitarios.encriptaParaMd5(senha);
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
			new UsuarioAcessoLogRaiz().gerarLogLogout();
			setUsuarioAtual(new Usuario());
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
			session.invalidate();
			setUsuarioAtual(null);
		}catch (Exception e) {
			e.printStackTrace();
			if(getUsuarioAtual() != null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao tentar sair! Tente sair novamente.", "Logout não realizado!"));
			}
		}
	}
	
	public boolean isUsuarioAtivo(){
		return getUsuarioAtual() != null && getUsuarioAtual().getIdUsuario() != 0 ? true : false;
	}
	
	public Usuario procurarUsuario(String nome) throws ExcecaoUsuarioInativo, ExcecaoUsuarioLogin{
		ConsultaGeral<Usuario> cg = new ConsultaGeral<Usuario>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("login", nome.trim());
		Usuario resultado = cg.consultaUnica(new StringBuilder("select o from Usuario o where o.login = :login"), hm);
		if(resultado != null){
			if(resultado.getProfissional().getSituacao().equals(TipoSituacaoEnum.A))
				return resultado;
			else{
				throw new ExcecaoUsuarioInativo();
			}
		}else{
			throw new ExcecaoUsuarioLogin();
		}
	}

	public boolean verificaSenha(Usuario usuarioLogado, String senha){
		if(usuarioLogado.getSenha().equals(Utilitarios.encriptaParaMd5(senha))){
			return true;
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usu�rio e/ou senha n�o confere!", "Login n�o realizado!"));
			String sql = "update Usuario set quantidadeErroLogin = quantidadeErroLogin + 1 where login = '"+usuarioLogado.getLogin()+"'";
			new UsuarioRaiz().executa(sql);
			if(usuarioLogado.getQuantidadeErroLogin() == Constantes.QUANTIDADE_BLOQUEIO_USUARIO.intValue()){
				new UsuarioAcessoLogRaiz().gerarLog(usuarioLogado, TipoUsuarioLogEnum.B);
			}
			return false;
		}
	}
	
	private void atualizarPaineis(){
		ControlePainel cp = ControlePainel.getInstancia();
		if(cp.getPainelAutorizadoStringList().contains(Constantes.PAINEL_MEDICAMENTO_VENCIDO)){
			EstoqueRaiz.getInstanciaAtual().setEstoqueVencido(new EstoqueConsultaRaiz().consultarEstoqueVencidoLimiteSeteDias());
		}
		if(cp.getPainelAutorizadoStringList().contains(Constantes.PAINEL_SOLICITACOES_MEDICAMENTO_USUARIO)){
			new SolicitacaoMedicamentoUnidadeConsultaRaiz().consultarSolicitacoesProfissional();
		}
		
		if(cp.getPainelAutorizadoStringList().contains(Constantes.PAINEL_MATERIAL_ALMOXARIFADO_VENCIDO)){
			EstoqueAlmoxarifadoRaiz.getInstanciaAtual().setEstoqueVencido(new EstoqueAlmoxarifadoConsultaRaiz().consultarEstoqueVencidoEVenceraSeisMeses());
		}
	}
	
	public void sairManutencao(){
		StringBuilder sb = new StringBuilder("update Configuracao set valor = 'false' where ");
		sb.append("nome = 'Manutencao'");
		new ConfiguracaoRaiz().executa(sb.toString());
	}
	
	public boolean senhaResetada(){
		try {
			return new ControleSenha().senhaResetada();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean getUsuarioTrocarSenha(){
		try {
			return new ControleSenha().senhaIgualMatricula() || senhaResetada();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean precisaTrocarSenha(){
		try {
			return new ControleSenha().senhaIgualMatricula();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Usuario getUsuarioAtual() {
		return usuarioAtual;
	}

	public void setUsuarioAtual(Usuario usuarioAtual) {
		this.usuarioAtual = usuarioAtual;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Profissional getProfissionalAtual() {
		return profissionalAtual;
	}

	public void setProfissionalAtual(Profissional profissionalAtual) {
		this.profissionalAtual = profissionalAtual;
	}

	public Profissional getProfissionalRecuperacao() {
		return profissionalRecuperacao;
	}

	public void setProfissionalRecuperacao(Profissional profissionalRecuperacao) {
		this.profissionalRecuperacao = profissionalRecuperacao;
	}

	public boolean getExibirFormularioAtual() {
		return exibirFormularioAtual;
	}

	public void setExibirFormularioAtual(boolean exibirFormularioAtual) {
		this.exibirFormularioAtual = exibirFormularioAtual;
	}

	public boolean getExibirMensagemUsuarioBloqueado() {
		return exibirMensagemUsuarioBloqueado;
	}

	public void setExibirMensagemUsuarioBloqueado(
			boolean exibirMensagemUsuarioBloqueado) {
		this.exibirMensagemUsuarioBloqueado = exibirMensagemUsuarioBloqueado;
	}

}
