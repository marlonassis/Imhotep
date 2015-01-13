package ehealth;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.enums.TipoEhealthNaturezaEnum;
import br.com.imhotep.enums.TipoEhealthRedeSocialEnum;
import br.com.imhotep.enums.TipoEhealthTipoTecnologiaEnum;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class EstatisticaEhealth {

	private static final String ip = Constantes.IP_LOCAL;
	static List<String> estadosSigla = new ArrayList<String>(Arrays.asList(
    		"AC","AL","AM","AP","BA","CE","DF","ES",
    		"GO","MA","MT","MS","MG","PA","PB","PR","PE",
    		"PI","RJ","RN","RO","RS",
    		"RR","SC","SE","SP","TO"));
	
	private static String sqlTabelaRelatorioResumido(){
		String sql = "select a.tp_regiao regiao, a.tp_estado estado, count(a.cv_nome) qtdMunicipios, "+ 

		"(select count(DISTINCT b.cv_nome) from tb_ehealth_municipio as b "+
		"inner join tb_ehealth_estabelecimento c on c.id_ehealth_municipio = b.id_ehealth_municipio and c.id_profissional_pesquisador is not null "+
		"inner join tb_ehealth_formulario d on d.id_ehealth_estabelecimento = c.id_ehealth_estabelecimento "+
		"where b.tp_estado = a.tp_estado) qtdMuniPesq, "+

		"(select count(e.cv_nome) from tb_ehealth_estabelecimento as e "+
		"inner join tb_ehealth_municipio g on g.id_ehealth_municipio = e.id_ehealth_municipio and g.tp_estado = a.tp_estado "+
		"where e.cv_tipo_unidade = 'HOSPITAL ESPECIALIZADO' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL GERAL' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL/DIA - ISOLADO' "+
		") qtdHospitais, "+
		
		"(select count(e.cv_nome) from tb_ehealth_estabelecimento as e "+
		"inner join tb_ehealth_formulario f on f.id_ehealth_estabelecimento = e.id_ehealth_estabelecimento "+
		"inner join tb_ehealth_municipio g on g.id_ehealth_municipio = e.id_ehealth_municipio and g.tp_estado = a.tp_estado "+
		"where e.cv_tipo_unidade = 'HOSPITAL ESPECIALIZADO' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL GERAL' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL/DIA - ISOLADO' "+
		") qtdHospitaisPesquisados, "+

		"(select count(e.cv_nome) from tb_ehealth_estabelecimento as e "+
		"inner join tb_ehealth_formulario f on f.id_ehealth_estabelecimento = e.id_ehealth_estabelecimento "+
		"inner join tb_ehealth_municipio g on g.id_ehealth_municipio = e.id_ehealth_municipio and g.tp_estado = a.tp_estado and bl_capital is true "+
		"where e.cv_tipo_unidade = 'HOSPITAL ESPECIALIZADO' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL GERAL' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL/DIA - ISOLADO' "+
		") qtdHospitaisPesquisadosCapital, "+

		"(select count(e.cv_nome) from tb_ehealth_estabelecimento as e "+
		"inner join tb_ehealth_formulario f on f.id_ehealth_estabelecimento = e.id_ehealth_estabelecimento "+
		"inner join tb_ehealth_municipio g on g.id_ehealth_municipio = e.id_ehealth_municipio and g.tp_estado = a.tp_estado and bl_capital is false "+
		"where e.cv_tipo_unidade = 'HOSPITAL ESPECIALIZADO' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL GERAL' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL/DIA - ISOLADO' "+
		") qtdHospitaisPesquisadosInterior, "+

		"(select count(e.cv_nome) from tb_ehealth_estabelecimento as e "+
		"inner join tb_ehealth_formulario f on f.id_ehealth_estabelecimento = e.id_ehealth_estabelecimento and f.bl_possui_site_proprio is true "+
		"inner join tb_ehealth_municipio g on g.id_ehealth_municipio = e.id_ehealth_municipio and g.tp_estado = a.tp_estado and bl_capital is true "+
		"where (e.cv_tipo_unidade = 'HOSPITAL ESPECIALIZADO' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL GERAL' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL/DIA - ISOLADO') "+
		") qtdHospitaisComSiteCapital, "+

		"(select count(e.cv_nome) from tb_ehealth_estabelecimento as e "+
		"inner join tb_ehealth_formulario f on f.id_ehealth_estabelecimento = e.id_ehealth_estabelecimento and f.bl_possui_site_proprio is false "+
		"inner join tb_ehealth_municipio g on g.id_ehealth_municipio = e.id_ehealth_municipio and g.tp_estado = a.tp_estado and bl_capital is true "+
		"where (e.cv_tipo_unidade = 'HOSPITAL ESPECIALIZADO' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL GERAL' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL/DIA - ISOLADO') "+
		") qtdHospitaisSemSiteCapital, "+

		"(select count(e.cv_nome) from tb_ehealth_estabelecimento as e "+
		"inner join tb_ehealth_formulario f on f.id_ehealth_estabelecimento = e.id_ehealth_estabelecimento and f.bl_possui_site_proprio is true "+
		"inner join tb_ehealth_municipio g on g.id_ehealth_municipio = e.id_ehealth_municipio and g.tp_estado = a.tp_estado and bl_capital is false "+
		"where (e.cv_tipo_unidade = 'HOSPITAL ESPECIALIZADO' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL GERAL' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL/DIA - ISOLADO') "+
		") qtdHospitaisComSiteInterior, "+

		"(select count(e.cv_nome) from tb_ehealth_estabelecimento as e "+
		"inner join tb_ehealth_formulario f on f.id_ehealth_estabelecimento = e.id_ehealth_estabelecimento and f.bl_possui_site_proprio is false "+
		"inner join tb_ehealth_municipio g on g.id_ehealth_municipio = e.id_ehealth_municipio and g.tp_estado = a.tp_estado and bl_capital is false "+
		"where (e.cv_tipo_unidade = 'HOSPITAL ESPECIALIZADO' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL GERAL' or  "+
		"e.cv_tipo_unidade = 'HOSPITAL/DIA - ISOLADO') "+
		") qtdHospitaisSemSiteInterior "+

		"from tb_ehealth_municipio a "+
		"group by a.tp_regiao, a.tp_estado "+
		"order by a.tp_regiao, a.tp_estado ";
		
		return sql;
	}
	
	public static void main(String[] args) {
		System.out.println("In�cio: "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
		System.out.println("Gerando relat�rio geral");
//		gerarRelatorioGenerico();
		System.out.println("Gerando relat�rios por estado");
		gerarRelatorioEstabelecimentoEstado();
		System.out.println("Fim: "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
	}

	private static void gerarRelatorioGenerico() {
		String caminho = "/Users/marlonassis/Documents/HU/Pesquisa/quantidadesTotais.xls";
		String modelo = "/Users/marlonassis/Documents/HU/Pesquisa/Modelo/modeloQuantidadeTotais.xls";
		gerarArquivo(caminho, modelo);
		
        try {
        	
        	String sql = EstatisticaEhealth.sqlTabelaRelatorioResumido();
        	
        	//Cria um Arquivo Excel
            Workbook wb = new HSSFWorkbook(new FileInputStream(caminho));  
            
            //Cria uma planilha Excel
            Sheet sheet = wb.getSheet("Totais");   
            int i = 7;
            LinhaMecanica lm = new LinhaMecanica();
    		lm.setNomeBanco("db_imhotep");
    		lm.setIp(ip);
            ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
			try {
				while (rs.next()) {
					Row dados = sheet.createRow((short)i);
					//Nas c�lulas a seguir vc substitui pelas getText dos seus JTextFields e JLabels
					dados.createCell(0).setCellValue(rs.getString("regiao"));
					dados.createCell(1).setCellValue(rs.getString("estado"));
					dados.createCell(2).setCellValue(rs.getInt("qtdHospitais"));
					dados.createCell(3).setCellValue(rs.getInt("qtdHospitaisPesquisados"));
					dados.createCell(4).setCellValue(rs.getInt("qtdHospitaisPesquisadosCapital"));
					dados.createCell(5).setCellValue(rs.getInt("qtdHospitaisPesquisadosInterior"));
					dados.createCell(6).setCellValue(rs.getInt("qtdHospitaisComSiteCapital"));
					dados.createCell(7).setCellValue(rs.getInt("qtdHospitaisSemSiteCapital"));
					dados.createCell(8).setCellValue(rs.getInt("qtdHospitaisComSiteInterior"));
					dados.createCell(9).setCellValue(rs.getInt("qtdHospitaisSemSiteInterior"));
					i++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
            FileOutputStream fileOut = new FileOutputStream(caminho);
            wb.write(fileOut);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();

        }
	}

	private static void gerarRelatorioEstabelecimentoEstado() {
		LinhaMecanica lm = new LinhaMecanica("db_imhotep", "200.133.41.8");
		try {
			lm.criarConexao();
			for(String uf : estadosSigla){
				System.out.println();
				System.out.println(uf);
				String caminho = "/Users/marlonassis/Desktop/e-health/Brasil/"+uf+".xls";
				String modelo = "/Users/marlonassis/Desktop/e-health/Modelo/modelo.xls";
				new File(caminho).delete();
				gerarArquivo(caminho, modelo);
				
            	String sql = "select a.id_ehealth_estabelecimento as id, b.id_ehealth_formulario idForm, upper(a.cv_nome) as estab, c.cv_nome as munici, c.bl_capital capital, a.tp_tipo_natureza as nature, "+ 
		            			"b.bl_possui_site_proprio as siteProprio,b.tp_tipo_presenca_web presenca,  "+
		            			"b.bl_informacao_institucional_hospital as infoInst, b.bl_informacao_servico_prestado as servPres,  "+
		            			"b.bl_informacao_prevencao_cuidados_saude as prevSau, b.bl_procedimentos_emergencia_medica as procEmer,  "+
		            			"b.bl_endereco_eletronico_recepcao as endElet,  b.bl_marcacao_consulta as marcConsu, b.bl_tabela_custo_servico as tabSer,  "+
		            			"b.bl_localizacao_meios_acesso as locali, b.bl_corpo_clinico as infoCC, b.bl_rastreio_medico_online as rastreamento, b.bl_consulta_online as consultaOnline, "+ 
		            			"b.bl_disponibilizacao_formulario_download as formDown, bl_disponibilizacao_formulario_online as formOnLine, b.bl_acessibilidade as acessibilidade, "+ 
		            			"b.cv_observacao as obs from ehealth.tb_ehealth_estabelecimento a  "+
		            			"inner join ehealth.tb_ehealth_formulario b on b.id_ehealth_estabelecimento = a.id_ehealth_estabelecimento "+ 
		            			"inner join ehealth.tb_ehealth_municipio c on c.id_ehealth_municipio = a.id_ehealth_municipio "+ 
		            			"inner join ehealth.tb_ehealth_estado d on d.id_ehealth_estado = c.id_ehealth_estado "+ 
		            			"inner join ehealth.tb_ehealth_pais e on e.id_ehealth_pais = d.id_ehealth_pais "+ 
		            			"where d.cv_sigla_estado = '"+uf+"' and e.id_ehealth_pais = 1 and "+
		            			" (a.cv_tipo_unidade = 'HOSPITAL ESPECIALIZADO' or "+  
		            			"a.cv_tipo_unidade = 'HOSPITAL GERAL' or   "+
		            			"a.cv_tipo_unidade = 'HOSPITAL/DIA - ISOLADO') "+ 
		            			"order by c.cv_nome, a.cv_nome";
            			
            			
            	//Cria um Arquivo Excel
                Workbook wb = new HSSFWorkbook(new FileInputStream(caminho));  
                
                //Cria uma planilha Excel
                Sheet sheet = wb.getSheet("Hospitais");   
                int i = 7;
                ResultSet rs = lm.fastConsulta(lm.utf8_to_latin1(sql));
				while (rs.next()) {
					//adicionando o nome do pesquisador
//					Row dados = sheet.getRow((short)3);
//						dados.createCell(2).setCellValue(rs.getString("profissisonal"));
					//////
					Row dados = sheet.createRow((short)i);
					//Nas c�lulas a seguir vc substitui pelas getText dos seus JTextFields e JLabels
//						int id = rs.getInt("id");
//						dados.createCell(0).setCellValue(id);
					dados.createCell(0).setCellValue(rs.getString("estab"));
					dados.createCell(1).setCellValue("Brasil");
					dados.createCell(2).setCellValue(rs.getString("munici") + "/" + uf);
					dados.createCell(3).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("capital")));
					String natureza = rs.getString("nature");
					dados.createCell(4).setCellValue(conversaoNatureza(natureza));
					dados.createCell(5).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("siteProprio")));
					String presenca = rs.getString("presenca");
					
					int idForm = rs.getInt("idForm");
					dados.createCell(6).setCellValue(presenca == null ? "N/A" : presenca.equals("H") ? "Hospedado" : presenca.equals("P") ? "Pr�prio" : "");
					dados.createCell(7).setCellValue(EstatisticaEhealth.tecnologias(idForm, lm));
					
					dados.createCell(8).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("infoInst")));
					dados.createCell(9).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("servPres")));
					dados.createCell(10).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("prevSau")));
					dados.createCell(11).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("procEmer")));
					dados.createCell(12).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("endElet")));
					dados.createCell(13).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("marcConsu")));
					dados.createCell(14).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("tabSer")));
					dados.createCell(15).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("locali")));
					dados.createCell(16).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("infoCC")));
					dados.createCell(17).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("rastreamento")));
					dados.createCell(18).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("consultaOnline")));
					dados.createCell(19).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("formDown")));
					dados.createCell(20).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("formOnLine")));
					dados.createCell(21).setCellValue(EstatisticaEhealth.converteResultadoBooleano(rs.getBoolean("acessibilidade")));
					dados.createCell(22).setCellValue(EstatisticaEhealth.redesSociais(idForm, lm));
//						dados.createCell(23).setCellValue(rs.getString("site"));
					dados.createCell(24).setCellValue(rs.getString("obs"));
					i++;
					System.out.print(i+"-");
				}
                
                FileOutputStream fileOut = new FileOutputStream(caminho);
                wb.write(fileOut);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			lm.fecharConexoes();
		}
	}
	
	private static String converteResultadoBooleano(boolean valor) {
		if(valor){
			return "Sim";
		}else{
			return "N�o";
		}
	}
	
	
	
	private static String conversaoNatureza(String natureza){
		for(TipoEhealthNaturezaEnum tipo : TipoEhealthNaturezaEnum.values()){
			if(tipo.name().equals(natureza)){
				tipo.getLabel();
			}
		}
		return "P�blico";
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
}
