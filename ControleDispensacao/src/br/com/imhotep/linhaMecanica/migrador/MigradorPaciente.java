package br.com.imhotep.linhaMecanica.migrador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.imhotep.entidade.Paciente;
import br.com.imhotep.enums.TipoSanguineoEnum;
import br.com.imhotep.enums.TipoSexoEnum;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.PadraoHome;

/**
 * Classe para migrar os pacientes do medlynx
 * @author marlonassis
 *
 */
public class MigradorPaciente extends PadraoHome<Paciente> {

	public static void main(String[] args) {
//		migrarPacientes();
		atualizarPacienteTrimTipoSanguineo();
	}

	private static void atualizarPacienteTrimTipoSanguineo() {
		String sqlPes = "select id_paciente, tp_sangue from tb_paciente ";
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco("db_imhotep_temp");
		lm.setIp("127.0.0.1");
		int totalCadastrado = 0;
		Calendar ini = Calendar.getInstance();
		int total = 0;
		try {
			ResultSet rs = lm.consultar(lm.utf8_to_latin1("select count(id_paciente) as total from tb_paciente limit 100"));
			while (rs.next()) {
				total = rs.getInt("total");
			}
			
			rs = lm.consultar(lm.utf8_to_latin1(sqlPes));
			while (rs.next()) {
				int id = rs.getInt("id_paciente");
				String tipoSangue = rs.getString("tp_sangue").trim();
				String sql = "update tb_paciente set tp_sangue = '" + tipoSangue + "' where id_paciente = "+ id;
				System.out.println(sql);
				LinhaMecanica lml = new LinhaMecanica();
				lml.setNomeBanco("db_imhotep_temp");
				lml.setIp("127.0.0.1");
				if(lml.executarCUD(sql)){
					totalCadastrado++;
				}else{
					File arquivo = new File("/home/desenvolvimento/Imhotep/erro.txt");
					FileOutputStream arquivoOutput = new FileOutputStream(arquivo, true); 
					PrintStream gravador = new PrintStream(arquivoOutput);
					gravador.println(sql);
					gravador.close();
				}
				
				double per = (totalCadastrado/total)*100d;
				
				System.out.println(totalCadastrado + "/" + total + " = " + per+"%");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Total Achado: "+total);
		System.out.println("Total Cadastrado: "+totalCadastrado);
		System.out.println("Início: "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(ini.getTime()));
		System.out.println("Fim: "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
	}
	
	private static void migrarPacientes() {
		//cd_pessoa é o prontuário e id do paciente (int)
		//razao_social é o nome (string)
		//remover os ceps que estão no formato 49000000 (int)
		//compl_cep é a rua com o número da casa, bairro, povoado, etc... (string) 
		//sexo_pf (string)
		//dt_nasc - tem que ter a hora também (date)
		//tp_sanguineo - tipo do sangue (string)
		//nome_pai, nome_mae, cd_rg, orgao_rg (string)
		//nacionalidade, naturalidade (string) 
		//cor, nacionalidade, naturalidade (string)
		String sqlPes = "select cd_pessoa, razao_social, sexo_pf, dt_nasc, tp_sanguineo, "+
				"nome_pai, nome_mae, cd_rg, orgao_rg, nacionalidade, naturalidade, cor, cd_cpf "+
				"from cadpessoa where tp_pessoa = 'F' order by razao_social";
		
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco("hospital_latin");
		lm.setIp("127.0.0.1");
		int totalAchado = 0;
		int totalCadastrado = 0;
		Calendar ini = Calendar.getInstance();
		try {
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sqlPes));
			while (rs.next()) {
				int prontuario = rs.getInt("cd_pessoa");
				String nome = limparString(rs.getString("razao_social"));
				String sexo = limparString(rs.getString("sexo_pf"));
				TipoSexoEnum sexoo = sexo == null || sexo.equals("") ? null : (sexo.equals("M") ? TipoSexoEnum.M : TipoSexoEnum.F );
				Date nascimento = rs.getDate("dt_nasc");
				TipoSanguineoEnum tipoSanguineoEnum = converterTipo(rs.getString("tp_sanguineo"));
				String nomePai = limparString(rs.getString("nome_pai"));
				String nomeMae = limparString(rs.getString("nome_mae"));
				String rg = limparString(rs.getString("cd_rg"));
				String orgaoRg = limparString(rs.getString("orgao_rg"));
				String nacionalidade = limparString(rs.getString("nacionalidade"));
				String naturalidade = limparString(rs.getString("naturalidade"));
				String cor = limparString(rs.getString("cor"));
				String cpf = limparString(rs.getString("cd_cpf"));
				String sexooo = sexoo != null ? "'"+sexoo.name()+"'" : null;
				String ts = tipoSanguineoEnum != null ? "'"+tipoSanguineoEnum.name().trim()+"'" : null;
				String sql = "insert into tb_paciente (cv_nome, cv_nome_mae, cv_nome_pai, tp_sexo, dt_data_nascimento, cv_cpf, " +
						"cv_prontuario, tp_sangue, cv_registro_geral, cv_orgao_registro_geral, cv_nacionalidade, " +
						"cv_cor, cv_naturalidade) values("+nome+","+nomeMae+","+nomePai+","+sexooo+","+nascimento+"," +
								cpf+","+prontuario+","+ts+","+rg+","+orgaoRg+","+nacionalidade+","+
								cor+","+naturalidade+");";
				System.out.println(sql);
				LinhaMecanica lml = new LinhaMecanica();
				lml.setNomeBanco("db_imhotep_temp");
				lml.setIp("127.0.0.1");
				if(lml.executarCUD(sql)){
					totalCadastrado++;
				}
				
				totalAchado++;
				
				File arquivo = new File("/home/desenvolvimento/Imhotep/pacientes.txt");
				FileOutputStream arquivoOutput = new FileOutputStream(arquivo, true); 
				PrintStream gravador = new PrintStream(arquivoOutput);
				gravador.println(sql);
				gravador.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Total Achado: "+totalAchado);
		System.out.println("Total Cadastrado: "+totalCadastrado);
		System.out.println("Início: "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(ini.getTime()));
		System.out.println("Fim: "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
	}

	private static String limparString(String v){
		if(v != null){
			if(!v.trim().equals("")){
				String nome = v.trim().replace("'", "").replace("+", "").replace(".", "").replace(";", "").replace("+", "").replace("\"", "").replace("-", "").replace("´", "").replace("=", "").replace("~", "").replace("`", "");
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
				return "'"+strNova+"'";
			}
		}
		return null;
	}
	
	private static TipoSanguineoEnum converterTipo(String tipoSanguineo) {
		if(tipoSanguineo == null || tipoSanguineo.trim().equalsIgnoreCase("NI") || tipoSanguineo.trim().equalsIgnoreCase("")){
			return TipoSanguineoEnum.NI;
		}
		tipoSanguineo = tipoSanguineo.trim();
		if(tipoSanguineo.equalsIgnoreCase("AB-")){
			return TipoSanguineoEnum.ABN;
		}
		if(tipoSanguineo.equalsIgnoreCase("AB+")){
			return TipoSanguineoEnum.ABP;
		}
		if(tipoSanguineo.equalsIgnoreCase("A-")){
			return TipoSanguineoEnum.AN;
		}
		if(tipoSanguineo.equalsIgnoreCase("A+")){
			return TipoSanguineoEnum.AP;
		}
		if(tipoSanguineo.equalsIgnoreCase("B-")){
			return TipoSanguineoEnum.BN;
		}
		if(tipoSanguineo.equalsIgnoreCase("B+")){
			return TipoSanguineoEnum.BP;
		}
		if(tipoSanguineo.equalsIgnoreCase("O-")){
			return TipoSanguineoEnum.ON;
		}
		if(tipoSanguineo.equalsIgnoreCase("O+")){
			return TipoSanguineoEnum.OP;
		}
		return null;
	}
	
}
