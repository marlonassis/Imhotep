package br.com.imhotep.linhaMecanica.migrador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class MigradorFinanceiroAlmoxarifado {
	
	private static RoundingMode rouding = RoundingMode.HALF_EVEN;
	private static int scale = 2;
	
	public static void main(String[] args) {
		
		carregarItensArquivo();
		
		transportarPrecoMedioSaldoNovembroDezembro();
		
	}

	private static void transportarPrecoMedioSaldoNovembroDezembro() {
		String sql = "select a.id_financeiro_mensal_almoxarifado, "
				+ "a.id_material_almoxarifado, "
				+ "a.in_saldo_atual, "
				+ "a.db_preco_medio "
				+ "from tb_financeiro_mensal_almoxarifado a where a.cv_mes_referencia = '2013-11' order by a.id_material_almoxarifado";
		
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", Constantes.IP_LOCAL);
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			while (rs.next()) {
				BigDecimal id = new BigDecimal(rs.getInt("id_material_almoxarifado"));
				BigDecimal saldoAtual = new BigDecimal(rs.getInt("in_saldo_atual"));
				BigDecimal precoMedio = new BigDecimal(rs.getDouble("db_preco_medio"));
				
				String update = "update tb_financeiro_mensal_almoxarifado set db_preco_medio_transportado="+precoMedio.setScale(scale, rouding)+
						", in_saldo_transportado = "+saldoAtual.setScale(scale, rouding).intValue() + " where id_material_almoxarifado = "+id+" and cv_mes_referencia = '2013-12'";
				System.out.println(update);
				
				lm.executarCUD(update);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void carregarItensArquivo() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("/Users/marlonassis/Desktop/financeiroTempAlmoxarifado.txt"));
	        String str;
	        while (in.ready()) {
	            str = in.readLine();
	            LinhaMecanica lml = new LinhaMecanica();
				lml.setNomeBanco("db_imhotep");
				lml.setIp(Constantes.IP_LOCAL);
				String[] linha = str.split(";");
				String stringExecutar = "INSERT INTO tb_financeiro_mensal_almoxarifado( "+
			            "id_material_almoxarifado, "+
			            "db_preco_medio_transportado, db_preco_medio_atual, in_saldo_transportado, "+
			            "in_total_entrada, in_total_saida, cv_mes_referencia, dt_data_insercao, "+
			            "dt_data_atualizacao, in_saldo_atual, id_grupo_almoxarifado, db_preco_medio, "+
			            "id_sub_grupo_almoxarifado, db_total_final) "+
					    "VALUES ("+linha[1].replaceAll("\"", "")+", "+
					    "        "+linha[2].replaceAll("\"", "")+", "+linha[3].replaceAll("\"", "")+", "+linha[4].replaceAll("\"", "")+", "+
					    "        "+linha[5].replaceAll("\"", "")+", "+linha[6].replaceAll("\"", "")+", '"+linha[7].replaceAll("\"", "")+"', now(), "+
					    "        null, "+linha[10].replaceAll("\"", "")+", "+linha[11].replaceAll("\"", "")+", "+linha[12].replaceAll("\"", "")+", "+ 
					    "        "+(linha[13].replaceAll("\"", "").equals("") ? "null" : linha[13].replaceAll("\"", ""))+", "+
					    linha[14].replaceAll("\"", "").replaceAll(",", "")+");";
					    
				System.out.println(stringExecutar.replaceAll("\\$", ""));
				if(!lml.executarCUD(stringExecutar.replaceAll("\\$", ""))){
					System.out.println("erro");
					System.exit(1);
				}
	        }
	        in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
