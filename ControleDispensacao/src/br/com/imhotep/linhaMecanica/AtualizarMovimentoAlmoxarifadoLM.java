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
		ResultSet rs = lm.consultar("select a.id_estoque_almoxarifado from tb_estoque_almoxarifado a order by a.id_estoque_almoxarifado");
		try {
			while (rs.next()) {
				Integer id = rs.getInt("id_estoque_almoxarifado");
				new AtualizarMovimentoAlmoxarifadoLM().atualizarMovimentoAlmoxarifado(id, lm);
			}
		} catch (ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void atualizarMovimentoAlmoxarifado(int idEstoqueAlmoxarifado, LinhaMecanica lm) throws SQLException, ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos{
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
