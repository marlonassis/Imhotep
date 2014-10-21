package ehealth;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import br.com.imhotep.enums.TipoEhealthNaturezaEnum;
import br.com.imhotep.enums.TipoEstadoEnum;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
      
    public class Ehealth{  
         
        static List<String> estados = new ArrayList<String>(Arrays.asList("Acre","Alagoas","Amazonas","Amapa","Bahia","Ceara","Distrito Federal","Espirito Santo",
"Goias","Maranhao","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Para","Paraiba","Parana","Pernambuco",
"Piaui","Rio de Janeiro","Rio Grande do Norte","Rondonia","Rio Grande do Sul",
"Roraima","Santa Catarina","Sergipe","Sao Paulo","Tocantins"));

        static List<String> estadosSigla = new ArrayList<String>(Arrays.asList(
        		"AC","AL","AM","AP","BA","CE","DF","ES",
        		"GO","MA","MT","MS","MG","PA","PB","PR","PE",
        		"PI","RJ","RN","RO","RS",
        		"RR","SC","SE","SP","TO"));
        
        private static String buscarConteudoTag(String string, String tag){
            int posFont = string.indexOf(tag);
            String href = string.substring(posFont);
            char[] hrefq = href.toCharArray();
            String link = "";
            boolean carregarConteudo = false;
            for(char c : hrefq){
            	boolean maiorQue = c == '>';
            	boolean menorQue = c == '<';
            	if(menorQue)
            		break;
				if(carregarConteudo)
            		link = link.concat(String.valueOf(c));
				if(maiorQue){
					carregarConteudo = true;
				}
            }
			return link;
       }        
        
        private static String buscaConteudoHref(String string){
             int poshref = string.indexOf("href");
             String href = string.substring(poshref);
             char[] hrefq = href.toCharArray();
             String link = "";
             int status = 0;
             for(char c : hrefq){
             	boolean aspas = c == 44 || c == 39 || c == 34;
 				if(aspas){
             		status++;
             		if(status == 2)
             			break;
             	}
             	
             	if(!aspas & status == 1){
             		link = link.concat(String.valueOf(c));
             	}
             }
             
 			return link;
        }
        
        private static String buscaConteudoAncora(String string){
            int poshref = string.indexOf(">");
            String nomeMunicipioSujo = string.substring(poshref+1);
            char[] hrefq = nomeMunicipioSujo.toCharArray();
            String nomeMunicipio = "";
            for(char c : hrefq){
            	boolean menorQue = c == '<';
            	if(!menorQue){
            		nomeMunicipio = nomeMunicipio.concat(String.valueOf(c));
            	}else{
           			break;
            	}
            }
            
			return nomeMunicipio;
       }
        
        public static String existeEstadoLinha(String linha){
        	for(String estado : estados){
        		if(linha.toLowerCase().contains(estado.toLowerCase())){
        			estados.remove(estado);
        			return estado;
        		}
        	}
        	return "";
        }
        
        /** 
         * @param args 
         */  
		public static void main(String... args) {  
			try{
				String linkDataSus = "http://cnes.datasus.gov.br/";
				
//				carregarEstados(linkDataSus);
//				carregarCidades(linkDataSus);
//				carregarUnidadesSaude(linkDataSus);
//				migrarEstado();
//				migrarNatureza();
				atualizarTipoUnidade(linkDataSus);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		private static void atualizarTipoUnidade(String linkDataSus) {
			LinhaMecanica lm = new LinhaMecanica();
    		lm.setNomeBanco("db_imhotep");
    		lm.setIp("127.0.0.1");
			String sql = "select * from tb_ehealth_estabelecimento where cv_tipo_unidade is null "+ 
						 "order by id_ehealth_estabelecimento ";
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
			try {
				while (rs.next()) {
					int idEhealthEstabelecimento = rs.getInt("id_ehealth_estabelecimento");
					String link = rs.getString("cv_link");
					String tipoUnidade = buscaTipoUnidade(linkDataSus.concat(link));
					if(tipoUnidade != null && !tipoUnidade.isEmpty()){
						sql = "update tb_ehealth_estabelecimento set cv_tipo_unidade = '"+tipoUnidade+"' where id_ehealth_estabelecimento = "+idEhealthEstabelecimento;
						System.out.println(sql);
						if(!lm.executarCUD(sql)){
							gerarErro("/Users/marlonassis/Programacao/Banco/erroTipoUnidade.txt", String.valueOf(idEhealthEstabelecimento)); 
						}
					}else{
						gerarErro("/Users/marlonassis/Programacao/Banco/erroTipoUnidade.txt", String.valueOf(idEhealthEstabelecimento)); 
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
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

		private static String buscaTipoUnidade(String linkDataSusEstabelecimento) {
			InputStream siteEstado;
			try {
				siteEstado = new URL(linkDataSusEstabelecimento).openStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(siteEstado, "ISO-8859-1"));
				String line = null;
				boolean achouTipoUnidade=false;
				int cont = 0;
				String tipoUnidade = "";
				while ((line = reader.readLine()) != null) {
					if(line.contains("Tipo Unidade:"))
						achouTipoUnidade = true;
					if(achouTipoUnidade)
						cont++;
					if(cont == 8){
						tipoUnidade = Ehealth.buscarConteudoTag(line, "font");
						break;
					}
				}
				return tipoUnidade;
			} catch (Exception e) {
				System.out.println("erro");;
			}
			return null;
		}

		private static void migrarEstado(){
			LinhaMecanica lm = new LinhaMecanica();
    		lm.setNomeBanco("db_imhotep");
    		lm.setIp("127.0.0.1");
			String sql = "select a.id_ehealth_municipio, b.cv_nome as estado from tb_ehealth_municipio a " + 
						"inner join tb_ehealth_estado b on b.id_ehealth_estado = a.id_ehealth_estado order by a.id_ehealth_municipio";
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
			try {
				while (rs.next()) {
					int idEhealthMunicipio = rs.getInt("id_ehealth_municipio");
					String estado = rs.getString("estado");
					sql = "update tb_ehealth_municipio set tp_estado = "+converteNomeEstadoSigla(estado)+" where id_ehealth_municipio = "+idEhealthMunicipio;
					System.out.println(sql);
					LinhaMecanica lml = new LinhaMecanica();
					lml.setNomeBanco("db_imhotep");
					lml.setIp("127.0.0.1");
					lml.executarCUD(sql);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		private static String converteNomeEstadoSigla(String nome){
			for(TipoEstadoEnum e : TipoEstadoEnum.values()){
				if(e.getLabel().trim().equalsIgnoreCase(nome.trim())){
					return "'".concat(e.name()).concat("'");
				}
			}
			return null;
		}
		
		private static void migrarNatureza(){
			LinhaMecanica lm = new LinhaMecanica();
    		lm.setNomeBanco("db_imhotep");
    		lm.setIp("127.0.0.1");
			String sql = "select id_ehealth_estabelecimento, cv_natureza from tb_ehealth_estabelecimento order by id_ehealth_estabelecimento";
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
			try {
				while (rs.next()) {
					int idEhealthEstabelecimento = rs.getInt("id_ehealth_estabelecimento");
					String natureza = rs.getString("cv_natureza");
					sql = "update tb_ehealth_estabelecimento set tp_tipo_natureza = "+converterNatureza(natureza)+" where id_ehealth_estabelecimento = "+idEhealthEstabelecimento;
					System.out.println(sql);
					LinhaMecanica lml = new LinhaMecanica();
					lml.setNomeBanco("db_imhotep");
					lml.setIp("127.0.0.1");
					lml.executarCUD(sql);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
    private static String converterNatureza(String nome){
    	for(TipoEhealthNaturezaEnum n : TipoEhealthNaturezaEnum.values()){
    		if(nome.equals(n.getLabel())){
    			return "'".concat(n.name()).concat("'");
    		}
    	}
    	return null;
    }
    
		private static void carregarUnidadesSaude(String linkDataSus) throws SQLException, FileNotFoundException  {
			Calendar ini = Calendar.getInstance();
	    	LinhaMecanica lm = new LinhaMecanica();
			List<Municipio> municipios = consultaTodosMunicipios(lm);
			StringBuilder erro = new StringBuilder();
			StringBuilder sb=null;
			StringBuilder todos = new StringBuilder();
			PrintStream gravador;
			int i = 0;
			for(Municipio municipio : municipios){
				InputStream siteEstado;
				try {
					siteEstado = new URL(linkDataSus + municipio.getLink()).openStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(siteEstado, "ISO-8859-1"));
					String line = null;
					sb = new StringBuilder();
					while ((line = reader.readLine()) != null) {
						if(line.contains("VCo_Unidade=")){
							String link = Ehealth.buscaConteudoHref(line);
							String momeEstabelecimento = Ehealth.buscaConteudoAncora(line.substring(line.indexOf("<a")));
							String natureza = complementarUnidadesSaude(linkDataSus+link, erro);
							String sql = "insert into tb_estabelecimento (id_municipio, cv_nome, cv_natureza, cv_link) " +
									"values("+municipio.getIdMunicipio()+",'"+momeEstabelecimento+"','"+natureza+"', '"+link+"');";
							sb.append(sql).append("\n");
							i++;
							System.out.println(i+" - municipio: "+municipio.getIdMunicipio());
						}
					}
					
					File arquivo = new File("/home/desenvolvimento/ehealth/"+municipio.getEstado().getIdEstados());
					arquivo.mkdirs();
					arquivo = new File("/home/desenvolvimento/ehealth/"+municipio.getEstado().getIdEstados()+"/"+municipio.getNome()+".txt");
					FileOutputStream arquivoOutput = new FileOutputStream(arquivo, true); 
					gravador = new PrintStream(arquivoOutput);
					gravador.println(sb);
					gravador.close();
					
					todos.append(sb.toString());
				} catch (Exception e) {
					erro.append("Município:").append(municipio.getLink()).append(" - id:").append(municipio.getIdMunicipio()).append("\n");
				}
			}
			
			
			File arquivo = new File("/home/desenvolvimento/ehealth/erroURL.txt");
			FileOutputStream arquivoOutput = new FileOutputStream(arquivo, true); 
			gravador = new PrintStream(arquivoOutput);
			gravador.println(erro);
			gravador.close();
			
			
			if(todos.length() > 0){
			    Scanner scan = new Scanner(todos.toString());
			    while(scan.hasNextLine()) {  
			    	String sql = scan.nextLine();
			    	lm.setNomeBanco("db_ehealth");
			       if(!lm.executarCUD(sql)){
						arquivo = new File("/home/desenvolvimento/ehealth/erroSQL.txt");
						arquivoOutput = new FileOutputStream(arquivo, true); 
						gravador = new PrintStream(arquivoOutput);
						gravador.println(sql);
						gravador.close();
					}
			    }
			}
			System.out.println("ini:"+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(ini.getTime()));
			System.out.println("fim:"+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		}

		private static List<Municipio> consultaTodosMunicipios(LinhaMecanica lm) throws SQLException {
			lm.setNomeBanco("db_ehealth");
			ResultSet rs = lm.consultar("select id_municipio, cv_link, cv_nome, id_estado from tb_municipio order by id_municipio");
			List<Municipio> municipios = new ArrayList<Municipio>();
			while (rs.next()) {
				municipios.add(new Municipio(rs.getInt("id_municipio"), rs.getString("cv_nome"), rs.getString("cv_link"), rs.getInt("id_estado")));
			}
			return municipios;
		}
		
		
		private static String complementarUnidadesSaude(String linkEstabelecimento, StringBuilder erro) {
			InputStream siteEstabelecimento;
			try {
				siteEstabelecimento = new URL(linkEstabelecimento).openStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(siteEstabelecimento, "ISO-8859-1"));
				String line = null;
				int i = 0;
				boolean achouNatureza = false;
				while ((line = reader.readLine()) != null) {
					if(achouNatureza || line.contains("Natureza da Organização")){
						achouNatureza = true;
						if(achouNatureza){
							i++;
							if(i==5){
								String natureza = buscarConteudoTag(line, "font");
								return natureza;
							}
						}
					}
				}
			}catch(Exception e){
				erro.append("Estabelecimento:").append(linkEstabelecimento).append("\n");
			}
			return "";
		}
		
		private static void carregarCidades(String linkDataSus) throws SQLException, MalformedURLException, IOException {
	    	LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco("db_ehealth");
			ResultSet rs = lm.consultar("select id_estados, cv_nome, cv_link from tb_estados order by cv_nome");
			List<Estado> estados = new ArrayList<Estado>();
			while (rs.next()) {
				estados.add(new Estado(rs.getInt("id_estados"), rs.getString("cv_nome"), rs.getString("cv_link")));
			}
			
			for(Estado estado : estados){
				InputStream siteEstado = new URL(linkDataSus + estado.getLink()).openStream();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(siteEstado, "ISO-8859-1"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				int i = 0;
				while ((line = reader.readLine()) != null) {
					if(line.replaceAll(" ", "").toLowerCase().contains("nomeestado="+estado.getNome().toLowerCase().replaceAll(" ", ""))){
						String link = Ehealth.buscaConteudoHref(line);
						String momeMunicipio = Ehealth.buscaConteudoAncora(line);
						String sql = "insert into tb_municipio (cv_nome, cv_link, id_estado) values('"+momeMunicipio.replace("'","")+"','"+link+"',"+estado.getIdEstados()+");";
						sb.append(sql).append("\n");
						lm.setNomeBanco("db_ehealth");
						if(!lm.executarCUD(sql)){
							System.out.println(sql);
							System.exit(1);
						}
						i++;
					}
				}
//				if(i==0){
//					System.out.println(estado.getNome());
//					System.exit(1);
//				}
//				break;
//				System.out.println(sb.toString());
//				System.out.println(i);
			}
			
		}
		
		private static void carregarEstados(String linkDataSus) throws IOException,
				MalformedURLException, UnsupportedEncodingException {
			InputStream siteEstado = new URL(linkDataSus + "Lista_Tot_Es_Estado.asp").openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(siteEstado, "ISO-8859-1"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				if(line.indexOf("href") > 0){
					String estado = Ehealth.existeEstadoLinha(line);
					if(!estado.equals("")){
						String link = Ehealth.buscaConteudoHref(line);
						sb.append(link).append("\n");
						String sql = "insert into tb_estados (cv_nome, cv_link) values('"+estado+"','"+link+"');";
						LinhaMecanica lm = new LinhaMecanica();
						lm.setNomeBanco("db_ehealth");
						lm.executarCUD(sql);
						System.out.println(sql);
					}
				}
			}
		}
    }  