package br.com.imhotep.linhaMecanica.atualizador;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.linhaMecanica.GerenciadorMecanico;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class AtualizadorMovimento extends GerenciadorMecanico{
	
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public static void main(String[] args) {
		LinhaMecanica lm = new LinhaMecanica(DB_BANCO_IMHOTEP, "200.133.41.8");
		try {
			lm.criarConexao();
			ResultSet rs = lm.fastConsulta("select id_estoque, cv_lote from tb_estoque where id_estoque = 868 order by id_estoque");
			while (rs.next()) { 
				int idEstoque = rs.getInt(1);
				String lote = rs.getString(2);
				atualizarMovimentacoes(lm, idEstoque, lote);
				atualizarSaldoAtual(lm, idEstoque);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			lm.fecharConexoes();
		}
	}

	public static void atualizarSaldoAtual(LinhaMecanica lm, int idEstoque) throws SQLException {
		String sqlUtualizarQtdEstoque = "select "+
										"(coalesce((select sum(b.in_quantidade_movimentacao) from tb_movimento_livro b "+ 
										"inner join tb_tipo_movimento c on b.id_tipo_movimento = c.id_tipo_movimento  "+
										"where c.tp_operacao = 'E' and b.id_estoque = a.id_estoque), 0) -   "+
										"coalesce((select sum(b.in_quantidade_movimentacao) from tb_movimento_livro b "+ 
										"inner join tb_tipo_movimento c on b.id_tipo_movimento = c.id_tipo_movimento  "+
										"where c.tp_operacao != 'E' and b.id_estoque = a.id_estoque), 0)) as total  "+
										"from tb_estoque a  "+
										"where a.id_estoque = "+idEstoque;
		ResultSet rs2 = lm.fastConsulta(sqlUtualizarQtdEstoque);
		rs2.next();
		int total = rs2.getInt("total");
		String sqlUpdateEstoque = "update tb_estoque set in_quantidade_atual = "+total+" where id_estoque = "+idEstoque;
		System.out.println(sqlUpdateEstoque);
		if(!lm.fastExecutarCUD(sqlUpdateEstoque)){
			System.out.println("erro2!");
			System.out.println("Estoque: " + idEstoque);
		}
	}
	
	public static void atualizarMovimentacoes(LinhaMecanica lm, int idEstoque, String lote) throws SQLException {
		int valorAnterior = 0;
		ResultSet rs2 = lm.fastConsulta("select a.id_movimento_livro, b.tp_operacao, a.in_quantidade_movimentacao from tb_movimento_livro a "
				+ "inner join tb_tipo_movimento b on a.id_tipo_movimento = b.id_tipo_movimento "
				+ "where a.id_estoque = " + idEstoque + " order by a.dt_data_movimento");
		while (rs2.next()) {
			String op = rs2.getString("tp_operacao");
			Integer qtdMov = rs2.getInt("in_quantidade_movimentacao");
			Integer id = rs2.getInt("id_movimento_livro");
			System.out.print(id + " - " + idEstoque + " - " + lote + " - " + op + " - " + valorAnterior + " - ");
			String sqlUpd = "update tb_movimento_livro set in_quantidade_atual="+valorAnterior+" where id_movimento_livro = "+id;
			if(op.equalsIgnoreCase("E")){
				valorAnterior += qtdMov;
			}else{
				valorAnterior -= qtdMov;
			}
			System.out.println(qtdMov + " - ("+valorAnterior+")");
			if(!lm.fastExecutarCUD(sqlUpd) || valorAnterior < 0){
				System.out.println("erro!");
			}
		}
	}
}
