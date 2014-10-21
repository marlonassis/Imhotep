package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.linhaMecanica.atualizador.AtualizadorEstoqueAlmoxarifado;
import br.com.imhotep.linhaMecanica.atualizador.AtualizadorMovimentoAlmoxarifado;

public class EstornoDispensacaoSimplesAlmoxarifado{
	
	private static final String IP = "200.133.41.8";
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public static void main(String[] args) {
		try {
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(DB_BANCO_IMHOTEP);
			lm.setIp(IP);
			String idSolicitacaoMaterialAlmoxarifadoUnidade = "851";
			String data = "2014-02-06 08:23:04.017";
			String sql = "select a.id_movimento_livro_almoxarifado, a.id_estoque_almoxarifado from tb_movimento_livro_almoxarifado a "+
						"inner join tb_dispensacao_simples_almoxarifado b on a.id_movimento_livro_almoxarifado = b.id_movimento_livro_almoxarifado "+
						"inner join tb_solicitacao_material_almoxarifado_unidade_item c on c.id_solicitacao_material_almoxarifado_unidade_item = b.id_solicitacao_material_almoxarifado_unidade_item "+
						"inner join tb_solicitacao_material_almoxarifado_unidade d on c.id_solicitacao_material_almoxarifado_unidade = d.id_solicitacao_material_almoxarifado_unidade "+
						"where d.id_solicitacao_material_almoxarifado_unidade = "
								+ idSolicitacaoMaterialAlmoxarifadoUnidade
								+ " and a.dt_data_movimento = '"
										+ data + "' "+
						"order by a.id_estoque_almoxarifado, a.dt_data_movimento";
			ResultSet rs = lm.consultar(sql);
			while (rs.next()) {
				
				
				int idMovimentoLivroAlmoxarifado = rs.getInt("id_movimento_livro_almoxarifado");
				int idEstoqueAlmoxarifado = rs.getInt("id_estoque_almoxarifado");
				
				String deleteDispensacao = "delete from tb_dispensacao_simples_almoxarifado where id_movimento_livro_almoxarifado = "+idMovimentoLivroAlmoxarifado;
				String deleteMovimentoLivro = "delete from tb_movimento_livro_almoxarifado where id_movimento_livro_almoxarifado = "+idMovimentoLivroAlmoxarifado;
				
				System.out.println(deleteDispensacao);
				System.out.println(deleteMovimentoLivro);
				
				lm.executarCUD(deleteDispensacao);
				lm.executarCUD(deleteMovimentoLivro);
				
				AtualizadorMovimentoAlmoxarifado ama = new AtualizadorMovimentoAlmoxarifado();
				ama.atualizarMovimentos(idEstoqueAlmoxarifado, lm);
			}
			
			AtualizadorEstoqueAlmoxarifado aea = new AtualizadorEstoqueAlmoxarifado();
			aea.atualizarQuantidadeEstoqueGeralAlmoxarifado(IP, DB_BANCO_IMHOTEP);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
