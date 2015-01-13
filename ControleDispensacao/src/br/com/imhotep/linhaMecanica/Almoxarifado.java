package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;

import br.com.imhotep.auxiliar.Constantes;

public class Almoxarifado {
	public static void main(String[] args) {
		
	}
	
	private void apagaMovimentacoesAposNovembro(){
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_LOCAL);
		String sqlFinanceiroAlmoxarifadoFinal = "select a.id_movimento_livro_almoxarifado id, a.id_estoque_almoxarifado idEstoque, a.in_quantidade_movimentacao qtd, "
				+ "b.tp_operacao operacao from tb_movimento_livro_almoxarifado a "+
				"inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "+
				"where a.dt_data_movimento >= cast('2013-12-01' as date)";
		
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlFinanceiroAlmoxarifadoFinal));
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int idEstoque = rs.getInt("idEstoque");
				int qtd = rs.getInt("qtd");
				String operacao = rs.getString("operacao");
				String sql = null;
				if(operacao.equalsIgnoreCase("e")){
					sql = "update tb_estoque_almoxarifado set in_quantidade_atual = in_quantidade_atual -"+qtd+" where id_estoque_almoxarifado = "+idEstoque;
				}else{
					sql = "update tb_estoque_almoxarifado set in_quantidade_atual = in_quantidade_atual +"+qtd+" where id_estoque_almoxarifado = "+idEstoque;
				}
				System.out.println(sql);
				lm.executarCUD(sql);
				sql = "delete from tb_movimento_livro_almoxarifado where id_movimento_livro_almoxarifado = "+id;
				System.out.println(sql);
				lm.executarCUD(sql);
			}
		}catch(Exception e){
		e.printStackTrace();
		}
	}
	
	private void atualizaQuantidadeEstoqueMovimentoAlmoxarifado(){
		try {
			String sqlEstoque = "select id_estoque_almoxarifado from tb_estoque_almoxarifado";
			LinhaMecanica lm = new LinhaMecanica();
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
						System.out.println("D - Estoque: "+idEstoque2+" - "+lote+"\nMaterial: "+material+"\nTotal: "+entrada+" - "+saida+" = "+(entrada-saida)+"//"+quantidade+"\n••••••••");
					}
				}
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
	}
}
