package br.com.imhotep.linhaMecanica.migrador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.imhotep.auxiliar.Utilities;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class MigradorProcedimentoSus {

	private static final String arquivoProcedimento = "/Users/marlonassis/Downloads/TabelaUnificada_201305_v1305061436-1/tb_procedimento.txt";
	private static final String layoutTabelaUnificada = "/Users/marlonassis/Downloads/TabelaUnificada_201305_v1305061436-1/layout.txt";
	private static final String arquivoErro = "/Users/marlonassis/Documents/erroProcedimento.txt";
	private static BufferedReader bufRead;
	private static String sqlProcedimento = "insert into tb_procedimento_saude (cv_codigo_procedimento, cv_nome, cv_complexidade, cv_sexo,"+
								"in_quantidade_maxima_execucoes, in_dias_permanencia, in_pontos, in_idade_minima, in_idade_maxima,"+
								"db_valor_sh, db_valor_sa, db_valor_sp, cv_financiamento, cv_rubrica, in_quantidade_tempo_permanencia, dt_competencia) " +
								"values (?0, ?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15);";
	private static List<String[]> restricoes = new ArrayList<String[]>();
	private static String[] procedimentoAtual = {"tb_procedimento", sqlProcedimento, arquivoProcedimento};
	public static void main(String[] args) {
		carregarRestricoes();
		List<String[]> regras = carregarRegrasTabela(procedimentoAtual[0]);
		Calendar ini = Calendar.getInstance();
		int quantidadeEncontrada = 0;
		int quantidadePersistida = 0;
		try {
			String linha;
			bufRead = new BufferedReader(new FileReader(procedimentoAtual[2]));
			StringBuilder sb = new StringBuilder();
			while ((linha = bufRead.readLine()) != null) {
				try {
					linha = linha.replace("'", "");
					quantidadeEncontrada++;
					int cont = 0;
					String sql = String.valueOf(procedimentoAtual[1].toCharArray());
					while(cont < regras.size()){
						String[] regra = regras.get(cont);
						String substring = linha.substring(Integer.parseInt(regra[2])-1, Integer.parseInt(regra[3])).trim();
						substring = tratarValor(regra, substring);
						sql = sql.replaceFirst("\\?"+cont, substring == null ? "NULL" : substring);
						cont++;
					}
					sb.append(sql.concat("\n"));
					System.out.println(sql);
					
					LinhaMecanica lm = new LinhaMecanica();
					lm.setNomeBanco("db_imhotep");
					lm.setIp("127.0.0.1");
					if(!lm.executarCUD(sql)){
						File arquivo = new File(arquivoErro);
						FileOutputStream arquivoOutput = new FileOutputStream(arquivo, true); 
						PrintStream gravador = new PrintStream(arquivoOutput);
						gravador.println(sql);
						gravador.close();
					}else{
						quantidadePersistida++;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Início: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(ini.getTime()));
		System.out.println("Fim: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		System.out.println("Qtd Encontrada: " + quantidadeEncontrada);
		System.out.println("Qtd Persistida: " + quantidadePersistida);
	}

	private static void carregarRestricoes() {
		String[] restricao = {"tb_procedimento", "CO_FINANCIAMENTO", "ConcatenarInicio", "T"};
		restricoes.add(restricao);
	}

	private static String tratarValor(String[] regra, String substring) {
		if(substring.equals("")){
			substring = null;
		}else{
			if(regra[4].equalsIgnoreCase("VARCHAR2")){
				if(regra[0].split("_")[0].equalsIgnoreCase("tp")){
					if(Utilities.isNumero(substring)){
						substring = "T".concat(substring);
					}
				}
				substring = new LinhaMecanica().ascii_to_latin1(substring);
				substring = "'".concat(substring.concat("'"));
			}else{
				if(regra[4].equalsIgnoreCase("NUMBER") && regra[0].split("_")[0].equalsIgnoreCase("vl")){
					substring =  String.valueOf(Utilities.StringParaDoubleDiv100(substring));
				}else{
					if(regra[4].equalsIgnoreCase("CHAR") && regra[0].split("_")[0].equalsIgnoreCase("dt")){
						substring = substring.substring(0, 4).concat("-").concat(substring.substring(4, 6)).concat("-01") ;
						substring = "'".concat(substring).concat("'");
					}
				}
			}
		}
		return substring;
	}

	private static List<String[]> carregarRegrasTabela(String tabela) {
		try {
			String linha;
			bufRead = new BufferedReader(new FileReader(layoutTabelaUnificada));
			boolean achouProcedimento = false;
			List<String[]> info = new ArrayList<String[]>();
			int i = 1;
			while ((linha = bufRead.readLine()) != null) {
				if(linha.trim().equals("")){
					achouProcedimento = false;
				}
				
				if(achouProcedimento){
					if(i > 1){
						info.add(linha.split(","));
					}
					i++;
				}
				
				if(linha.trim().equals(tabela)){
					achouProcedimento = true;
				}
			}
			return info;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
