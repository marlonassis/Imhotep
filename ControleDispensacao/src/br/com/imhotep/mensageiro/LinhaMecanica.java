package br.com.imhotep.mensageiro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.enums.TipoSituacaoEnum;
import br.com.imhotep.excecoes.ExcecaoFusaoNaoRealizada;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.mensageiro.entidade.Mensagem;
import br.com.imhotep.mensageiro.entidade.Usuario;
import br.com.imhotep.seguranca.Autenticador;

public class LinhaMecanica extends GerenciadorMecanico {
	
//	public LinhaMecanica(){
//		super();
//	}
//	
//	public LinhaMecanica(String banco){
//		super();
//		setNomeBanco(banco);
//	}
//	
//	public LinhaMecanica(String banco, String ip){
//		super();
//		setNomeBanco(banco);
//		setIp(ip);
//	}
//	
//	public static final String DB_BANCO_IMHOTEP_MENSAGEIRO = "db_imhotep_mensageiro";
//	
//	private Integer getIdUsuario(){
//		try {
//			return Autenticador.getProfissionalLogado().getUsuario().getIdUsuario();
//		} catch (ExcecaoProfissionalLogado e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
//	
//	public boolean executarCUD(String sql){
//		return super.executarQuery(sql);
//	}
//	
//	public boolean apagarMovimentoLivroEstoque(int idEstoque){
//		setNomeBanco(DB_BANCO_IMHOTEP);
//		String sql = "delete from tb_movimento_livro where id_estoque = "+idEstoque;
//		return executarQuery(sql);
//	}
//	
//	private void executarFluxoFusao(int idEstoqueOrigem, int idEstoqueDestino) throws ExcecaoFusaoNaoRealizada{
//		String sql = "update tb_movimento_livro set id_estoque = "+idEstoqueDestino+" where id_estoque = "+idEstoqueOrigem;
//		super.getFluxo().put("transfereMovimentacaoEstoque", sql);
//		
//		sql = "delete from tb_estoque where id_estoque = "+idEstoqueOrigem;
//		super.getFluxo().put("apagarEstoque", sql);
//		
//		if(!executarQueryFluxo())
//			throw new ExcecaoFusaoNaoRealizada();
//
//	}
//	
//	public List<Usuario> getUsuarios() throws SQLException {
//		setNomeBanco(DB_BANCO_IMHOTEP_MENSAGEIRO);
//		int cont = 0;
//		List<Usuario> usuarios = new ArrayList<Usuario>();
//		ResultSet rs = consultar(utf8_to_latin1("select * from tb_usuario a "));
//		while(rs.next()){
//			cont++;
//			Usuario usuario = new Usuario();
//			usuario.setApelido(rs.getString("cv_apelido"));
//			usuario.setNome(rs.getString("cv_nome"));
//			usuario.setIdUsuario(rs.getInt("id_usuario"));
//			usuarios.add(usuario);
//		}
//		return usuarios;
//	}
//	
//	public List<Usuario> getUsuarios(String nome) throws SQLException {
//		setNomeBanco(DB_BANCO_IMHOTEP_MENSAGEIRO);
//		int cont = 0;
//		List<Usuario> usuarios = new ArrayList<Usuario>();
//		ResultSet rs = consultar(utf8_to_latin1("select * from tb_usuario a "
//				+ "where a.cv_nome ilike '%"+nome+"%' or a.cv_apelido ilike '%"+nome+"%' order by a.cv_nome, a.cv_apelido"));
//		while(rs.next()){
//			cont++;
//			Usuario usuario = new Usuario();
//			usuario.setApelido(rs.getString("cv_apelido"));
//			usuario.setNome(rs.getString("cv_nome"));
//			usuario.setIdUsuario(rs.getInt("id_usuario"));
//			usuarios.add(usuario);
//		}
//		return usuarios;
//	}
//	
//	public List<Mensagem> getMensagensUsuario() throws SQLException {
//		setNomeBanco(DB_BANCO_IMHOTEP_MENSAGEIRO);
//		int cont = 0;
//		List<Mensagem> mensagens = new ArrayList<Mensagem>();
//		ResultSet rs = consultar(utf8_to_latin1("select b.*, c.* from tb_mensagem_destinatario a "
//				+ "inner join tb_mensagem b on a.id_mensagem = b.id_mensagem "
//				+ "inner join tb_usuario c on c.id_usuario = b.id_usuario "
//				+ "where a.id_usuario_destinatario = "+getIdUsuario()+" and id_mensagem_pai is null order by b.bl_mensagem_lida, b.dt_data_envio desc"));
//		while(rs.next()){
//			cont++;
//			Mensagem mensagem = new Mensagem();
//			mensagem.setAssunto(rs.getString("cv_assunto"));
//			mensagem.setConteudo(rs.getString("tx_conteudo"));
//			mensagem.setDataCriacao(rs.getDate("dt_data_envio"));
//			mensagem.setDataEnvio(rs.getDate("dt_data_criacao"));
//			mensagem.setIdMensagem(rs.getInt("id_mensagem"));
//			mensagem.setMensagemLida(rs.getBoolean("bl_mensagem_lida"));
//			mensagem.setStatus(TipoSituacaoEnum.valueOf(rs.getString("tp_status")));
//			Usuario usuario = new Usuario();
//			usuario.setApelido(rs.getString("cv_apelido"));
//			usuario.setNome(rs.getString("cv_nome"));
//			usuario.setIdUsuario(rs.getInt("id_usuario"));
//			mensagem.setUsuario(usuario);
//			mensagens.add(mensagem);
//		}
//		return mensagens;
//	}
//	
//	
//	public boolean apagarEstoque(int idEstoque){
//		setNomeBanco(DB_BANCO_IMHOTEP);
//		String sql = "delete from tb_estoque where id_estoque = "+idEstoque;
//		return executarQuery(sql);
//	}
//	
//	public void transfereMovimentacaoEstoque(int idEstoqueOrigem, int idEstoqueDestino){
//		setNomeBanco(DB_BANCO_IMHOTEP);
//		String sql = "update tb_movimento_livro set id_estoque = "+idEstoqueDestino+" where id_estoque = "+idEstoqueOrigem;
//		executarQuery(sql);
//	}
//	
//	public void salvarLogSessaoDestruida(Date dataLog, String idSessao, Date dataUltimoMovimentoSessao, int tempoSessao){
//		try {
//			setNomeBanco(DB_BANCO_IMHOTEP);
//			ResultSet rs = consultar(utf8_to_latin1("select cv_sessao from tb_usuario_acesso_log where cv_sessao = '"+idSessao+"'"));
//			while (rs.next()) { 
//				String sql = "insert into tb_usuario_acesso_log (dt_data_log, tp_tipo_log, cv_sessao, dt_data_ultimo_movimento_sessao, in_tempo_sessao) " +
//						"values ('"+new SimpleDateFormat().format(dataLog)+"','A', '"+idSessao+"', '"+new SimpleDateFormat().format(dataUltimoMovimentoSessao)+"', " +
//								tempoSessao+")";
//				executarQuery(sql);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void inserirProfissionalEspecialidade() throws SQLException{
//		setNomeBanco(DB_BANCO_IMHOTEP);
//		ResultSet rs = consultar(utf8_to_latin1("select id_profissional, id_especialidade from tb_profissional order by id_profissional"));
//		while (rs.next()) { 
//			int idProfissional = rs.getInt(1);
//			int idEspecialidade = rs.getInt(2);
//			String sql = "insert into tb_profissional_especialidade (id_profissional, id_especialidade) values("+idProfissional+","+idEspecialidade+");";
//			System.out.println(idProfissional);
//			if(!executarCUD(sql))
//				System.exit(1);
//		}
//	}
//	
//	private void atualizarQuantidadeEstoque() throws SQLException{
//		setNomeBanco(DB_BANCO_IMHOTEP);
//		ResultSet rs = consultar(utf8_to_latin1("select id_estoque from tb_estoque order by id_estoque"));
//		while (rs.next()) { 
//			int idEstoque = rs.getInt(1);
//			atualizarQuantidadeEstoque(idEstoque);
//		}
//	}
//	
//	private void atualizarQuantidadeEstoque(int idEstoque) {
//		setNomeBanco(DB_BANCO_IMHOTEP);
//		String sql = "update tb_estoque  set in_quantidade_atual = " +
//					"( " +
//					"select " +
//					"( " +
//					"coalesce((select sum(a.in_quantidade_movimentacao) from tb_movimento_livro a " + 
//					"inner join tb_tipo_movimento d on d.id_tipo_movimento = a.id_tipo_movimento " +
//					"where a.id_estoque = "+ idEstoque + " and d.tp_operacao = 'E'), 0) " +
//					"- "+
//					"coalesce((select sum(b.in_quantidade_movimentacao) from tb_movimento_livro b " + 
//					"inner join tb_tipo_movimento e on e.id_tipo_movimento = b.id_tipo_movimento " +
//					"where b.id_estoque = "+ idEstoque + " and e.tp_operacao != 'E'), 0) " +
//					")) " +
//					"where id_estoque = "+ idEstoque;
//		System.out.println(idEstoque);
//		if(!executarCUD(sql)){
//			System.exit(1);
//		}
//	}
//	
//	public List<Estoque> estoquePorMaterial(int idMaterial){
//		try {
//			setNomeBanco(DB_BANCO_IMHOTEP);
//			ResultSet rs = consultar(utf8_to_latin1("select id_estoque, cv_lote from tb_estoque " +
//					"where id_material = "+idMaterial));
//			List<Estoque> list = new ArrayList<Estoque>();
//			while (rs.next()) { 
//				int idEstoque = rs.getInt(1);
//				String lote = rs.getString(2);
//				Estoque estoque = new Estoque();
//				estoque.setIdEstoque(idEstoque);
//				estoque.setLote(lote);
//				list.add(estoque);
//			}
//			
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
}
