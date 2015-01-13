package br.com.imhotep.testes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.excecoes.ExcecaoEstoqueNaoAtualizado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;




public class Teste {
	public static void main(String[] args) {
		String sql = "select y.id_material, y.cv_codigo_material, y.cv_descricao, "+ 
						"coalesce((select avg(c.db_valor_unitario) from tb_estoque b "+
						"left join tb_nota_fiscal_estoque c on c.id_estoque = b.id_estoque "+
						"where y.id_material = b.id_material), 0) PMImhotep "+
						"from tb_material y "+
						"group by y.id_material, y.cv_codigo_material, y.cv_descricao "+
						"order by to_ascii(lower(y.cv_descricao))"; 
		LinhaMecanica lm = new LinhaMecanica();
		lm.setIp("200.133.41.8");
		ResultSet rs = lm.consultar(sql);
		try {
			while(rs.next()){
				int id = rs.getInt("id_material");
				Double precoMedio = rs.getDouble("PMImhotep");
				BigDecimal bd = new BigDecimal(precoMedio.doubleValue());
				bd = bd.setScale(2, RoundingMode.HALF_UP);
				
				if(precoMedio.doubleValue() != 0d){
					String update = "UPDATE tb_material "+
									"   SET db_preco_medio= "+ bd.doubleValue() + 
									" WHERE id_material = "+id+";";
					
					if(!lm.executarCUD(update))
						throw new ExcecaoEstoqueNaoAtualizado();
					
					String justificativa = null;
				    if(bd.doubleValue() != precoMedio.doubleValue()){
				    	justificativa = "'O preço médio foi arredondado de " + 
				    					precoMedio.doubleValue() + 
				    					" para " + 
				    					bd.doubleValue() + "'";
				    }
				    
				    String sqlLog = "INSERT INTO farmacia.tb_medicamento_preco_medio_log( "+
								    "        id_medicamento, db_preco_medio, "+ 
								    "        id_profissional_responsavel, dt_data_cadastro, cv_justificativa) "+
								    "VALUES ("+ id +", "+ bd.doubleValue() +", "+
								    "        1742, now(), "+ justificativa +");";
				    
//					System.out.println(bd.doubleValue() + " - " + precoMedio.doubleValue());
					
					System.out.println(id);
					if(!lm.executarCUD(sqlLog))
						throw new ExcecaoEstoqueNaoAtualizado();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
