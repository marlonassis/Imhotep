package br.com.imhotep.testes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import br.com.imhotep.enums.TipoEhealthNaturezaEnum;
import br.com.imhotep.enums.TipoEhealthRedeSocialEnum;
import br.com.imhotep.enums.TipoEhealthTipoTecnologiaEnum;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class Temp {

	private static final int ID_PAIS = 14;
	private static final int ID_PESQUISADOR = 1762;

	public static void main(String[] args) {
//		argentina();
//		importacaoLocalidadesUruguai();
//		importacaoPlanilhaUruguay();
//		importacaoPlanilhaAndre();
		
		
//		gerarEstatisticaBasica();
		gerarPlanilhas();
		gerarResultadosFinais();
		
	}
	
	private static void importacaoLocalidadesUruguai() {
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", "200.133.41.8");
        try {
        	lm.criarConexao();
        	String caminho = "/Users/marlonassis/Downloads/localidadeUruguai.xls";
        	//Cria um Arquivo Excel
            Workbook wb = new HSSFWorkbook(new FileInputStream(caminho));  
            //Cria uma planilha Excel
            Sheet sheet = wb.getSheet("tabla_loc11");
            int cont = 0;
            for(int i = 1; i < 616; i++){
            	Row row = sheet.getRow(i);
				if(row != null && row.getCell(0) != null && !row.getCell(0).getStringCellValue().trim().isEmpty()){
					cont++;
					System.out.println(cont);
					String estado = row.getCell(0).getStringCellValue().trim();
					String municipio = row.getCell(1).getStringCellValue().trim();
					
					
					System.out.println("estado: " + estado);
					System.out.println("municipio: " + municipio);
					
					System.out.println("+++++++++++++++++++++++++++++++++++");
					
					
					
					if(	estado == null || 
						municipio == null) {
						System.out.println("Erro!!!");
						lm.fecharConexoes();
						System.exit(1);
					}
					
//					if(true)
//						continue;
					
					String sqlInsert = "INSERT INTO ehealth.tb_ehealth_estado( "+
							           "cv_nome, id_ehealth_pais) VALUES ('"+estado+"', "+ID_PAIS+");";
					System.out.println(sqlInsert);
					lm.fastExecutarCUD(sqlInsert);
					
					Integer idEstado = getIdEstado(estado, lm);
					if(idEstado == null || idEstado.intValue() == 0){ 
						System.out.println("Erro idEstado!!!");
						lm.fecharConexoes();
						System.exit(1);
					}
					
					sqlInsert = "INSERT INTO ehealth.tb_ehealth_municipio( "+
							    "       cv_nome, id_ehealth_estado) "+
							    "VALUES ('"+municipio+"', "+idEstado+");";
					System.out.println(sqlInsert);
					lm.fastExecutarCUD(sqlInsert);
				}
            }
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	lm.fecharConexoes();
        }
	}
	
	
	
	
	
	private static Integer getIdEstado(String estado, LinhaMecanica lm) {
		try {
			String sql = "select a.id_ehealth_estado, a.cv_nome from ehealth.tb_ehealth_estado a "
					+ "inner join ehealth.tb_ehealth_pais c on c.id_ehealth_pais = a.id_ehealth_pais "
					+ "where trim(lower(to_ascii(a.cv_nome))) ilike to_ascii(trim(lower('%"+estado.replace("'", "").trim()+"%'))) and c.id_ehealth_pais = "+ID_PAIS;
			
			ResultSet consultar = lm.fastConsulta(sql);
			Integer idEstado = null;
			if(consultar.next()){
				idEstado = consultar.getInt("id_ehealth_estado");
			}
			return idEstado;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	
	
	private static Integer getIdMunicipioPeloEstado(String estado, String municipio, LinhaMecanica lm) {
		try {
			String sql = "select a.id_ehealth_municipio, a.cv_nome from ehealth.tb_ehealth_municipio a "
					+ "inner join ehealth.tb_ehealth_estado b on b.id_ehealth_estado = a.id_ehealth_estado "
					+ "inner join ehealth.tb_ehealth_pais c on c.id_ehealth_pais = b.id_ehealth_pais "
					+ "where trim(lower(to_ascii(b.cv_nome))) ilike to_ascii(trim('%"+estado.replace("'", "").replace("Â ", "").trim()+"%')) and "
					+ "trim(lower(to_ascii(a.cv_nome))) ilike to_ascii(trim('%"+municipio.replace("'", "").replace("Â ", "").trim()+"%')) and c.id_ehealth_pais = "+ID_PAIS;
			
			ResultSet consultar = lm.fastConsulta(sql);
			Integer idComuna = null;
			if(consultar.next()){
				idComuna = consultar.getInt("id_ehealth_municipio");
			}
			return idComuna;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	private static Integer getIdMunicipio(String municipio, LinhaMecanica lm) {
		try {
			String sql = "select a.id_ehealth_municipio, a.cv_nome from ehealth.tb_ehealth_municipio a "
					+ "inner join ehealth.tb_ehealth_estado b on b.id_ehealth_estado = a.id_ehealth_estado "
					+ "inner join ehealth.tb_ehealth_pais c on c.id_ehealth_pais = b.id_ehealth_pais "
					+ "where trim(lower(to_ascii(a.cv_nome))) ilike to_ascii(trim('%"+municipio.replace("'", "").replace("Â ", "").trim()+"%')) and c.id_ehealth_pais = "+ID_PAIS;
			
			ResultSet consultar = lm.fastConsulta(sql);
			Integer idComuna = null;
			if(consultar.next()){
				idComuna = consultar.getInt("id_ehealth_municipio");
			}
			if(idComuna == null)
				System.out.println();
			return idComuna;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	private static void importacaoPlanilhaUruguay() {
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", "200.133.41.8");
        try {
        	lm.criarConexao();
        	String caminho = "/Users/marlonassis/Desktop/Urugauy.xls";
        	//Cria um Arquivo Excel
            Workbook wb = new HSSFWorkbook(new FileInputStream(caminho));  
            //Cria uma planilha Excel
            Sheet sheet = wb.getSheet("Sheet1");
            for(int i = 1; i < 46; i++){
            	Row row = sheet.getRow(i);
				if(row != null && row.getCell(0) != null && !row.getCell(0).getStringCellValue().trim().isEmpty()){
					String nome = row.getCell(1).getStringCellValue().trim() + " - " + row.getCell(2).getStringCellValue().trim();
					nome = nome.replaceAll("\\(.+?\\)", "").trim();
					String site = row.getCell(3).getStringCellValue().trim();
					String enderecos[] = row.getCell(4).getStringCellValue().trim().split("-");
					String endereco = enderecos[0].trim();
					String municipio = enderecos[1].trim();
					Integer idMunicipio = getIdMunicipio(municipio, lm);
					String naturezaBruta = row.getCell(0).getStringCellValue().trim().toLowerCase();
					String natureza = naturezaBruta.contains("privado") ? "EP" : 
										(naturezaBruta.contains("cooperativa") ? "COO" : 
											(naturezaBruta.contains("sem fins lucrativos") ? "EBFL" : null));
					
					System.out.println(natureza + " - nome: " + nome);
					System.out.println("site: " + site);
					System.out.println("endereco: " + endereco);
					System.out.println(idMunicipio + " - municipio: " + municipio);
					
					System.out.println("+++++++++++++++++++++++++++++++++++");
					
					
					
					if(	nome == null || 
						site == null || 
						endereco == null || 
						idMunicipio == null || 
						municipio == null ||
						natureza == null) {
						System.out.println("Erro!!!");
						lm.fecharConexoes();
						System.exit(1);
					}
					
//					if(true)
//						continue;
					
					String sqlDeleteEsta = "delete from ehealth.tb_ehealth_estabelecimento where "+
							"cv_nome = '"+nome+
							"' and id_ehealth_municipio = "+ idMunicipio; 
					
					lm.fastExecutarCUD(sqlDeleteEsta);
					
					String sqlInsert = "INSERT INTO ehealth.tb_ehealth_estabelecimento( "+
									"id_ehealth_municipio, cv_nome,  "+
									"cv_link, tp_tipo_natureza, id_pesquisador, "+ 
									"tp_tipo_unidade, dt_data_cadastro) "+
									"VALUES ("+idMunicipio+", '"+nome+"', '"+lm.utf8_to_latin1(lm.ascii_to_latin1(site))+"', '"+natureza+"', "+ID_PESQUISADOR+", "+ 
									            "'HP', now());";
					System.out.println(sqlInsert);
					if(!lm.fastExecutarCUD(sqlInsert.replaceAll("'null'", "null").replaceAll("''", "null"))){
						 System.out.println("Erro!");
						 lm.fecharConexoes();
						 System.exit(1);
					 }
				}
            }
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	lm.fecharConexoes();
        }
	}
	
	private static void importacaoPlanilhaAndre() {
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", "200.133.41.8");
        try {
        	lm.criarConexao();
        	String caminho = "/Users/marlonassis/Downloads/Venezuela.xls";
        	//Cria um Arquivo Excel
            Workbook wb = new HSSFWorkbook(new FileInputStream(caminho));  
            //Cria uma planilha Excel
            Sheet sheet = wb.getSheet("Dados coletados");
            for(int i = 7; i < 53; i++){
            	Row row = sheet.getRow(i);
				if(row != null && row.getCell(0) != null && !row.getCell(0).getStringCellValue().trim().isEmpty()){
					String nome = row.getCell(0).getStringCellValue().trim().replaceAll("'", "");
					
					
					String comuna = row.getCell(2).getStringCellValue().trim();
					Integer idComuna = getIdMunicipio(comuna, lm);
					
					
					String tipoOrganizacao = row.getCell(4).getStringCellValue().trim();
					String tpNatureza = tipoOrganizacao.toLowerCase().equals("pœblico") ? "PU" : 
						(tipoOrganizacao.toLowerCase().equals("privado") ? "EP" : 
							(tipoOrganizacao.toLowerCase().equals("misto") ? "EM" : 
								(tipoOrganizacao.toLowerCase().equals("ong") ? "ONG" : null)));
					
					String siteProprio = row.getCell(5).getStringCellValue().trim();
					siteProprio = siteProprio.toLowerCase().equals("sim") ? "true" : (siteProprio.toLowerCase().equals("n‹o") ? "false" : null);
					
					String tipoPresencaWeb = row.getCell(6).getStringCellValue().trim();
					tipoPresencaWeb = tipoPresencaWeb.toLowerCase().equals("pr—prio") ? "P" : (tipoPresencaWeb.toLowerCase().equals("outro") ? "H" : null);
					
					
					String tecnologiasUtilizadas = row.getCell(7).getStringCellValue().trim();
					tecnologiasUtilizadas = tecnologiasUtilizadas.toLowerCase().equals("n/a") ? null : tecnologiasUtilizadas;
					
					
					String informacaoInstitucional = row.getCell(8).getStringCellValue().trim();
					informacaoInstitucional = informacaoInstitucional.toLowerCase().equals("sim") ? "true" : (informacaoInstitucional.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String informacaoServicos = row.getCell(9).getStringCellValue().trim();
					informacaoServicos = informacaoServicos.toLowerCase().equals("sim") ? "true" : (informacaoServicos.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String informacaoCuidadosSaude = row.getCell(10).getStringCellValue().trim();
					informacaoCuidadosSaude = informacaoCuidadosSaude.toLowerCase().equals("sim") ? "true" : (informacaoCuidadosSaude.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String indicacaoProcedimento = row.getCell(11).getStringCellValue().trim();
					indicacaoProcedimento = indicacaoProcedimento.toLowerCase().equals("sim") ? "true" : (indicacaoProcedimento.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String enderecoEletronico = row.getCell(12).getStringCellValue().trim();
					enderecoEletronico = enderecoEletronico.toLowerCase().equals("sim") ? "true" : (enderecoEletronico.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String marcacaoConsulta = row.getCell(13).getStringCellValue().trim().replace("\\?", "");
					marcacaoConsulta = marcacaoConsulta.toLowerCase().equals("sim") ? "true" : (marcacaoConsulta.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String tabelaCustos = row.getCell(14).getStringCellValue().trim();
					tabelaCustos = tabelaCustos.toLowerCase().equals("sim") ? "true" : (tabelaCustos.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String localizacao = row.getCell(15).getStringCellValue().trim();
					localizacao = localizacao.toLowerCase().equals("sim") ? "true" : (localizacao.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String informacaoCorpoClinico = row.getCell(16).getStringCellValue().trim();
					informacaoCorpoClinico = informacaoCorpoClinico.toLowerCase().equals("sim") ? "true" : (informacaoCorpoClinico.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String rastreio = row.getCell(17).getStringCellValue().trim();
					rastreio = rastreio.toLowerCase().equals("sim") ? "true" : (rastreio.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String consultaOnline = row.getCell(18).getStringCellValue().trim();
					consultaOnline = consultaOnline.toLowerCase().equals("sim") ? "true" : (consultaOnline.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String formularioDownload = row.getCell(19).getStringCellValue().trim();
					formularioDownload = formularioDownload.toLowerCase().equals("sim") ? "true" : (formularioDownload.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String formularioSubmissao = row.getCell(20).getStringCellValue().trim();
					formularioSubmissao = formularioSubmissao.toLowerCase().equals("sim") ? "true" : (formularioSubmissao.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String acessibilidade = row.getCell(21).getStringCellValue().trim();
					acessibilidade = acessibilidade.toLowerCase().equals("sim") ? "true" : (acessibilidade.toLowerCase().equals("n‹o") ? "false" : null);
					
					
					String redesSociais = row.getCell(22).getStringCellValue().trim();
					redesSociais = redesSociais.toLowerCase().equals("nao") ? null : redesSociais;
					
					
					String site = row.getCell(23) != null ? row.getCell(23).getStringCellValue().trim() : "";
					String observacoes = row.getCell(24) != null ? row.getCell(24).getStringCellValue().trim() : "";
					
					System.out.println("nome: " + nome);
					System.out.println(idComuna + " - comuna: " + comuna);
					System.out.println("tipoOrganizacao: " + tipoOrganizacao + "/" + tpNatureza);
					System.out.println("siteProprio: " + siteProprio);
					System.out.println("tipoPresencaWeb: " + tipoPresencaWeb);
					System.out.println("tecnologiasUtilizadas: " + tecnologiasUtilizadas);
					System.out.println("informacaoInstitucional: " + informacaoInstitucional);
					System.out.println("informacaoServicos: " + informacaoServicos);
					System.out.println("informacaoCuidadosSaude: " + informacaoCuidadosSaude);
					System.out.println("indicacaoProcedimento: " + indicacaoProcedimento);
					System.out.println("enderecoEletronico: " + enderecoEletronico);
					System.out.println("marcacaoConsulta: " + marcacaoConsulta);
					System.out.println("tabelaCustos: " + tabelaCustos);
					System.out.println("localizacao: " + localizacao);
					System.out.println("informacaoCorpoClinico: " + informacaoCorpoClinico);
					System.out.println("rastreio: " + rastreio);
					System.out.println("consultaOnline: " + consultaOnline);
					System.out.println("formularioDownload: " + formularioDownload);
					System.out.println("nome:" + formularioSubmissao);
					System.out.println("formularioSubmissao: " + acessibilidade);
					System.out.println("participaRedesSociais: " + redesSociais);
					System.out.println("site: " + site);
					System.out.println("observacoes: " + observacoes);
					
					System.out.println("+++++++++++++++++++++++++++++++++++");
					
					
					
					if(
							idComuna == null || 
							tpNatureza == null || 
							siteProprio == null || 
							informacaoInstitucional == null || 
							informacaoServicos == null || 
							informacaoCuidadosSaude == null || 
							indicacaoProcedimento == null || 
							enderecoEletronico == null || 
							marcacaoConsulta == null || 
							tabelaCustos == null || 
							localizacao == null || 
							informacaoCorpoClinico == null || 
							rastreio == null || 
							consultaOnline == null || 
							formularioDownload == null || 
							formularioSubmissao == null || 
							acessibilidade == null){
						System.out.println("Erro!!!");
						lm.fecharConexoes();
						System.exit(1);
					}
					
//					if(true)
//						continue;
					
					String sqlDeleteEsta = "delete from ehealth.tb_ehealth_estabelecimento where "+
							"cv_nome = '"+nome+
							"' and id_ehealth_municipio = "+ idComuna; 
					
					lm.fastExecutarCUD(sqlDeleteEsta);
					
					String sqlInsert = "INSERT INTO ehealth.tb_ehealth_estabelecimento( "+
									"id_ehealth_municipio, cv_nome,  "+
									"cv_link, tp_tipo_natureza, id_pesquisador, "+ 
									"tp_tipo_unidade, dt_data_cadastro) "+
									"VALUES ("+idComuna+", '"+nome+"', '"+lm.utf8_to_latin1(lm.ascii_to_latin1(site))+"', '"+tpNatureza+"', "+ID_PESQUISADOR+", "+ 
									            "'HP', now());";
					String replaceAll = sqlInsert.replaceAll("'null'", "null").replaceAll("''", "null");
					System.out.println(replaceAll);
					if(!lm.fastExecutarCUD(lm.ascii_to_latin1(lm.utf8_to_latin1(replaceAll)))){
						 System.out.println("Erro!");
						 lm.fecharConexoes();
						 System.exit(1);
					 }
					
					String sqlIdEstabelecimento = "select id_ehealth_estabelecimento from ehealth.tb_ehealth_estabelecimento where "+
							"cv_nome = '"+nome+
							"' and id_ehealth_municipio = "+ idComuna; 
					System.out.println(sqlIdEstabelecimento);
					 ResultSet rs = lm.fastConsulta(sqlIdEstabelecimento);
					 Integer idEstabe = null;
					 if(rs.next()){
						 idEstabe = rs.getInt("id_ehealth_estabelecimento");
						 if(rs.next()){
							 System.out.println("Foi encontrado mais de um estabelecimento para este critŽrio de busca.");
							 lm.fecharConexoes();
							 System.exit(1);
						 }
						 if(idEstabe == null || idEstabe.intValue() == 0){
							 System.out.println("N‹o foi poss’vel encontrar o estabelecimento.");
							 lm.fecharConexoes();
							 System.exit(1);
						 }
					 }
					 
					 String sqlInsertFormulario = "INSERT INTO ehealth.tb_ehealth_formulario( "+
										            "bl_possui_site_proprio, tp_tipo_presenca_web,  "+
										            "bl_informacao_institucional_hospital, bl_informacao_servico_prestado,  "+
										            "bl_informacao_prevencao_cuidados_saude, bl_procedimentos_emergencia_medica, "+ 
										            "bl_endereco_eletronico_recepcao, bl_marcacao_consulta, bl_tabela_custo_servico, "+ 
										            "bl_localizacao_meios_acesso, bl_corpo_clinico, bl_rastreio_medico_online,  "+
										            "bl_consulta_online, bl_disponibilizacao_formulario_download,  "+
										            "bl_acessibilidade, cv_observacao, bl_disponibilizacao_formulario_online, "+ 
										            "id_ehealth_estabelecimento, dt_data_criacao, id_pesquisador,  "+
										            "dt_data_cadastro) "+
										    "VALUES ("+siteProprio+", '"+tipoPresencaWeb+"', "+informacaoInstitucional+",  "+
										            informacaoServicos+", "+informacaoCuidadosSaude+",  "+indicacaoProcedimento
										            +", "+enderecoEletronico+",  "+marcacaoConsulta
										            +", "+tabelaCustos+", "+localizacao+",  "+informacaoCorpoClinico
										            +", "+rastreio+", "+consultaOnline+",  "+formularioDownload
										            +", "+acessibilidade+",  '"+observacoes
										            +"', "+formularioSubmissao+", "+idEstabe+",  "
										            +"now(), "+ID_PESQUISADOR+", now()); ";
					 System.out.println(lm.utf8_to_latin1(sqlInsertFormulario));
					 if(!lm.fastExecutarCUD(sqlInsertFormulario.replaceAll("'null'", "null").replaceAll("''", "null"))){
						 System.out.println("Erro!");
						 lm.fecharConexoes();
						 System.exit(1);
					 }
					 
					 String sqlFormulario = "select id_ehealth_formulario from ehealth.tb_ehealth_formulario where id_ehealth_estabelecimento = "+idEstabe;
					 ResultSet rs2 = lm.fastConsulta(sqlFormulario);
					 Integer idForm = null;
					 if(rs2.next()){
						 idForm = rs2.getInt("id_ehealth_formulario");
					 }
					 
					 if(redesSociais != null){
						 String[] redes = redesSociais.split(",");
						 for(String rede : redes){
							 for(TipoEhealthRedeSocialEnum tipo : TipoEhealthRedeSocialEnum.values()){
								 if(tipo.getLabel().equalsIgnoreCase(rede.trim())){
									 String sqlInsertRedeSocial = "INSERT INTO ehealth.tb_ehealth_formulario_rede_social( "+
														            "id_ehealth_formulario, tp_tipo_rede_social) "+
														            "VALUES ("+idForm+", '"+tipo.name()+"');";
									 System.out.println(sqlInsertRedeSocial);
									 if(!lm.fastExecutarCUD(lm.utf8_to_latin1(sqlInsertRedeSocial.replaceAll("'null'", "null").replaceAll("''", "null")))){
										 System.out.println("Erro!");
										 lm.fecharConexoes();
										 System.exit(1);
									 }
								 }
							 }
						 }
					 }
					
					 
					 if(tecnologiasUtilizadas != null){
						 String[] tecs = tecnologiasUtilizadas.split(",");
						 for(String tec : tecs){
							 for(TipoEhealthTipoTecnologiaEnum tipo : TipoEhealthTipoTecnologiaEnum.values()){
								 if(tipo.getLabel().equalsIgnoreCase(tec.trim())){
									 String sqlInsertTecnologia = "INSERT INTO ehealth.tb_ehealth_formulario_tecnologia( "+
														            "id_ehealth_formulario, tp_tipo_tecnologia) "+
																	    "VALUES ("+idForm+", '"+tipo.name()+"');";
									 System.out.println(lm.utf8_to_latin1(sqlInsertTecnologia));
									 if(!lm.fastExecutarCUD(sqlInsertTecnologia.replaceAll("'null'", "null").replaceAll("''", "null"))){
										 System.out.println("Erro!");
										 lm.fecharConexoes();
										 System.exit(1);
									 }
								 }
							 }
						 }
					 }
            	}
            }
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	lm.fecharConexoes();
        }
	}
	
	private static void argentina() {
//		carregarLocalidades();
		carregarEstabelecimento();
	}

	private static String getProvincia(int idProvincia) {
		try {
			File file = new File("/Users/marlonassis/Downloads/argentinadatabase_sql_xml_csv/sql/arg_provincias.sql");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String sCurrentLine = "";
			while ((sCurrentLine  = reader.readLine()) != null) {
				String[] b = sCurrentLine.replace('(', ' ').replace(')', ' ').replace('\'', ' ').split(",");
				if(b[0].trim().equals(String.valueOf(idProvincia)) )
					return b[1].trim();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	private static String getDepartamento(int idDepartamento) {
		try {
			File file = new File("/Users/marlonassis/Downloads/argentinadatabase_sql_xml_csv/sql/arg_departamentos.sql");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String sCurrentLine = "";
			while ((sCurrentLine  = reader.readLine()) != null) {
				String[] b = sCurrentLine.replace('(', ' ').replace(')', ' ').replace('\'', ' ').split(",");
				if(b[0].trim().equals(String.valueOf(idDepartamento)) )
					return b[2].trim();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	private static void carregarLocalidades() {
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", "200.133.41.8");
		try {
			File file = new File("/Users/marlonassis/Downloads/argentinadatabase_sql_xml_csv/sql/arg_localidades.sql");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String sCurrentLine = "";
			lm.criarConexao();
			int cont = 0;
			while ((sCurrentLine  = reader.readLine()) != null) {
				cont++;
				if(cont < 2)
					continue;
				
				String[] b = sCurrentLine.replace('(', ' ').replace(')', ' ').replace('\'', ' ').split(",");
				String departamento = getDepartamento(Integer.valueOf(b[1].trim()));
				String provincia = getProvincia(Integer.valueOf(b[2].trim())).replaceAll(";", "");
				String sql = "select id_ehealth_estado from ehealth.tb_ehealth_estado where trim(lower(to_ascii(cv_nome))) ilike to_ascii('%"+provincia.trim()+"%')";
				ResultSet consultar = lm.fastConsulta(sql);
				Integer idEstado = null;
				if(consultar.next()){
					idEstado = consultar.getInt("id_ehealth_estado");
				}
				
				if(departamento == null || departamento.isEmpty() || idEstado == null || idEstado == 0 || departamento == null || provincia == null){
					System.out.println(departamento + " - " + idEstado + " - " + provincia);
					System.out.println(sql);
					lm.fecharConexoes();
					System.exit(1);
				}
				
				
			String sqlInsert = "INSERT INTO ehealth.tb_ehealth_municipio( "+
			             "cv_nome, id_ehealth_estado, bl_capital, cv_departamento) "+
			             "VALUES ('"+b[3].trim()+"', "+idEstado+", false, '"+departamento+"'); ";
			
			System.out.println(cont + " - " + sqlInsert);
			
			if(!new LinhaMecanica("db_imhotep", "200.133.41.8").executarCUD(sqlInsert)){
				gerarErro("/Users/marlonassis/Desktop/erro.txt", cont + " - " + sqlInsert);
			}
			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			lm.fecharConexoes();
		}
	}	
	
	private static void gerarErro(String caminho, String conteudo) {
		try {
			PrintStream gravador;
			File arquivo = new File(caminho);
			FileOutputStream arquivoOutput;
			arquivoOutput = new FileOutputStream(arquivo, true);
			gravador = new PrintStream(arquivoOutput);
			gravador.println(conteudo);
			gravador.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean contemLocalidade(String local, String... locais) {
		
		for(String l : locais){
			if(l.equals(local)){
				return true;
			}
		}
		
		return false;
	}
	
	private static void carregarEstabelecimento() {
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", "200.133.41.8");
		try {
			File file = new File("/Users/marlonassis/Desktop/estabelecimentosArgentina.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String sCurrentLine = "";
			lm.criarConexao();
			int cont = 0;
			int cont2 = 0;
			while((sCurrentLine  = reader.readLine()) != null) {
				String[] b = sCurrentLine.trim().split(",");
				String localidade = b[3].replace(";", "").replaceAll("\\(.+?\\)", "").trim();
				cont++;
				if((!sCurrentLine.toLowerCase().contains("hospital") 
						|| contemLocalidade(localidade, "La Matanza", "Benito Ju‡rez", "Juan CoustŽ", "Lezama", "Marcos Paz", "Melchor Romero")) 
						)
					continue;
				
				String nomeEstabelecimento = b[0].trim();
				String financiamento = b[1].trim().equalsIgnoreCase("Privado") ? "EP" : (b[1].trim().equalsIgnoreCase("Publico") ? "PU" : null);
				String estado = b[2].trim();
				String municipio = b[3].replace(";", "").replaceAll("\\(.+?\\)", "").trim();
				String internacao = b[4].equals("Sim") ? "true" : (b[4].equals("No") ? "false" : null);
				String macas = b[5].trim();
				
				
				Integer idLocalidade = getIdMunicipioPeloEstado(lm.utf8_to_latin1(lm.ascii_to_latin1(estado)), 
																lm.utf8_to_latin1(lm.ascii_to_latin1(municipio)), lm);
				
				if(!(idLocalidade == null || idLocalidade == 0 || financiamento == null)){
					String sqlInsert = "INSERT INTO ehealth.tb_ehealth_estabelecimento( "+
							"id_ehealth_municipio, cv_nome,   "+
							"cv_razao_social, tp_tipo_natureza,  "+
							"id_pesquisador, tp_tipo_unidade, dt_data_cadastro, in_quantidade_macas, bl_internacao) "+
							"VALUES ("+idLocalidade+", '"+nomeEstabelecimento+"', null, '"+financiamento+"', null, 'HP', now(), "+macas+", "+internacao+");";
					sqlInsert = lm.utf8_to_latin1(lm.ascii_to_latin1(sqlInsert));
					System.out.println(cont + " - " + sqlInsert);
					cont2++;
					if(!lm.fastExecutarCUD(sqlInsert.replaceAll("'null'", "null"))){
						gerarErro("/Users/marlonassis/Desktop/erroEstabelecimento.txt", cont + " - " + sqlInsert);
					}
				}
			}
			System.out.println(cont2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			lm.fecharConexoes();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void gerarEstatisticaBasica(){
		String sql = "select d.cv_nome pais, "+
						"(case when b.bl_capital then 'Sim' else 'N‹o' end) capital, count(a) estabelecimentos, count(e) pesquisados "+
						"from ehealth.tb_ehealth_estabelecimento a  "+
						"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
						"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado  "+
						"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais   "+
						"left join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+
						"where d.id_ehealth_pais != 1 "+
						"group by d.cv_nome, capital "+
						"order by d.cv_nome, capital desc";	
		
		
        try {
        	
        	String caminho = "/Users/marlonassis/Desktop/estatisticaParcial.xls";
        	String modelo = "/Users/marlonassis/Desktop/estatisticaParcial.xls";
//        	new File(caminho).createNewFile();
//        	gerarArquivo(caminho, modelo);
//            Workbook wb = new HSSFWorkbook(new FileInputStream(caminho));  
//            Sheet sheet = wb.getSheet("Totais");   
            
            
            
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Merge Cells");   
            
            int i = 0;
            LinhaMecanica lm = new LinhaMecanica("db_imhotep", "200.133.41.8");
            ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
			try {
				String pais = "";
				int linhaInicial = -8;
				while (rs.next()) {
					if(!pais.equals(rs.getString("pais"))){
						linhaInicial += 8;
						i = linhaInicial;
						pais = rs.getString("pais");
						System.out.println(pais);
						
						//criando linha da capital
						sheet.createRow((short)linhaInicial+2);
						
						//linha inicial
						Row dados = sheet.createRow((short)i);
						dados.createCell(1).setCellValue(rs.getString("pais"));
						
						//pr—xima linha
						i++;
						dados = sheet.createRow((short)i);
						dados.createCell(1).setCellValue("Capital");
						dados.createCell(3).setCellValue("Hospitais");
						//pr—xima linha
						i += 2;
						dados = sheet.createRow((short)i);
						dados.createCell(0).setCellValue("Total Cadastrado");
						dados.createCell(3).setCellFormula("sum(Bi+Ci)".replaceAll("i", String.valueOf((i+1))));
						//pr—xima linha
						i ++;
						dados = sheet.createRow((short)i);
						dados.createCell(0).setCellValue("Total Pesquisado");
						dados.createCell(3).setCellFormula("sum(Bi+Ci)".replaceAll("i", String.valueOf((i+1))));
						//pr—xima linha
						i++;
						dados = sheet.createRow((short)i);
						dados.createCell(0).setCellValue("% pesquisado");
						dados.createCell(1).setCellFormula("(B"+i+"/B"+(i-1)+")*100".replaceAll("i", String.valueOf((i+1))));
						dados.createCell(2).setCellFormula("(C"+i+"/C"+(i-1)+")*100".replaceAll("i", String.valueOf((i+1))));
						dados.createCell(3).setCellFormula("(D"+i+"/D"+(i-1)+")*100".replaceAll("i", String.valueOf((i+1))));
						
						//fazendo mege das celulas
						sheet.addMergedRegion(CellRangeAddress.valueOf("Bi:Di".replaceAll("i", String.valueOf(linhaInicial+1))));
						sheet.addMergedRegion(CellRangeAddress.valueOf("Bi:Ci".replaceAll("i", String.valueOf(linhaInicial+2))));
						sheet.addMergedRegion(CellRangeAddress.valueOf("D"+(linhaInicial+2)+":D"+(linhaInicial+3)));
					}
					
					Row dados = sheet.getRow((short)linhaInicial+2);
					String capital = rs.getString("capital");
					int celula = 0;
					if(capital.equals("Sim")){
						celula = 1;
					}
					else{
						capital = "N‹o";
						celula = 2;
					}
					
					dados.createCell(celula).setCellValue(capital);
					dados = sheet.getRow((short)linhaInicial+3);
					dados.createCell(celula).setCellValue(rs.getInt("estabelecimentos"));
					dados = sheet.getRow((short)linhaInicial+4);
					dados.createCell(celula).setCellValue(rs.getInt("pesquisados"));
					
					System.out.println(linhaInicial);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
			FileOutputStream out = new FileOutputStream(new File(caminho));
            wb.write(out);
            out.close();
            System.out.println("FIM");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();

        }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void gerarResultadosFinais(){
		String[][] paises = {{"Brasil", "2", "C"}, {"Chile", "3", "D"}, {"Col™mbia", "4", "E"}, {"Equador", "5", "F"}, {"Guiana", "6", "G"}, 
				{"Bol’via", "7", "H"}, {"Peru", "8", "I"}, {"Paraguai", "9", "J"}, {"Suriname", "10", "K"}, {"Argentina", "11", "L"}, 
				{"Venezuela", "12", "M"}, {"Uruguai", "13", "N"}, {"Guiana Francesa", "14", "O"}, {"Ilhas Malvinas", "15", "P"}};
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", "200.133.41.8");
		try{
			lm.criarConexao();
			Workbook wb = null;
			Sheet sheet = null;
			Row dados = null;
			String caminho = "/Users/marlonassis/Desktop/e-health/AmericaLatina/DadosConsolidadosAS.xls";
			new File(caminho).delete();
			String modelo = "/Users/marlonassis/Desktop/e-health/Modelo/DadosConsolidadosAS.xls";
			gerarArquivo(caminho, modelo);
			//Cria um Arquivo Excel
			wb = new HSSFWorkbook(new FileInputStream(caminho));  
			//Cria uma planilha Excel
			sheet = wb.getSheet("Geral");
			CellStyle stylePercent = wb.createCellStyle();
			stylePercent.setDataFormat(wb.createDataFormat().getFormat("0.00%"));
			
			
			
			for(String linha[] : paises){
				String pais = linha[0];
				Integer coluna = Integer.valueOf(linha[1]);
				String letra = linha[2];
				System.out.println(pais);
				
				ResultSet rs;
				//Add total encontrado com total pesquisado
				gerarDadosTotalPesquisadoTotalEncontrado(lm, sheet, pais, coluna);
				
				//add qtd capital interior
				rs = lm.fastConsulta(getSqlCapitalInterior().replaceAll("#pais#", pais));
				gerarCapitalInterior(sheet, coluna, rs);
				
				//add tipo de organiza‹o
				rs = lm.fastConsulta(getSqlNatureza().replaceAll("#pais#", pais));
				gerarDadosTipoOrganizacao(sheet, stylePercent, coluna, letra, rs);
				
				//Totais com e sem site
				rs = lm.fastConsulta(getSqlTotalComSemSite().replaceAll("#pais#", pais));
				gerarDadosComSemSite(sheet, stylePercent, coluna, letra, rs);
				
				//Total com site na capital e no interior
				gerarDadosTotalCapitalInterior(lm, sheet, pais, coluna);
				
				
				//Totais presena na web
				rs = lm.fastConsulta(getSqlPresencaWeb().replaceAll("#pais#", pais));
				gerarDadosPresencaWebComSite(sheet, coluna, rs);
				
				//Totais critŽrios pesquisa
				gerarDadosPropriedades(lm, sheet, pais, coluna);
				
				//Listagem de redes sociais
				gerarDadosRedeSocial(lm, sheet, pais, coluna);

				
			}
			FileOutputStream out = new FileOutputStream(new File("/Users/marlonassis/Desktop/e-health/AmericaLatina/DadosConsolidadosAS.xls"));
	        wb.write(out);
	        out.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lm.fecharConexoes();
		}
	}

	private static void gerarDadosTotalCapitalInterior(LinhaMecanica lm, Sheet sheet, String pais,
			Integer coluna) throws SQLException {
		Row dados;
		ResultSet rs;
		rs = lm.fastConsulta(getSqlTotalPesquisadoCapitalInterior().replaceAll("#pais#", pais));
		rs.next();
		Long totalCapital = rs.getLong("capital");
		Long totalInterior = rs.getLong("interior");
		dados = sheet.getRow((short)67);
		dados.createCell(coluna).setCellValue(totalCapital);
		dados = sheet.getRow((short)68);
		dados.createCell(coluna).setCellValue(totalInterior);
	}

	private static void gerarDadosTotalPesquisadoTotalEncontrado(LinhaMecanica lm, Sheet sheet, String pais,
			Integer coluna) throws SQLException {
		Row dados;
		ResultSet rs = lm.fastConsulta(getSqlTotalEncontradoTotalPesquisado().replaceAll("#pais#", pais));
		rs.next();
		Long total = rs.getLong("total");
		Long pesquisado = rs.getLong("pesquisado");
		dados = sheet.getRow((short)20);
		dados.createCell(coluna).setCellValue(total);
		dados = sheet.getRow((short)21);
		dados.createCell(coluna).setCellValue(pesquisado);
	}

	private static void gerarDadosRedeSocial(LinhaMecanica lm, Sheet sheet, String pais,
			Integer coluna) throws SQLException {
		Row dados;
		ResultSet rs;
		rs = lm.fastConsulta(getSqlTecnologiasUtilizadas().replaceAll("#pais#", pais));
		int linhaRede = 151;
		while(rs.next()){
			String sigla = rs.getString("redeSocial");
			for(TipoEhealthRedeSocialEnum redeSocial : TipoEhealthRedeSocialEnum.values()){
				if(redeSocial.name().equals(sigla)){
					dados = sheet.getRow((short)linhaRede);
					if(dados == null)
						dados = sheet.createRow((short)linhaRede);
					dados.createCell(coluna).setCellValue(redeSocial.getLabel());
					linhaRede++;
				}
			}
		}
	}

	private static void gerarDadosPropriedades(LinhaMecanica lm, Sheet sheet, String pais,
			Integer coluna) throws SQLException {
		Row dados;
		ResultSet rs;
		String[][] camposPesquisa = {
				{"bl_informacao_institucional_hospital", "109", "110"},
				{"bl_informacao_servico_prestado", "112", "113"},
				{"bl_informacao_prevencao_cuidados_saude", "115", "116"},
				{"bl_procedimentos_emergencia_medica", "118", "119"},
				{"bl_endereco_eletronico_recepcao", "121", "122"},
				{"bl_marcacao_consulta", "124", "125"},
				{"bl_tabela_custo_servico", "127", "128"},
				{"bl_localizacao_meios_acesso", "130", "131"},
				{"bl_corpo_clinico", "133", "134"},
				{"bl_rastreio_medico_online", "136", "137"},
				{"bl_consulta_online", "139", "140"},
				{"bl_disponibilizacao_formulario_download", "142", "143"},
				{"bl_disponibilizacao_formulario_online", "145", "146"},
				{"bl_acessibilidade", "148", "149"}};
		
		for(String[] campo : camposPesquisa){
			String propriedade = campo[0];
			Integer linhaSim = Integer.valueOf(campo[1]);
			Integer linhaNao = Integer.valueOf(campo[2]);
			rs = lm.fastConsulta(getSqlPropriedadeSimNao().replaceAll("#pais#", pais).replaceAll("#propriedade#", propriedade));
			rs.next();
			Long sim = rs.getLong("sim");
			Long nao = rs.getLong("nao");
			dados = sheet.getRow((short)linhaSim.intValue());
			dados.createCell(coluna).setCellValue(sim);
			dados = sheet.getRow((short)linhaNao.intValue());
			dados.createCell(coluna).setCellValue(nao);
			
		}
	}

	private static void gerarDadosPresencaWebComSite(Sheet sheet, Integer coluna, ResultSet rs)
			throws SQLException {
		Row dados;
		Map<String, Long> map = new HashMap<String, Long>();
		
		if(rs.next()){
			String tipo = rs.getString("presenca");
			long qtd = rs.getLong("total");
			map.put(tipo, qtd);
		}
		
		if(rs.next()){
			String tipo = rs.getString("presenca");
			long qtd = rs.getLong("total");
			map.put(tipo, qtd);
		}
		
		//hospedado
		long qtd = 0;
		if(map.containsKey("H")){
			qtd = map.get("H");
		}
		dados = sheet.getRow((short)107);
		dados.createCell(coluna).setCellValue(qtd);
		//pr—prio
		qtd = 0;
		if(map.containsKey("P")){
			qtd = map.get("P");
		}
		dados = sheet.getRow((short)106);
		dados.createCell(coluna).setCellValue(qtd);
	}

	private static void gerarDadosComSemSite(Sheet sheet, CellStyle stylePercent,
			Integer coluna, String letra, ResultSet rs) throws SQLException {
		Row dados;
		rs.next();
		Long comSite = rs.getLong("comSite");
		Long semSite = rs.getLong("semSite");
		//com site
		dados = sheet.getRow((short)61);
		dados.createCell(coluna).setCellValue(comSite);
		dados = sheet.getRow((short)62);
		dados.createCell(coluna).setCellFormula("("+letra+"62/"+letra+"22)");
		dados.getCell(coluna).setCellStyle(stylePercent);
		
		//sem site
		dados = sheet.getRow((short)63);
		dados.createCell(coluna).setCellValue(semSite);
		dados = sheet.getRow((short)64);
		dados.createCell(coluna).setCellFormula("("+letra+"64/"+letra+"22)");
		dados.getCell(coluna).setCellStyle(stylePercent);
	}

	private static void gerarDadosTipoOrganizacao(Sheet sheet, CellStyle stylePercent,
			Integer coluna, String letra, ResultSet rs) throws SQLException {
		Row dados;
		while(rs.next()){
			Long total = rs.getLong("total");
			Long comSite = rs.getLong("comsite");
			String natureza = rs.getString("natureza");
			int linhaPosNatu[] = getLinhaNatureza(natureza);
			
			dados = sheet.getRow((short)linhaPosNatu[0]);
			dados.createCell(coluna).setCellValue(total);
			
			dados = sheet.getRow((short)linhaPosNatu[1]);
			dados.createCell(coluna).setCellValue(comSite);
			
			dados = sheet.getRow((short)linhaPosNatu[0]+1);
			dados.createCell(coluna).setCellFormula("("+letra+(linhaPosNatu[0]+1)+"/"+letra+"21)");
			dados.getCell(coluna).setCellStyle(stylePercent);
			
			dados = sheet.getRow((short)linhaPosNatu[1]+1);
			dados.createCell(coluna).setCellFormula("("+letra+(linhaPosNatu[1]+1)+"/"+letra+"21)");
			dados.getCell(coluna).setCellStyle(stylePercent);
			
		}
	}

	private static int[] getLinhaNatureza(String natureza){
		int linha[] = {0, 0};
		
		if(natureza == null){
			linha[0] = 58;
			linha[1] = 103;
			return linha;
		}
		
		if(natureza.equals("EP")){
			linha[0] = 28;
			linha[1] = 71;
			return linha;
		}
		if(natureza.equals("AIOS")){
				linha[0] = 30;
				linha[1] = 73;
				return linha;
		}
		if(natureza.equals("COO")){
				linha[0] = 32;
				linha[1] = 75;
				return linha;
		}
		if(natureza.equals("ONG")){
				linha[0] = 34;
				linha[1] = 77;
				return linha;
		}
		if(natureza.equals("EM")){
				linha[0] = 36;
				linha[1] = 79;
				return linha;
		}
		if(natureza.equals("AIEP")){
				linha[0] = 38;
				linha[1] = 81;
				return linha;
		}
		if(natureza.equals("EBFL")){
				linha[0] = 40;
				linha[1] = 83;
				return linha;
		}
		if(natureza.equals("ADOO")){
				linha[0] = 42;
				linha[1] = 85;
				return linha;
		}
		if(natureza.equals("PU")){
				linha[0] = 44;
				linha[1] = 87;
				return linha;
		}
		if(natureza.equals("FP")){
				linha[0] = 46;
				linha[1] = 89;
				return linha;
		}
		if(natureza.equals("ADS")){
				linha[0] = 48;
				linha[1] = 91;
				return linha;
		}
		if(natureza.equals("AIA")){
				linha[0] = 50;
				linha[1] = 93;
				return linha;
		}
		if(natureza.equals("AIFP")){
				linha[0] = 52;
				linha[1] = 95;
				return linha;
		}
		if(natureza.equals("SIN")){
				linha[0] = 54;
				linha[1] = 97;
				return linha;
		}
		if(natureza.equals("SSA")){
				linha[0] = 56;
				linha[1] = 99;
				return linha;
		}
		
		return linha;
	}
	
	private static void gerarCapitalInterior(Sheet sheet, Integer coluna, ResultSet rs) throws SQLException {
		Row dados;
		Map<String, Long> map = new HashMap<String, Long>();
		
		if(rs.next()){
			long qtd = rs.getLong("estabelecimentos");
			String tipo = rs.getString("capital");
			map.put(tipo, qtd);
		}
		
		if(rs.next()){
			long qtd = rs.getLong("estabelecimentos");
			String tipo = rs.getString("capital");
			map.put(tipo, qtd);
		}
		
		//capital
		long qtd = 0;
		if(map.containsKey("Capital")){
			qtd = map.get("Capital");
		}
		dados = sheet.getRow((short)23);
		dados.createCell(coluna).setCellValue(qtd);
		//interior
		qtd = 0;
		if(map.containsKey("Interior")){
			qtd = map.get("Interior");
		}
		dados = sheet.getRow((short)25);
		dados.createCell(coluna).setCellValue(qtd);
	}
	
	private static String getSqlTotalPesquisadoCapitalInterior(){
		return "select "+
				"(select count(a) "+ 
				"from ehealth.tb_ehealth_estabelecimento a "+ 
				"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
				"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado  "+
				"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais  "+
				"inner join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+
				"where d.cv_nome = '#pais#' and e.bl_possui_site_proprio is true and b.bl_capital is true) capital, "+
				"(select count(a)  "+
				"from ehealth.tb_ehealth_estabelecimento a "+ 
				"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
				"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado  "+
				"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais  "+
				"inner join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+
				"where d.cv_nome = '#pais#' and e.bl_possui_site_proprio is true and b.bl_capital is false) interior ";
	}
	
	private static String getSqlTotalEncontradoTotalPesquisado(){
		return "select  "+
				"(select count(e) "+ 
				"from ehealth.tb_ehealth_estabelecimento a "+  
				"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
				"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado    "+
				"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais    "+
				"left join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+ 
				"where d.cv_nome = '#pais#' and e.id_ehealth_formulario is not null) pesquisado,  "+
				"(select count(a)  "+
				"from ehealth.tb_ehealth_estabelecimento a "+  
				"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
				"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado    "+
				"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais    "+
				"where d.cv_nome = '#pais#') total";
	}
	
	private static String getSqlPropriedadeSimNao(){
		return "select (select count(e.#propriedade#)  "+
				"from ehealth.tb_ehealth_estabelecimento a   "+
				"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
				"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado  "+
				"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais    "+
				"inner join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+ 
				"where d.cv_nome = '#pais#' and e.bl_possui_site_proprio is true and e.#propriedade# is true) sim, "+
				"(select count(e.#propriedade#)  "+
				"from ehealth.tb_ehealth_estabelecimento a   "+
				"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
				"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado  "+
				"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais    "+
				"inner join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+ 
				"where d.cv_nome = '#pais#' and e.bl_possui_site_proprio is true and e.#propriedade# is false) nao ";
	}
	
	private static String getSqlTecnologiasUtilizadas(){
		return "select f.tp_tipo_rede_social redeSocial "+
				"from ehealth.tb_ehealth_estabelecimento a "+  
				"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
				"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado  "+
				"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais    "+
				"inner join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+ 
				"inner join ehealth.tb_ehealth_formulario_rede_social f on f.id_ehealth_formulario = e.id_ehealth_formulario "+
				"where d.cv_nome = '#pais#'  "+
				"group by f.tp_tipo_rede_social";
	}
	
	private static String getSqlPresencaWeb(){
		return "select e.tp_tipo_presenca_web presenca, count(a) total "+ 
				"from ehealth.tb_ehealth_estabelecimento a  "+
				"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
				"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado "+  
				"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais   "+
				"inner join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+ 
				"where d.cv_nome = '#pais#'"+
				"group by e.tp_tipo_presenca_web "+
				"order by e.tp_tipo_presenca_web";
	}
	
	private static String getSqlCapitalInterior(){
		return "select d.cv_nome pais, (case when b.bl_capital then 'Capital' else 'Interior' end) capital, count(a) estabelecimentos, count(e) formularios "+
				"from ehealth.tb_ehealth_estabelecimento a  "+
				"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
				"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado "+ 
				"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais  "+
				"left join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+
				"where d.cv_nome = '#pais#' "+
				"group by d.cv_nome, b.bl_capital "+
				"order by d.cv_nome";
	}
	
	private static String getSqlTotalComSemSite(){
		return "select count(a) total, count(f) comSite, count(g) semSite "+ 
				"from ehealth.tb_ehealth_estabelecimento a  "+
				"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
				"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado   "+
				"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais   "+
				"left join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+ 
				"left join ehealth.tb_ehealth_formulario f on f.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento and f.bl_possui_site_proprio is true "+ 
				"left join ehealth.tb_ehealth_formulario g on g.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento and g.bl_possui_site_proprio is false  "+
				"where d.cv_nome = '#pais#' ";
	}
	
	private static String getSqlNatureza(){
		return "select a.tp_tipo_natureza natureza, count(a) total, count(f) comSite, count(g) semSite "+
				"from ehealth.tb_ehealth_estabelecimento a "+ 
				"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
				"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado  "+
				"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais  "+
				"left join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+
				"left join ehealth.tb_ehealth_formulario f on f.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento and f.bl_possui_site_proprio is true "+
				"left join ehealth.tb_ehealth_formulario g on g.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento and g.bl_possui_site_proprio is false "+
				"where d.cv_nome = '#pais#' "+
				"group by a.tp_tipo_natureza "+
				"order by a.tp_tipo_natureza";
	}
	
	
	
	
	public static void gerarPlanilhas(){
		String sql = "select e.id_ehealth_formulario idForm, d.cv_nome pais, c.cv_nome estado, b.cv_nome municipio, "+
						"(case when b.bl_capital then 'Capital' else 'Interior' end) capital, upper(a.cv_nome) estabelecimento, a.tp_tipo_natureza natureza, "+
						"e.bl_possui_site_proprio siteProprio, e.tp_tipo_presenca_web tipoPresenca, e.bl_informacao_institucional_hospital informacoes,  "+
						"e.bl_informacao_servico_prestado servicoPrestado, e.bl_informacao_prevencao_cuidados_saude prevencao, e.bl_procedimentos_emergencia_medica procedimentosEmergencia, "+
						"e.bl_endereco_eletronico_recepcao emailRecepcao, e.bl_marcacao_consulta macacaoConsulta, e.bl_tabela_custo_servico tabela, e.bl_localizacao_meios_acesso localizacao, "+
						"e.bl_corpo_clinico corpoClinico, e.bl_rastreio_medico_online rastreio, e.bl_consulta_online consulta, e.bl_disponibilizacao_formulario_download formularioDownload, "+
						"e.bl_disponibilizacao_formulario_online formularioOnline, e.bl_acessibilidade acessibilidade, e.cv_observacao, a.cv_link site "+
						"from ehealth.tb_ehealth_estabelecimento a  "+
						"inner join ehealth.tb_ehealth_municipio b on a.id_ehealth_municipio = b.id_ehealth_municipio "+ 
						"inner join ehealth.tb_ehealth_estado c on b.id_ehealth_estado = c.id_ehealth_estado  "+
						"inner join ehealth.tb_ehealth_pais d on c.id_ehealth_pais = d.id_ehealth_pais  "+
						"inner join ehealth.tb_ehealth_formulario e on e.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+
//						"where d.id_ehealth_pais != 1  "+
						"group by d.cv_nome, c.cv_nome, b.cv_nome, capital, a.cv_nome, a.tp_tipo_natureza, e.id_ehealth_formulario, "+
						"e.bl_possui_site_proprio, e.tp_tipo_presenca_web, e.bl_informacao_institucional_hospital,  "+
						"e.bl_informacao_servico_prestado, e.bl_informacao_prevencao_cuidados_saude, e.bl_procedimentos_emergencia_medica, "+
						"e.bl_endereco_eletronico_recepcao, e.bl_marcacao_consulta, e.bl_tabela_custo_servico, e.bl_localizacao_meios_acesso, "+
						"e.bl_corpo_clinico, e.bl_rastreio_medico_online, e.bl_consulta_online, e.bl_disponibilizacao_formulario_download, "+
						"e.bl_disponibilizacao_formulario_online, e.bl_acessibilidade, e.cv_observacao, a.cv_link "+
						"order by d.cv_nome, c.cv_nome, capital desc, b.cv_nome";
			
			try {
				LinhaMecanica lm = new LinhaMecanica("db_imhotep", "200.133.41.8");
				lm.criarConexao();
				ResultSet rs = lm.fastConsulta(sql);
				String modelo = "/Users/marlonassis/Desktop/e-health/Modelo/modelo.xls";
				
				int i = 7;
    			try {
    				String pais = "";
    				Workbook wb = null;
    				Sheet sheet = null;
    				Row dados = null;
    				while (rs.next()) {
    					if(!pais.equals(rs.getString("pais"))){
    						if(!pais.equals("")){
    							FileOutputStream out = new FileOutputStream(new File("/Users/marlonassis/Desktop/e-health/AmericaLatina/"+pais+".xls"));
    				            wb.write(out);
    				            out.close();
    						}
    						i = 7;
	    					pais = rs.getString("pais");
	    					System.out.println();
	    					System.out.println(pais);
	    					String caminho = "/Users/marlonassis/Desktop/e-health/AmericaLatina/"+pais+".xls";
	    					new File(caminho).delete();
	    					gerarArquivo(caminho, modelo);
	    					//Cria um Arquivo Excel
	    					wb = new HSSFWorkbook(new FileInputStream(caminho));  
	    					//Cria uma planilha Excel
	    					sheet = wb.getSheet("Hospitais");
	    					//adicionando o nome do pesquisador
//		    					dados = sheet.getRow((short)3);
//		    					dados.createCell(2).setCellValue(rs.getString("profissisonal"));
	    					//////
    					}
    					dados = sheet.createRow((short)i);
//							dados.createCell(0).setCellValue(id);
						dados.createCell(0).setCellValue(rs.getString("estabelecimento"));
    					dados.createCell(1).setCellValue(rs.getString("pais"));
    					String localizacao = rs.getString("estado")+"/"+rs.getString("municipio");
						dados.createCell(2).setCellValue(localizacao);
    					dados.createCell(3).setCellValue(rs.getString("capital"));
    					String natureza = rs.getString("natureza");
    					natureza = conversaoNatureza(natureza);
						dados.createCell(4).setCellValue(natureza);
    					dados.createCell(5).setCellValue(converteResultadoBooleano(rs.getBoolean("siteProprio")));
    					
    					String presenca = rs.getString("tipopresenca");
						dados.createCell(6).setCellValue(presenca == null ? "N/A" : presenca.equals("H") ? "Hospedado" : presenca.equals("P") ? "Pr—prio" : "");
						
						int idForm = rs.getInt("idForm");
    					dados.createCell(7).setCellValue(tecnologias(idForm, lm));
    					
    					dados.createCell(8).setCellValue(converteResultadoBooleano(rs.getBoolean("informacoes")));
    					dados.createCell(9).setCellValue(converteResultadoBooleano(rs.getBoolean("servicoPrestado")));
    					dados.createCell(10).setCellValue(converteResultadoBooleano(rs.getBoolean("prevencao")));
    					dados.createCell(11).setCellValue(converteResultadoBooleano(rs.getBoolean("procedimentosEmergencia")));
    					dados.createCell(12).setCellValue(converteResultadoBooleano(rs.getBoolean("emailRecepcao")));
    					dados.createCell(13).setCellValue(converteResultadoBooleano(rs.getBoolean("macacaoConsulta")));
    					dados.createCell(14).setCellValue(converteResultadoBooleano(rs.getBoolean("tabela")));
    					dados.createCell(15).setCellValue(converteResultadoBooleano(rs.getBoolean("localizacao")));
    					dados.createCell(16).setCellValue(converteResultadoBooleano(rs.getBoolean("corpoClinico")));
    					dados.createCell(17).setCellValue(converteResultadoBooleano(rs.getBoolean("rastreio")));
    					dados.createCell(18).setCellValue(converteResultadoBooleano(rs.getBoolean("consulta")));
    					dados.createCell(19).setCellValue(converteResultadoBooleano(rs.getBoolean("formularioDownload")));
    					dados.createCell(20).setCellValue(converteResultadoBooleano(rs.getBoolean("formularioOnline")));
    					dados.createCell(21).setCellValue(converteResultadoBooleano(rs.getBoolean("acessibilidade")));
    					dados.createCell(22).setCellValue(redesSociais(idForm, lm));
    					dados.createCell(23).setCellValue(rs.getString("site"));
    					dados.createCell(24).setCellValue(rs.getString("cv_observacao"));
    					i++;
    					System.out.print(i+"-");
    				}
    				String caminho = "/Users/marlonassis/Desktop/e-health/AmericaLatina/"+pais+".xls";
					new File(caminho).delete();
					gerarArquivo(caminho, modelo);
					FileOutputStream out = new FileOutputStream(new File("/Users/marlonassis/Desktop/e-health/AmericaLatina/"+pais+".xls"));
					wb.write(out);
					out.close();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}finally{
    				lm.fecharConexoes();
    			}
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
			
		}

	
	private static String conversaoNatureza(String natureza){
		for(TipoEhealthNaturezaEnum tipo : TipoEhealthNaturezaEnum.values()){
			if(tipo.name().equals(natureza)){
				tipo.getLabel();
			}
		}
		return "Pœblico";
	}
	
	private static String tecnologias(Integer id, LinhaMecanica lm){
		String sql = "select a.tp_tipo_tecnologia as tecno from ehealth.tb_ehealth_formulario_tecnologia a "+
					 "where a.id_ehealth_formulario = "+id;
		String redesSociais = "";
		ResultSet rs = lm.fastConsulta(lm.utf8_to_latin1(sql));
		try {
			while (rs.next()) {
				String tecno = rs.getString("tecno");
				for(TipoEhealthTipoTecnologiaEnum tipo : TipoEhealthTipoTecnologiaEnum.values()){
					if(tecno.equals(tipo.name())){
						if(!redesSociais.isEmpty())
							redesSociais = redesSociais.concat(", ");
						redesSociais = redesSociais.concat(tipo.getLabel());
						break;
					}
				}
			}
			return redesSociais;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "erro";
	}
	
	private static String redesSociais(Integer id, LinhaMecanica lm){
		String sql = "select a.tp_tipo_rede_social as rede from ehealth.tb_ehealth_formulario_rede_social a "+
					 "where a.id_ehealth_formulario = "+id;
		String redesSociais = "";
		ResultSet rs = lm.fastConsulta(lm.utf8_to_latin1(sql));
		try {
			while (rs.next()) {
				String rede = rs.getString("rede");
				for(TipoEhealthRedeSocialEnum tipo : TipoEhealthRedeSocialEnum.values()){
					if(rede.equals(tipo.name())){
						if(!redesSociais.isEmpty())
							redesSociais = redesSociais.concat(", ");
						redesSociais = redesSociais.concat(tipo.getLabel());
						break;
					}
				}
			}
			return redesSociais;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "erro";
	}
	
	private static void gerarArquivo(String caminho, String modelo) {
		try {
			FileChannel from = new RandomAccessFile(modelo, "r").getChannel();
			FileChannel to= new RandomAccessFile(caminho, "rw").getChannel();
			from.transferTo(0, from.size(), to);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String converteResultadoBooleano(boolean valor) {
		if(valor){
			return "Sim";
		}else{
			return "N‹o";
		}
	}
	
	
}
