package br.com.imhotep.linhaMecanica.atualizador;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class AtualizadorMovimentoAlmoxarifado {
	
private static final String DB_BANCO_IMHOTEP = "db_imhotep";

	public void atualizarMovimentos(Integer idE, LinhaMecanica lm) {
		try {
			String sql = "";
			if(idE == null)
				sql = "select id_estoque_almoxarifado from tb_estoque_almoxarifado order by id_estoque_almoxarifado";
			else
				sql = "select id_estoque_almoxarifado from tb_estoque_almoxarifado where id_estoque_almoxarifado = "+idE+" order by id_estoque_almoxarifado";
			ResultSet rs = lm.consultar(sql);
			while (rs.next()) { 
				int idEstoque = rs.getInt(1);
				ResultSet rs2 = lm.consultar("select a.id_movimento_livro_almoxarifado, b.tp_operacao, a.in_quantidade_movimentacao from tb_movimento_livro_almoxarifado a "
						+ "inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "
						+ "where a.id_estoque_almoxarifado = " + idEstoque + " order by a.dt_data_movimento");
				int valorAnterior = 0;
				while (rs2.next()) {
					String op = rs2.getString("tp_operacao");
					Integer qtdMov = rs2.getInt("in_quantidade_movimentacao");
					Integer id = rs2.getInt("id_movimento_livro_almoxarifado");
					
					
					String sqlUpd = "update tb_movimento_livro_almoxarifado set in_quantidade_atual="+valorAnterior+" where id_movimento_livro_almoxarifado = "+id;
					System.out.println("Estoque: "+idEstoque+" - "+sqlUpd);
					if(valorAnterior < 0){
						System.exit(1);
						System.out.println("ERRO!");
					}
					if(!lm.executarCUD(sqlUpd)){
						System.exit(1);
					}
					
					if(op.equalsIgnoreCase("E")){
						valorAnterior += qtdMov;
					}else{
						valorAnterior -= qtdMov;
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		LinhaMecanica lm = new LinhaMecanica(DB_BANCO_IMHOTEP, "200.133.41.8");
		try {
			lm.criarConexao();
			ResultSet rs = lm.fastConsulta("select id_estoque_almoxarifado, cv_lote from tb_estoque_almoxarifado where id_estoque_almoxarifado = 581 "
					+ "order by id_estoque_almoxarifado");
			while (rs.next()) { 
				int idEstoque = rs.getInt(1);
				String lote = rs.getString(2);
				ResultSet rs2 = lm.fastConsulta("select a.id_movimento_livro_almoxarifado, b.tp_operacao, a.in_quantidade_movimentacao from tb_movimento_livro_almoxarifado a "
						+ "inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "
						+ "where a.id_estoque_almoxarifado = " + idEstoque + " order by a.dt_data_movimento");
				int valorAnterior = 0;
				while (rs2.next()) {
					String op = rs2.getString("tp_operacao");
					Integer qtdMov = rs2.getInt("in_quantidade_movimentacao");
					Integer id = rs2.getInt("id_movimento_livro_almoxarifado");
					System.out.print(id + " - " + idEstoque + " - " + lote + " - " + op + " - " + valorAnterior + " - ");
					String sqlUpd = "update tb_movimento_livro_almoxarifado set in_quantidade_atual="+valorAnterior+" where id_movimento_livro_almoxarifado = "+id;
					if(op.equalsIgnoreCase("E")){
						valorAnterior += qtdMov;
					}else{
						valorAnterior -= qtdMov;
					}
					System.out.println(qtdMov + " - ("+valorAnterior+")");
					if(!lm.fastExecutarCUD(sqlUpd) || valorAnterior < 0){
						System.out.println("erro!");
						System.exit(1);
					}
				}
				String sqlUtualizarQtdEstoque = "select  "+
												"((select sum(b.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado b "+ 
												"inner join tb_tipo_movimento_almoxarifado c on b.id_tipo_movimento_almoxarifado = c.id_tipo_movimento_almoxarifado "+ 
												"where c.tp_operacao = 'E' and b.id_estoque_almoxarifado = a.id_estoque_almoxarifado) -  "+
												"(select sum(b.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado b "+ 
												"inner join tb_tipo_movimento_almoxarifado c on b.id_tipo_movimento_almoxarifado = c.id_tipo_movimento_almoxarifado "+ 
												"where c.tp_operacao != 'E' and b.id_estoque_almoxarifado = a.id_estoque_almoxarifado)) as total "+
												"from tb_estoque_almoxarifado a "+
												"where a.id_estoque_almoxarifado = "+idEstoque;
				rs2 = lm.fastConsulta(sqlUtualizarQtdEstoque);
				rs2.next();
				int total = rs2.getInt("total");
				String sqlUpdateEstoque = "update tb_estoque_almoxarifado set in_quantidade_atual = "+total+" where id_estoque_almoxarifado = "+idEstoque;
				System.out.println(sqlUpdateEstoque);
				if(!lm.fastExecutarCUD(sqlUpdateEstoque)){
					System.out.println("erro2!");
					System.out.println("Estoque: " + idEstoque);
					System.exit(1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			lm.fecharConexoes();
		}
	}
}
