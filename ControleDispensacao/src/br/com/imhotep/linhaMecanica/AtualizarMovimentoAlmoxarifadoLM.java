package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.excecoes.ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos;

public class AtualizarMovimentoAlmoxarifadoLM {
	
	public static void main(String[] args) {
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		lm.setIp("200.133.41.8");
		ResultSet rs = lm.consultar("select a.id_estoque_almoxarifado from tb_estoque_almoxarifado a where a.id_estoque_almoxarifado in (726) order by a.id_estoque_almoxarifado");
		try {
			while (rs.next()) {
				Integer id = rs.getInt("id_estoque_almoxarifado");
				AtualizarMovimentoAlmoxarifadoLM atualizarMovimentoAlmoxarifadoLM = new AtualizarMovimentoAlmoxarifadoLM();
				atualizarMovimentoAlmoxarifadoLM.atualizarMovimentoAlmoxarifado(id, lm);
				atualizarMovimentoAlmoxarifadoLM.atualizarSaldoAtual(lm, id);
			}
		} catch (ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void atualizarEstoqueEMovimento(int idEstoqueAlmoxarifado){
		AtualizarMovimentoAlmoxarifadoLM atualizarMovimentoAlmoxarifadoLM = new AtualizarMovimentoAlmoxarifadoLM();
		LinhaMecanica lm = new LinhaMecanica();
		try {
			atualizarMovimentoAlmoxarifadoLM.atualizarMovimentoAlmoxarifado(idEstoqueAlmoxarifado, lm );
			atualizarMovimentoAlmoxarifadoLM.atualizarSaldoAtual(lm, idEstoqueAlmoxarifado);
		} catch (ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void atualizarSaldoAtual(LinhaMecanica lm, int idEstoque) throws SQLException {
		String sqlUtualizarQtdEstoque = "select "+
										"(coalesce((select sum(b.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado b "+ 
										"inner join tb_tipo_movimento_almoxarifado c on b.id_tipo_movimento_almoxarifado = c.id_tipo_movimento_almoxarifado  "+
										"where c.tp_operacao = 'E' and b.id_estoque_almoxarifado = a.id_estoque_almoxarifado), 0) -   "+
										"coalesce((select sum(b.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado b "+ 
										"inner join tb_tipo_movimento_almoxarifado c on b.id_tipo_movimento_almoxarifado = c.id_tipo_movimento_almoxarifado "+
										"where c.tp_operacao != 'E' and b.id_estoque_almoxarifado = a.id_estoque_almoxarifado), 0)) as total  "+
										"from tb_estoque_almoxarifado a  "+
										"where a.id_estoque_almoxarifado = "+idEstoque;
		ResultSet rs2 = lm.consultar(sqlUtualizarQtdEstoque);
		rs2.next();
		int total = rs2.getInt("total");
		String sqlUpdateEstoque = "update tb_estoque_almoxarifado set in_quantidade_atual = "+total+" where id_estoque_almoxarifado = "+idEstoque;
		System.out.println(sqlUpdateEstoque);
		if(!lm.executarCUD(sqlUpdateEstoque)){
			System.out.println("erro2!");
			System.out.println("Estoque: " + idEstoque);
		}
	}
	
	public void atualizarMovimentoAlmoxarifado(int idEstoqueAlmoxarifado, LinhaMecanica lm) throws SQLException, ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos{
		ResultSet rs2 = lm.consultar("select a.id_movimento_livro_almoxarifado, b.tp_operacao, a.in_quantidade_movimentacao from tb_movimento_livro_almoxarifado a "
					+ "inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "
					+ "where a.id_estoque_almoxarifado = " + idEstoqueAlmoxarifado + " order by a.dt_data_movimento");
		int valorAnterior = 0;
		while (rs2.next()) {
			String op = rs2.getString("tp_operacao");
			Integer qtdMov = rs2.getInt("in_quantidade_movimentacao");
			Integer id = rs2.getInt("id_movimento_livro_almoxarifado");
			
			String sqlUpd = "update tb_movimento_livro_almoxarifado set in_quantidade_atual="+valorAnterior+" where id_movimento_livro_almoxarifado = "+id;
			System.out.println("EstoqueAlmoxarifado: "+idEstoqueAlmoxarifado+" - "+sqlUpd);
			if(valorAnterior < 0){
				throw new ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos();
			}
			if(!lm.executarCUD(sqlUpd)){
				throw new ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos();
			}
			
			if(op.equalsIgnoreCase("E")){
				valorAnterior += qtdMov;
			}else{
				valorAnterior -= qtdMov;
			}
		}
	}
	
}
