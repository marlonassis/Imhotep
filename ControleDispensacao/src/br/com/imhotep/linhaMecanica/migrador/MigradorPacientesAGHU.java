package br.com.imhotep.linhaMecanica.migrador;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class MigradorPacientesAGHU {

	
	private static final String DIRETORIO_CSV = "/Users/marlonassis/Desktop/pacientes.csv";

	public static void main(String[] args) {
		System.out.println("In’cio: "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
		System.out.println("Gerando relat—rio AGHU");
//		recuperarPacientesFalhos();
		gerarRelatorioGenerico();
	}

	private static void recuperarPacientesFalhos() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("/Users/marlonassis/Desktop/pacientesErro.txt"));
	        String str;
	        while (in.ready()) {
	            str = in.readLine();
	            LinhaMecanica lml = new LinhaMecanica();
				lml.setNomeBanco("db_imhotep_paciente");
				lml.setIp("200.133.41.8");
				System.out.println(str);
				if(!lml.executarCUD(str)){
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

	private static void gerarRelatorioGenerico() {
		String sql = "select * from tb_paciente a where id_paciente >  678011 ";
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco("db_imhotep_paciente");
		lm.setIp("200.133.41.8");
		ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
		try {
			File arquivo = new File(DIRETORIO_CSV);
			FileOutputStream arquivoOutput = new FileOutputStream(arquivo, true); 
			PrintStream gravador = new PrintStream(arquivoOutput);

			String csv = "prontuario;nome;nome_mae;dt_nascimento;cdd_codigo;uf_sigla;cpf;nac_codigo;cor;sexo;grau_instrucao;nome_pai;estado_civil;sexo_biologico;observacao;ddd_tel_res;numero_tel_res;ddd_tel_rec;numero_tel_rec;nro_cartao_sus;rg;id_sistema_legado;CEP_ENDERECO;NUMERO_ENDERECO;COMPLEMENTO_ENDERECO;CORRESPONDENCIA_ENDERECO";
//			csv = "12123;CARLOS ALBUQUERQUE FONTOURA;MARIA DE FATIMA FONTOURA;1910-12-25 00:00:00;7807;RS;82744033049;10;B;M;4;AMAURI LOPES FONTOURA;O;M;4546;51;33597495;51;94657788;706502362375594;1078499462;4546;03985073;556;102;S";
			gravador.println(csv);
			int cont = 1;
			while (rs.next()) {
//			    M - P - Parda
//			    P - N - Negra
//			    A - A - Amarela
//			    B - B - Branca
//			    I - ''
//				'' - O - Outros
				String prontuario = rs.getString("cv_prontuario");
				String nome = limparStringVirgula(rs.getString("cv_nome"));
				String nomeMa = limparStringVirgula(rs.getString("cv_nome_mae"));
				String dataNascimento = rs.getString("dt_data_nascimento");
				dataNascimento = dataNascimento == null ? "" : dataNascimento;
				String cpf = limparStringVirgula(rs.getString("cv_cpf"));
				String cor = rs.getString("cv_cor");
				cor = cor == null?"":(cor.equals("P") ? "M" : ( cor.equals("N") ? "P" : (cor.equals("P") ? "M" : (cor.equals("O") ? "" : cor))));
				String sexo = rs.getString("tp_sexo");
				String nomePai = rs.getString("cv_nome_pai");
				nomePai = limparStringVirgula(nomePai);
				String numeroSUS = limparString(rs.getString("cv_numero_sus"));
				String rg = limparString(rs.getString("cv_registro_geral"));
				String endereco = limparStringVirgula(rs.getString("cv_endereco"));
				
//				nome, mae, pront, nascimento, cor, pai, rg
				
				csv = prontuario+";"+nome+";"+nomeMa+";"+dataNascimento+
						 ";;;;;"+cor+";;;"+
						 nomePai+";;;;;;;;;"+rg+";;;;;;";
				
//				csv = prontuario+";"+nome+";"+nomeMa+";"+dataNascimento+
//					 ";;;"+cpf+";;"+cor+";"+sexo+";;"+
//					 nomePai+";;"+sexo+";;;;;;"+numeroSUS+";"+rg+";;;;"+
//					 endereco+";;";
				System.out.println(csv);
				gravador.println(csv);
				cont++;
			}
			System.out.println(cont);
			gravador.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static String limparStringVirgula(String v){
		String q = limparString(v);
		if(q != null && q != ""){
			return q.replace(",", "");
		}
		return "";
	}
	
	private static String limparString(String v){
		if(v != null && v != ""){
			if(!v.trim().equals("")){
				String nome = v.trim().replace("'", "").replace("+", "").replace(".", "").replace(";", "").replace("+", "").replace("\"", "").replace("-", "").replace("Â´", "").replace("=", "").replace("~", "").replace("`", "");
				char[] n = nome.toCharArray();
				int ev = 0;
				String strNova = "";
				for(char c : n){
					if(c == 32){
						ev++;
					}else{
						ev = 0;
					}
					
					if(ev < 2){
						strNova = strNova.concat(String.valueOf(c));
					}
				}
				return strNova;
			}
		}
		return "";
	}
	
}
