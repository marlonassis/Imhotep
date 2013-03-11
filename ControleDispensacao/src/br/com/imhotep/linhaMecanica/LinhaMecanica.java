package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LinhaMecanica extends GerenciadorMecanico {
	
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public boolean apagarMovimentoLivroEstoque(int idEstoque){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "delete from tb_movimento_livro where id_estoque = "+idEstoque;
		return executarQuery(sql);
	}
	
	public boolean fluxoFusaoEstoque(int idEstoqueOrigem, int idEstoqueDestino){
		setNomeBanco(DB_BANCO_IMHOTEP);
		
		String sql = "update tb_estoque set bl_lock = true where id_estoque = "+idEstoqueOrigem+" or id_estoque = "+idEstoqueDestino;
		if(!executarQuery(sql))
			return false;
		
		sql = "update tb_movimento_livro set id_estoque = "+idEstoqueDestino+" where id_estoque = "+idEstoqueOrigem;
		super.getFluxo().put("transfereMovimentacaoEstoque", sql);
		
		sql = "delete from tb_estoque where id_estoque = "+idEstoqueOrigem;
		super.getFluxo().put("apagarEstoque", sql);
		
		if(!executarQueryFluxo())
			return false;
		
		sql = "update tb_estoque set bl_lock = false where id_estoque = "+idEstoqueDestino;
		if(!executarQuery(sql))
			return false;
		
		if(!reordenacaoMovimento(idEstoqueDestino))
			return false;
		return true;
	}
	
	public void apagarEstoque(int idEstoque){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "delete from tb_estoque where id_estoque = "+idEstoque;
		executarQuery(sql);
	}
	
	public void transfereMovimentacaoEstoque(int idEstoqueOrigem, int idEstoqueDestino){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "update tb_movimento_livro set id_estoque = "+idEstoqueDestino+" where id_estoque = "+idEstoqueOrigem;
		executarQuery(sql);
	}
	
	public void lockEstoque(int idEstoque){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "update tb_estoque set bl_lock = true where id_estoque = "+idEstoque;
		executarQuery(sql);
	}
	
	public void unLockEstoque(int idEstoque){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "update tb_estoque set bl_lock = false where id_estoque = "+idEstoque;
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
		LinhaMecanica lm = new LinhaMecanica();

//		lm.reordenacaoMovimento(517);
		
		System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
		lm.setNomeBanco(DB_BANCO_IMHOTEP);
		ResultSet rs = lm.consultar(lm.utf8_to_latin1("select id_estoque from tb_estoque order by id_estoque"));
		while (rs.next()) { 
			lm.reordenacaoMovimento(rs.getInt(1));
		}
		System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
	}
	
	public boolean reordenacaoMovimento(int idEstoque){
		try {
			setNomeBanco(DB_BANCO_IMHOTEP);
			ResultSet rs = consultar(utf8_to_latin1("select a.id_movimento_livro, a.in_saldo_anterior, a.in_quantidade_movimentacao, b.tp_operacao from tb_movimento_livro a " +
					"inner join tb_tipo_movimento b on b.id_tipo_movimento = a.id_tipo_movimento " +
					"where a.id_estoque = "+idEstoque+" order by a.dt_data_movimento asc"));
			int saldoAnterior = 0;
			int quantidadeMovimentacao = 0;
			String tipoOperacao = null;
			while (rs.next()) { 
				int idMovimentoLivro = rs.getInt(1);
				quantidadeMovimentacao = rs.getInt(3);
				String sql = "update tb_movimento_livro set in_saldo_anterior = "+saldoAnterior+" where id_movimento_livro = "+idMovimentoLivro;
				executarQuery(sql);
				tipoOperacao = rs.getString(4);
				System.out.println("idEstoque: "+idEstoque+" - SA: "+saldoAnterior+" - QM: "+quantidadeMovimentacao);
				if(tipoOperacao.equals("E"))
					saldoAnterior += quantidadeMovimentacao;
				else
					saldoAnterior -= quantidadeMovimentacao;
			}
			
			String sql = "update tb_estoque set in_quantidade_atual = "+saldoAnterior+" where id_estoque = "+idEstoque;
			executarQuery(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
