package br.com.imhotep.linhaMecanica.atualizador;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class AtualizadorEstoqueAlmoxarifado{
	
	private static final String IP = "200.133.41.8";
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public static void main(String[] args) {
		AtualizadorEstoqueAlmoxarifado aea = new AtualizadorEstoqueAlmoxarifado();
		aea.atualizarQuantidadeEstoqueGeralAlmoxarifado(IP, DB_BANCO_IMHOTEP);
	}

	public void atualizarQuantidadeEstoqueGeralAlmoxarifado(String ip, String banco) {
		try {
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(banco);
			lm.setIp(ip);
			ResultSet rs3 = lm.consultar("select id_material_almoxarifado from tb_material_almoxarifado order by id_material_almoxarifado");
			while (rs3.next()) { 
				int idMaterial = rs3.getInt("id_material_almoxarifado");
				ResultSet rs = lm.consultar("select id_estoque_almoxarifado from tb_estoque_almoxarifado where id_material_almoxarifado = "+idMaterial+" order by id_estoque_almoxarifado");
				while (rs.next()) { 
					int idEstoque = rs.getInt(1);
					atualizarEstoque(lm, idEstoque);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void atualizarEstoque(LinhaMecanica lm, int idEstoque) throws SQLException {
		ResultSet rs2 = lm.consultar("select coalesce(coalesce((select sum(a.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado a "+ 
										"inner join tb_tipo_movimento_almoxarifado b on b.id_tipo_movimento_almoxarifado = a.id_tipo_movimento_almoxarifado "+
										"where b.tp_operacao = 'E' and a.id_estoque_almoxarifado = "+idEstoque+"), 0) "+
										"- "+
										"coalesce((select sum(c.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado c "+
										"inner join tb_tipo_movimento_almoxarifado d on d.id_tipo_movimento_almoxarifado = c.id_tipo_movimento_almoxarifado "+
										"where d.tp_operacao != 'E' and c.id_estoque_almoxarifado = "+idEstoque+"), 0), 0) as saldo");
		while (rs2.next()) {
			int saldo = rs2.getInt("saldo");
			String sqlUpd = "update tb_estoque_almoxarifado set in_quantidade_atual="+saldo+" where id_estoque_almoxarifado = "+idEstoque;
			System.out.println("Estoque: "+idEstoque+" - "+sqlUpd);
			if(!lm.executarCUD(sqlUpd)){
				System.out.println("Erro");
				System.exit(1);
			}
		}
	}
}
