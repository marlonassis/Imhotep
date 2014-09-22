package br.com.imhotep.linhaMecanica.atualizador;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class AtualizadorAlmoxarifadoPrimeiroSaldo{
	
	private static final String ip = "200.133.41.8";
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public static void main(String[] args) {
		try {
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(DB_BANCO_IMHOTEP);
			lm.setIp(ip);
			ResultSet rs = lm.consultar("select a.id_material_almoxarifado idMaterialAlmoxarifado, c.dt_data_movimento dataMovimento, coalesce(c.in_quantidade_movimentacao, 0) quantidade from tb_material_almoxarifado a "+
										"left join tb_estoque_almoxarifado b on b.id_material_almoxarifado = a.id_material_almoxarifado "+
										"left join tb_movimento_livro_almoxarifado c on b.id_estoque_almoxarifado = c.id_estoque_almoxarifado "+
										"where c.dt_data_movimento is null or "+ 
										"c.dt_data_movimento =  "+
										"(select min(d.dt_data_movimento) from tb_movimento_livro_almoxarifado d  "+
										"left join tb_estoque_almoxarifado e on e.id_estoque_almoxarifado = d.id_estoque_almoxarifado "+
										"where e.id_material_almoxarifado = a.id_material_almoxarifado) "+
										"group by a.id_material_almoxarifado, c.dt_data_movimento, c.in_quantidade_movimentacao "+
										"order by a.id_material_almoxarifado");
			while (rs.next()) {
				
				
				int idMaterialAlmoxarifado = rs.getInt("idMaterialAlmoxarifado");
				int quantidade = rs.getInt("quantidade");
				
//				String insertML = "INSERT INTO tb_material_almoxarifado_preco_medio_transportado_medlynx( "+
//								            "id_material_almoxarifado, "+ 
//								            "in_saldo_transportado) "+ 
//								    "VALUES ("+idMaterialAlmoxarifado+", "+quantidade+");";
				String update = "update tb_material_almoxarifado_preco_medio_transportado_medlynx set in_saldo_transportado = "+quantidade+" where id_material_almoxarifado = "+idMaterialAlmoxarifado;
				
				System.out.println(update);
				
				lm.executarCUD(update);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
