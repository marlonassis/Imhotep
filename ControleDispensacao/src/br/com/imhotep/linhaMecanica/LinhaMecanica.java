package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.imhotep.entidade.Especialidade;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.Menu;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoFusaoNaoRealizada;
import br.com.imhotep.excecoes.ExcecaoProfissionalNaoEncontrado;
import br.com.imhotep.seguranca.Autenticador;

public class LinhaMecanica extends GerenciadorMecanico {
	
	public LinhaMecanica(){
		super();
	}
	
	public LinhaMecanica(String banco){
		super();
		setNomeBanco(banco);
	}
	
	public LinhaMecanica(String banco, String ip){
		super();
		setNomeBanco(banco);
		setIp(ip);
	}
	
	public static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public boolean executarCUD(String sql){
		return super.executarQuery(sql);
	}
	
	private Profissional getProfissionalAtual() throws ExcecaoProfissionalNaoEncontrado{
		try {
			return Autenticador.getInstancia().getProfissionalAtual();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		};
		throw new ExcecaoProfissionalNaoEncontrado();
	}
	
	public void removerAutorizacaoMenu(Profissional profissional, Menu menu){
		setNomeBanco(DB_BANCO_IMHOTEP);
		removerFilhos(profissional, menu.getMenusFilho());
		String sql = "delete from tb_autoriza_menu_profissional where id_profissional = "+profissional.getIdProfissional()+" and " +
				"id_menu = "+menu.getIdMenu();
		executarQuery(sql);
	}
	
	public void removerAutorizacaoMenu(Especialidade especialidade, Menu menu){
		setNomeBanco(DB_BANCO_IMHOTEP);
		removerFilhos(especialidade, menu.getMenusFilho());
		String sql = "delete from tb_autoriza_menu where id_especialidade = "+especialidade.getIdEspecialidade()+" and " +
				"id_menu = "+menu.getIdMenu();
		executarQuery(sql);
	}
	
	private void removerFilhos(Profissional profissional, List<Menu> filhos) {
		for(Menu filho : filhos){
			if(filho.getMenusFilho() != null && !filho.getMenusFilho().isEmpty())
				removerFilhos(profissional, filho.getMenusFilho());
			String sql = "delete from tb_autoriza_menu_profissional where id_profissional = "+profissional.getIdProfissional()+" and " +
					"id_menu = "+filho.getIdMenu();
			executarQuery(sql);
		}
	}
	
	private void removerFilhos(Especialidade especialidade, List<Menu> filhos) {
		for(Menu filho : filhos){
			if(filho.getMenusFilho() != null && !filho.getMenusFilho().isEmpty())
				removerFilhos(especialidade, filho.getMenusFilho());
			String sql = "delete from tb_autoriza_menu where id_especialidade = "+especialidade.getIdEspecialidade()+" and " +
					"id_menu = "+filho.getIdMenu();
			executarQuery(sql);
		}
	}
	
	public void inserirAutorizacaoMenu(Profissional profissional, Menu menu) throws ExcecaoProfissionalNaoEncontrado{
		setNomeBanco(DB_BANCO_IMHOTEP);
		liberarPaisMenu(profissional, menu);
		liberarFilhosMenu(profissional, menu.getMenusFilho());
	}
	
	public void inserirAutorizacaoMenu(Especialidade especialidade, Menu menu){
		setNomeBanco(DB_BANCO_IMHOTEP);
		liberarPaisMenu(especialidade, menu);
		liberarFilhosMenu(especialidade, menu.getMenusFilho());
	}
	
	private void liberarFilhosMenu(Profissional profissional, List<Menu> filhos) throws ExcecaoProfissionalNaoEncontrado {
		for(Menu filho : filhos){
			if(filho.getMenusFilho() != null && !filho.getMenusFilho().isEmpty())
				liberarFilhosMenu(profissional, filho.getMenusFilho());
			String sql = "insert into tb_autoriza_menu_profissional (id_profissional, id_menu, id_profissional_insercao, dt_data_criacao) " +
					"values ("+profissional.getIdProfissional()+", "+filho.getIdMenu()+", "+getProfissionalAtual().getIdProfissional() + ", '"+new SimpleDateFormat().format(new Date())+"')";
			executarQuery(sql);
		}
	}
	
	private void liberarFilhosMenu(Especialidade especialidade, List<Menu> filhos) {
		for(Menu filho : filhos){
			if(filho.getMenusFilho() != null && !filho.getMenusFilho().isEmpty())
				liberarFilhosMenu(especialidade, filho.getMenusFilho());
			String sql = "insert into tb_autoriza_menu (id_especialidade, id_menu) " +
					"values ("+especialidade.getIdEspecialidade()+", "+filho.getIdMenu()+")";
			executarQuery(sql);
		}
	}
	
	private void liberarPaisMenu(Profissional profissional, Menu menu) throws ExcecaoProfissionalNaoEncontrado {
		if(menu.getMenuPai() != null)
			liberarPaisMenu(profissional, menu.getMenuPai());
		String sql = "insert into tb_autoriza_menu_profissional (id_profissional, id_menu, id_profissional_insercao, dt_data_criacao) " +
				"values ("+profissional.getIdProfissional()+", "+menu.getIdMenu()+", "+getProfissionalAtual().getIdProfissional() + ", '"+new SimpleDateFormat().format(new Date())+"')";
		executarQuery(sql);
		
	}
	
	private void liberarPaisMenu(Especialidade especialidade, Menu menu) {
		if(menu.getMenuPai() != null)
			liberarPaisMenu(especialidade, menu.getMenuPai());
		String sql = "insert into tb_autoriza_menu (id_especialidade, id_menu) " +
				"values ("+especialidade.getIdEspecialidade()+", "+menu.getIdMenu()+")";
		executarQuery(sql);
		
	}
	
	public boolean apagarMovimentoLivroEstoque(int idEstoque){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "delete from tb_movimento_livro where id_estoque = "+idEstoque;
		return executarQuery(sql);
	}
	
	private void executarFluxoFusao(int idEstoqueOrigem, int idEstoqueDestino) throws ExcecaoFusaoNaoRealizada{
		String sql = "update tb_movimento_livro set id_estoque = "+idEstoqueDestino+" where id_estoque = "+idEstoqueOrigem;
		super.getFluxo().put("transfereMovimentacaoEstoque", sql);
		
		sql = "delete from tb_estoque where id_estoque = "+idEstoqueOrigem;
		super.getFluxo().put("apagarEstoque", sql);
		
		if(!executarQueryFluxo())
			throw new ExcecaoFusaoNaoRealizada();

	}
	
	private void colocarEstoqueEmLockParaFusao(int idEstoqueOrigem, int idEstoqueDestino) throws SQLException, ExcecaoEstoqueLock{
		if(verificarLockParaFusao(idEstoqueOrigem, idEstoqueDestino)){
			String sql = "update tb_estoque set bl_lock = true where id_estoque = "+idEstoqueOrigem+" or id_estoque = "+idEstoqueDestino;
			if(!executarQuery(sql)){
				throw new ExcecaoEstoqueLock();
			}
		}else{
			throw new ExcecaoEstoqueLock();
		}
	}

	private boolean verificarLockParaFusao(int idEstoqueOrigem, int idEstoqueDestino) throws SQLException {
		boolean unlock = false;
		int cont = 0;
		setNomeBanco(DB_BANCO_IMHOTEP);
		while(!unlock && cont < 4){
			ResultSet rs = consultar(utf8_to_latin1("select bl_lock from tb_estoque where (id_estoque = "+idEstoqueOrigem+" or id_estoque = "+idEstoqueDestino+") and bl_lock is true"));
			if(rs.next()){
				unlock = false;
				//TODO colocar o sleep;
			}else{
				unlock = true;
			}
			cont++;
		}
		return unlock;
	}
	
	public void fluxoFusaoEstoque(int idEstoqueOrigem, int idEstoqueDestino) throws ExcecaoEstoqueLock{
		setNomeBanco(DB_BANCO_IMHOTEP);
		boolean ocorreuErroFluxo = false;

		try {
			colocarEstoqueEmLockParaFusao(idEstoqueOrigem, idEstoqueDestino);
		} catch (Exception e1) {
			e1.printStackTrace();
			ocorreuErroFluxo = true;
			tirarEstoqueLock(idEstoqueOrigem, idEstoqueDestino, ocorreuErroFluxo);
		}
		
		
		try {
			executarFluxoFusao(idEstoqueOrigem, idEstoqueDestino);
		} catch (ExcecaoFusaoNaoRealizada e) {
			e.printStackTrace();
			ocorreuErroFluxo = true;
			tirarEstoqueLock(idEstoqueOrigem, idEstoqueDestino, ocorreuErroFluxo);
		}
		
		tirarEstoqueLock(idEstoqueOrigem, idEstoqueDestino, ocorreuErroFluxo);
		
	}

	private void tirarEstoqueLock(int idEstoqueOrigem, int idEstoqueDestino, boolean ocorreuErroFluxo) throws ExcecaoEstoqueLock {
		String sql = "update tb_estoque set bl_lock = false where id_estoque = "+idEstoqueOrigem+" or id_estoque = "+idEstoqueDestino;
		if(executarQuery(sql))
			if(!ocorreuErroFluxo){
				atualizarQuantidadeEstoque(idEstoqueDestino);
			}else{
				throw new ExcecaoEstoqueLock();
			}
	}
	
	public boolean apagarEstoque(int idEstoque){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "delete from tb_estoque where id_estoque = "+idEstoque;
		return executarQuery(sql);
	}
	
	public void transfereMovimentacaoEstoque(int idEstoqueOrigem, int idEstoqueDestino){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "update tb_movimento_livro set id_estoque = "+idEstoqueDestino+" where id_estoque = "+idEstoqueOrigem;
		executarQuery(sql);
	}
	
	public void salvarLogSessaoDestruida(Date dataLog, String idSessao, Date dataUltimoMovimentoSessao, int tempoSessao){
		try {
			setNomeBanco(DB_BANCO_IMHOTEP);
			ResultSet rs = consultar(utf8_to_latin1("select cv_sessao from tb_usuario_acesso_log where cv_sessao = '"+idSessao+"'"));
			while (rs.next()) { 
				String sql = "insert into tb_usuario_acesso_log (dt_data_log, tp_tipo_log, cv_sessao, dt_data_ultimo_movimento_sessao, in_tempo_sessao) " +
						"values ('"+new SimpleDateFormat().format(dataLog)+"','A', '"+idSessao+"', '"+new SimpleDateFormat().format(dataUltimoMovimentoSessao)+"', " +
								tempoSessao+")";
				executarQuery(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
//		LinhaMecanica lm = new LinhaMecanica();
//		lm.setIp("127.0.0.1");
//		lm.setNomeBanco("db_ehealth");
//		ResultSet rs = lm.consultar("select a.id_estabelecimento, a.cv_link, a.cv_natureza, a.cv_razao_social, a.cv_endereco, a.cv_link_detalhado, a.cv_nome, b.cv_nome municipio, c.cv_nome as estado from tb_estabelecimento a " +
//									"inner join tb_municipio b on b.id_municipio = a.id_municipio " +
//									"inner join tb_estados c on c.id_estados = b.id_estado order by a.id_estabelecimento");
//		lm.setNomeBanco("db_imhotep");
//		int i = 0;
//		while(rs.next()){
//			String nome = rs.getString("cv_nome");
//			String link = rs.getString("cv_link");
//			String natureza = rs.getString("cv_natureza");
//			String razaoSocial = rs.getString("cv_razao_social");
//			String endereco = rs.getString("cv_endereco");
//			String linkDetalhado = rs.getString("cv_link_detalhado");
//			int idEstabelecimento = rs.getInt("id_estabelecimento");
//			String municipio = rs.getString("municipio");
//			String estado = rs.getString("estado");
//			
//			String sql = "insert into tb_ehealth_estabelecimento (cv_nome, cv_natureza, cv_razao_social, cv_endereco, cv_link, cv_link_detalhado, id_ehealth_municipio) " +
//					"values ("+adicionaAspas(nome)+", "+adicionaAspas(natureza)+", "+adicionaAspas(razaoSocial)+", "+adicionaAspas(endereco)+","+adicionaAspas(link)+", "+adicionaAspas(linkDetalhado)+",  " +
//							"( select a.id_ehealth_municipio from tb_ehealth_municipio a inner join tb_ehealth_estado b on b.cv_nome = '"+estado+"' and a.id_ehealth_estado = b.id_ehealth_estado where a.cv_nome='"+municipio+"'));";
//			System.out.println(sql);
//			if(!lm.executarCUD(sql)){
//				System.out.println("erro");
//				System.exit(1);
//			}
//			i++;
//			System.out.println(i+"/261994 : "+ idEstabelecimento);
//		}
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "insert into tb_movimento_livro (id_unidade_cadastrante, id_tipo_movimento, dt_data_movimento, " +
				"id_usuario_movimentacao, id_estoque, in_quantidade_movimentacao) values(2, 23, now(), 10, 522, 5)";
		int i = 0;
		while(i < 100000){
			System.out.println(i);
			lm.executarCUD(sql);
			i++;
		}
		
//		LinhaMecanica lm = new LinhaMecanica();
//		lm.setIp("200.133.41.8");
//		lm.atualizarQuantidadeEstoque();
		
	}

	private static String adicionaAspas(String valor){
		if(valor == null){
			return null;
		}
		
		return "'".concat(valor).concat("'");
	}
	
	private void atualizarQuantidadeEstoque() throws SQLException{
		setNomeBanco(DB_BANCO_IMHOTEP);
		ResultSet rs = consultar(utf8_to_latin1("select id_estoque from tb_estoque order by id_estoque"));
		while (rs.next()) { 
			int idEstoque = rs.getInt(1);
			atualizarQuantidadeEstoque(idEstoque);
		}
	}
	
	private void atualizarQuantidadeEstoque(int idEstoque) {
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "update tb_estoque  set in_quantidade_atual = " +
					"( " +
					"select " +
					"( " +
					"coalesce((select sum(a.in_quantidade_movimentacao) from tb_movimento_livro a " + 
					"inner join tb_tipo_movimento d on d.id_tipo_movimento = a.id_tipo_movimento " +
					"where a.id_estoque = "+ idEstoque + " and d.tp_operacao = 'E'), 0) " +
					"- "+
					"coalesce((select sum(b.in_quantidade_movimentacao) from tb_movimento_livro b " + 
					"inner join tb_tipo_movimento e on e.id_tipo_movimento = b.id_tipo_movimento " +
					"where b.id_estoque = "+ idEstoque + " and e.tp_operacao != 'E'), 0) " +
					")) " +
					"where id_estoque = "+ idEstoque;
		System.out.println(idEstoque);
		if(!executarCUD(sql)){
			System.exit(1);
		}
	}
	
	public List<Estoque> estoquePorMaterial(int idMaterial){
		try {
			setNomeBanco(DB_BANCO_IMHOTEP);
			ResultSet rs = consultar(utf8_to_latin1("select id_estoque, cv_lote from tb_estoque " +
					"where id_material = "+idMaterial));
			List<Estoque> list = new ArrayList<Estoque>();
			while (rs.next()) { 
				int idEstoque = rs.getInt(1);
				String lote = rs.getString(2);
				Estoque estoque = new Estoque();
				estoque.setIdEstoque(idEstoque);
				estoque.setLote(lote);
				list.add(estoque);
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
