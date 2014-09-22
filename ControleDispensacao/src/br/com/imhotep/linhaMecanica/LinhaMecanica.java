package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.imhotep.auxiliar.Utilitarios;
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
	
	public boolean fastExecutarCUD(String sql){
		return super.fastExecutarQuery(sql);
	}
	
	public boolean executarCUD(String sql){
		return super.executarQuery(sql);
	}
	
	
	public List<Object[]> getListaResultadoFast(String sql){
		String[] res = sql.substring(sql.indexOf("select")+6, sql.indexOf("from")-1).split(",");
		return getListaResultadoFast(sql, res.length);
	}
	
	public List<Object[]> getListaResultadoFast(String sql, int quantidadeColunas){
		ResultSet rs = super.fastConsulta(sql);
		return consultar(quantidadeColunas, rs);
	}
	
	public List<Object[]> getListaResultado(String sql){
		sql = sql.toLowerCase();
		String[] res = sql.substring(sql.indexOf("select")+6, sql.indexOf("from")-1).split(",");
		return getListaResultado(sql, res.length);
	}
	
	public List<Object[]> getListaResultado(String sql, int quantidadeColunas){
		ResultSet rs = super.consultar(sql);
		return consultar(quantidadeColunas, rs);
	}

	
	private List<Object[]> consultar(int quantidadeColunas, ResultSet rs) {
		List<Object[]> res = new ArrayList<Object[]>();
		try {
			while(rs.next()){
				Object[] array=null;
				for(int cont = 1;cont <= quantidadeColunas;cont++){
					array = Utilitarios.addElemento(array, rs.getObject(cont));
				}
				res.add(array);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
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
	
	public void unLockEstoqueAlmoxarifado(Integer id){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "update tb_estoque_almoxarifado set dt_data_lock = null, bl_lock = false where id_estoque_almoxarifado = "+id;
		executarQuery(sql);
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
	
	public static void main(String[] args) {
		
		String ip = "200.133.41.8";
		String dbBancoImhotep = "db_imhotep";//DB_BANCO_IMHOTEP;
		
		
//		LinhaMecanica lm = new LinhaMecanica();
//		try {
//			String sqlEstoque = "select id_estoque, cv_lote from tb_estoque order by id_estoque";
//			lm.setNomeBanco(dbBancoImhotep);
//			lm.setIp(ip);
//			lm.criarConexao();
//			ResultSet rs = lm.fastConsulta(lm.utf8_to_latin1(sqlEstoque));
//			while (rs.next()) { 
//				int idEstoque = rs.getInt("id_estoque");
//				String lote = rs.getString("cv_lote");
//				if(lote != null){
//					lote = lote.trim().toUpperCase();
//					if(lote.isEmpty() || lote.equalsIgnoreCase("NULL"))
//						lote = null;
//					else
//						lote = "'"+lote+"'";
//				}else{
//					lote = null;
//				}
//				String update = "update tb_estoque set cv_lote = "+lote+" where id_estoque = " + idEstoque;
//				System.out.println(update);
//				if(!lm.fastExecutarCUD(update)){
//					System.out.println("erro!");
//					System.exit(1);
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			lm.fecharConexoes();
//		}
		
		
		
		
		//verifica se a diferença dos movimentos está diferente do estoque atual
		movimentacoesDiferenteEstoqueAtualFarmacia(ip, dbBancoImhotep);
		
		//verifica se algum estoque não tem o primeiro movimento como entrada
		verificaEstoqueSemPrimeiroMovimentoEntradaFarmacia(ip, dbBancoImhotep);
		
		//verificação de movimento de medicamento vencido
//		verificarMovimentacaoMedicamentoVencidoFarmacia(ip, dbBancoImhotep);
		
		//script para verificar se algum estoque do ALMOXARIFADO não possui o primeiro movimento como entrada
		verificarEstoqueSemPrimeiroMovimentoEntradaAlmoxarifado(ip, dbBancoImhotep);
		
		
		//script para verificar se as movimentaç√ïes do almoxarifado não correspondem à quantidade em estoque
		verificarMovimentacoesNaoCorrespondemQuantidadeEstoqueAlmoxarifado(ip, dbBancoImhotep);
		
	}

	private static void verificarMovimentacoesNaoCorrespondemQuantidadeEstoqueAlmoxarifado(String ip, String dbBancoImhotep) {
		try {
			System.out.println("Script para verificar se as movimentações do almoxarifado não correspondem à quantidade em estoque...");
			String sqlEstoque = "select id_estoque_almoxarifado from tb_estoque_almoxarifado";
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(dbBancoImhotep);
			lm.setIp(ip);
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlEstoque));
			while (rs.next()) { 
				int idEstoque = rs.getInt("id_estoque_almoxarifado");
				String sql2 = "select b.id_estoque_almoxarifado, b.in_quantidade_atual, b.cv_lote lote, d.cv_descricao material, " +
						"(select sum(a.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado a  " +
						 "inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado " + 
						 " where b.tp_operacao = 'E' and a.id_estoque_almoxarifado = "+idEstoque+") as entrada,  " +
						"(select sum(c.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado c " +
						 "inner join tb_tipo_movimento_almoxarifado d on d.id_tipo_movimento_almoxarifado = c.id_tipo_movimento_almoxarifado " + 
						  "where d.tp_operacao != 'E' and c.id_estoque_almoxarifado = "+idEstoque+") as saida " +
						"from tb_estoque_almoxarifado b " +
						"inner join tb_material_almoxarifado d on d.id_material_almoxarifado = b.id_material_almoxarifado where b.id_estoque_almoxarifado = "+idEstoque;
				ResultSet rs2 = lm.consultar(lm.utf8_to_latin1(sql2));
				while (rs2.next()) { 
					int idEstoque2 = rs2.getInt("id_estoque_almoxarifado");
					int quantidade = rs2.getInt("in_quantidade_atual");
					String lote = rs2.getString("lote");
					String material = rs2.getString("material");
					int entrada = rs2.getInt("entrada");
					int saida = rs2.getInt("saida");
					if((entrada-saida) != quantidade){
						System.out.println("D - Estoque: "+idEstoque2+" - "+lote+"\nMaterial: "+material+"\nTotal: "+entrada+" - "+saida+" = "+(entrada-saida)+"//"+quantidade+"\n‚Ä¢‚Ä¢‚Ä¢‚Ä¢‚Ä¢‚Ä¢‚Ä¢‚Ä¢");
					}
				}
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	
	private static void verificarEstoqueSemPrimeiroMovimentoEntradaAlmoxarifado(String ip, String dbBancoImhotep) {
		try {
			System.out.println("Script para verificar se algum estoque do ALMOXARIFADO não possui o primeiro movimento como entrada...");
			String sqlEstoque = "select * from tb_estoque_almoxarifado";
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(dbBancoImhotep);
			lm.setIp(ip);
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlEstoque));
			while (rs.next()) { 
				int idEstoque = rs.getInt("id_estoque_almoxarifado");
				String sqlMovimentacao = "select b.tp_operacao op, a.id_estoque_almoxarifado estoque from tb_movimento_livro_almoxarifado a "+ 
											"inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "+
											"where a.id_estoque_almoxarifado = "+idEstoque+
											" and  "+
											"a.dt_data_movimento = ( "+
											"select min(b.dt_data_movimento) from tb_movimento_livro_almoxarifado b where a.id_estoque_almoxarifado = b.id_estoque_almoxarifado "+
											")";
				ResultSet rs2 = lm.consultar(lm.utf8_to_latin1(sqlMovimentacao));
				while (rs2.next()) { 
					String op = rs2.getString("op");
					int id = rs2.getInt("estoque");
					if(!op.equals("E")){
						System.out.println("C - op: "+op+" - ID: "+id);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void verificarMovimentacaoMedicamentoVencidoFarmacia(String ip, String dbBancoImhotep) {
		try {
			System.out.println("Script para verificar se algum estoque da FARMÁCIA possui movimentação de estoque vencido...");
			String sqlEstoque = "select a.id_movimento_livro, a.dt_data_movimento, b.dt_data_validade, b.id_estoque, b.cv_lote, a.cv_justificativa from tb_movimento_livro a "+
								"inner join tb_estoque b on a.id_estoque = b.id_estoque "+
								"where a.dt_data_movimento > b.dt_data_validade "+
								"order by a.dt_data_movimento";
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(dbBancoImhotep);
			lm.setIp(ip);
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlEstoque));
			while (rs.next()) { 
				int idMovimento = rs.getInt("id_movimento_livro");
				Date dataMovimento = rs.getTimestamp("dt_data_movimento");
				int idEstoque = rs.getInt("id_estoque");
				Date dataValidade = rs.getDate("dt_data_validade");
				String lote = rs.getString("cv_lote");
				String justificativa = rs.getString("cv_justificativa");
				System.out.println("-------------------");
				System.out.println("ID Movimento: "+idMovimento);
				System.out.println("Data Movimento: "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataMovimento));
				System.out.println("ID Estoque: "+idEstoque);
				System.out.println("Lote: "+lote);
				System.out.println("Data Validade: "+new SimpleDateFormat("dd/MM/yyyy").format(dataValidade));
				System.out.println("Justificativa Movimento: "+justificativa);
				System.out.println("-------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void verificaEstoqueSemPrimeiroMovimentoEntradaFarmacia(String ip, String dbBancoImhotep) {
		try {
			System.out.println("Verifica se algum estoque não tem o primeiro movimento como entrada...");
			String sqlEstoque = "select * from tb_estoque";
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(dbBancoImhotep);
			lm.setIp(ip);
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlEstoque));
			while (rs.next()) { 
				int idEstoque = rs.getInt("id_estoque");
				String sqlMovimentacao = "select b.tp_operacao op, a.id_estoque estoque from tb_movimento_livro a "+ 
											"inner join tb_tipo_movimento b on a.id_tipo_movimento = b.id_tipo_movimento "+
											"where a.id_estoque = "+idEstoque+
											" and  "+
											"a.dt_data_movimento = ( "+
											"select min(b.dt_data_movimento) from tb_movimento_livro b where a.id_estoque = b.id_estoque "+
											")";
				ResultSet rs2 = lm.consultar(lm.utf8_to_latin1(sqlMovimentacao));
				while (rs2.next()) { 
					String op = rs2.getString("op");
					int id = rs2.getInt("estoque");
					if(!op.equals("E")){
						System.out.println("B - op: "+op+" - ID: "+id);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void movimentacoesDiferenteEstoqueAtualFarmacia(String ip, String dbBancoImhotep) {
		try {
			System.out.println("Verificando se a diferença dos movimentos está diferente do estoque atual...");
			String sqlEstoque = "select id_estoque from tb_estoque";
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(dbBancoImhotep);
			lm.setIp(ip);
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlEstoque));
			while (rs.next()) { 
				int idEstoque = rs.getInt("id_estoque");
				String sql2 = "select b.id_estoque, b.in_quantidade_atual, b.cv_lote lote, d.cv_descricao material, " +
						"(select sum(a.in_quantidade_movimentacao) from tb_movimento_livro a  " +
						 "inner join tb_tipo_movimento b on a.id_tipo_movimento = b.id_tipo_movimento " + 
						 " where b.tp_operacao = 'E' and a.id_estoque = "+idEstoque+") as entrada,  " +
						"(select sum(c.in_quantidade_movimentacao) from tb_movimento_livro c " +
						 "inner join tb_tipo_movimento d on d.id_tipo_movimento = c.id_tipo_movimento " + 
						  "where d.tp_operacao != 'E' and c.id_estoque = "+idEstoque+") as saida " +
						"from tb_estoque b " +
						"inner join tb_material d on d.id_material = b.id_material where b.id_estoque = "+idEstoque;
				ResultSet rs2 = lm.consultar(lm.utf8_to_latin1(sql2));
				while (rs2.next()) { 
					int idEstoque2 = rs2.getInt("id_estoque");
					int quantidade = rs2.getInt("in_quantidade_atual");
					String lote = rs2.getString("lote");
					String material = rs2.getString("material");
					int entrada = rs2.getInt("entrada");
					int saida = rs2.getInt("saida");
					if((entrada-saida) != quantidade){
						System.out.println("A - Estoque: "+idEstoque2+" - "+lote+"\nMaterial: "+material+"\nTotal: "+entrada+" - "+saida+" = "+(entrada-saida)+"//"+quantidade+"\n‚Ä¢‚Ä¢‚Ä¢‚Ä¢‚Ä¢‚Ä¢‚Ä¢‚Ä¢");
					}
				}
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	private void inserirProfissionalEspecialidade() throws SQLException{
		setNomeBanco(DB_BANCO_IMHOTEP);
		ResultSet rs = consultar(utf8_to_latin1("select id_profissional, id_especialidade from tb_profissional order by id_profissional"));
		while (rs.next()) { 
			int idProfissional = rs.getInt(1);
			int idEspecialidade = rs.getInt(2);
			String sql = "insert into tb_profissional_especialidade (id_profissional, id_especialidade) values("+idProfissional+","+idEspecialidade+");";
			System.out.println(idProfissional);
			if(!executarCUD(sql))
				System.exit(1);
		}
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
