package br.com.imhotep.linhaMecanica.migrador;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

  
public class MigradorProfissionais {  
  
	private static void migrarProfissionalEspecialidadeFaltantes() {
	    try {
	        BufferedReader in = new BufferedReader(new FileReader("/home/desenvolvimento/Imhotep/erroEspecialidade.txt"));
            String str;
            while (in.ready()) {
                str = in.readLine();
                String sql = "insert into tb_especialidade (cv_descricao) values ('"+str.replace("MEDICO", "").trim()+"');";
                LinhaMecanica lml = new LinhaMecanica();
				lml.setNomeBanco("db_imhotep_temp");
				lml.setIp("127.0.0.1");
				lml.executarCUD(sql);
            }
            in.close();
            
            
            in = new BufferedReader(new FileReader("/home/desenvolvimento/Imhotep/erroProfissional.txt"));
            while (in.ready()) {
            	str = in.readLine();
            	
            	LinhaMecanica lm = new LinhaMecanica();
        		lm.setNomeBanco("db_imhotep_temp");
        		lm.setIp("127.0.0.1");
    			String sql = "select id_especialidade from tb_especialidade where lower(to_ascii(cv_descricao)) = lower(to_ascii('"+str.split("::")[1].replace("MEDICO", "").trim()+"'))";
    			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
    			int idEspecialidade = 0;
    			while (rs.next()) {
    				idEspecialidade = rs.getInt("id_especialidade");
    			}
            	
                sql = "insert into tb_profissional (cv_nome, id_especialidade) values ('"+str.split("::")[0]+"', "+idEspecialidade+");";
                LinhaMecanica lml = new LinhaMecanica();
				lml.setNomeBanco("db_imhotep_temp");
				lml.setIp("127.0.0.1");
				lml.executarCUD(sql);
            }
            in.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void atualizarEpecialidadesProfissionais() {
	    try {
	        BufferedReader in = new BufferedReader(new FileReader("/home/desenvolvimento/Imhotep/profissionais.txt"));
            String str;
            while (in.ready()) {
                str = in.readLine();
                String sql = "update tb_profissional set id_especialidade = " +
                		"(select a.id_especialidade from tb_especialidade a where a.cv_descricao ='"+str.split("::")[1].replace("MEDICO", "").trim()+"') where cv_nome = ('"+str.split("::")[0].trim()+"');";
                LinhaMecanica lml = new LinhaMecanica();
				lml.setNomeBanco("db_imhotep_temp");
				lml.setIp("127.0.0.1");
				lml.executarCUD(sql);
            }
            in.close();
            
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
    public static void main(String[] args) throws Exception {  
    	
//    	migrarProfissionaisXLS();
//    	migrarProfissionalEspecialidadeFaltantes();
//    	atualizarEpecialidadesProfissionais();
        
    }


	private static void migrarProfissionaisXLS() throws FileNotFoundException{
		InputStream is = new FileInputStream("/home/desenvolvimento/Desktop/Mod_Profissional.xls");  
//        LeitorExcel leitor = null;  
//  
//        //Lista que irá guardar os dados da planilha  
//        final List<ProfissionalImportacao> listaDadosProfissionalImportacao = new LinkedList<ProfissionalImportacao>();  
//  
//        leitor = new LeitorExcel("[*,*]", 1, is, null,  
//  
//            new ColunaListener() {  
//        	ProfissionalImportacao dadosProfissionalImportacao = null;  
//      
//                  
//                @Override  
//                public boolean lendoColuna(int linha, int coluna, Map dadosColuna)  
//                        throws ListenerException {  
//                    LinhaColunaListenerVo voAtual = (LinhaColunaListenerVo) dadosColuna  
//                            .get(ColunaListener.CHAVE_VO_COLUNA);  
//                    if (linha > 5) { //Pula primeira linha pois é a linha que possui o título  
//                        switch (coluna) {  
//                            case 1:// Coluna data  
//                                if(dadosProfissionalImportacao == null){  
//                                    dadosProfissionalImportacao = new ProfissionalImportacao();  
//                                }  
//                                String nome = voAtual.getCelulaAtual().getStringCellValue();  
////                                System.out.println("Nome --> " + nome);  
//                                dadosProfissionalImportacao.setNome(nome);  
//                                break;  
//                            case 5:// Coluna meta  
//                                String prof = voAtual.getCelulaAtual().getStringCellValue();
//                                prof = prof.split("-")[1].trim();
////                                System.out.println("Profissao -->" + prof);  
//                                dadosProfissionalImportacao.setProfissao(prof);  
//                                //"acabei de ler a linha" Adiciono o vo na lista  
//                                listaDadosProfissionalImportacao.add(dadosProfissionalImportacao);  
//                                dadosProfissionalImportacao = null;  
//                        }  
//                    }  
//      
//                    return true;  
//      
//                }  
//            }  
//        );  
//          
//        leitor.processarLeituraPlanilha();  
//          
////        //Agora faço o que quiser com os dados da planilha  
//        for(ProfissionalImportacao vo : listaDadosProfissionalImportacao) {  
//        	LinhaMecanica lm = new LinhaMecanica();
//    		lm.setNomeBanco("db_imhotep_temp");
//    		lm.setIp("127.0.0.1");
//			String str = "select id_especialidade from tb_especialidade where lower(to_ascii(cv_descricao)) = to_ascii('"+vo.getProfissao().toLowerCase().replace("medico", "").trim()+"')";
//			ResultSet rs = lm.consultar(lm.utf8_to_latin1(str));
//			int id = 0;
//			while (rs.next()) {
//				id = rs.getInt("id_especialidade");
//			}
//			if(id == 0){
//				File arquivo = new File("/home/desenvolvimento/Imhotep/erroEspecialidade.txt");
//				FileOutputStream arquivoOutput = new FileOutputStream(arquivo, true); 
//				PrintStream gravador = new PrintStream(arquivoOutput);
//				gravador.println(vo.getProfissao());
//				gravador.close();
//			}
//            System.out.println( "Nome: " + vo.getNome() + " - Profissao: " + vo.getProfissao() + " - id: "+id);  
//        }
//          
//        for(ProfissionalImportacao vo : listaDadosProfissionalImportacao) {  
//        	LinhaMecanica lm = new LinhaMecanica();
//    		lm.setNomeBanco("db_imhotep_temp");
//    		lm.setIp("127.0.0.1");
//			String str = "select id_profissional from tb_profissional where lower(to_ascii(cv_nome)) = to_ascii('"+vo.getNome().toLowerCase().trim()+"')";
//			ResultSet rs = lm.consultar(lm.utf8_to_latin1(str));
//			int id = 0;
//			while (rs.next()) {
//				id = rs.getInt("id_profissional");
//			}
//			if(id == 0){
//				File arquivo = new File("/home/desenvolvimento/Imhotep/erroProfissional.txt");
//				FileOutputStream arquivoOutput = new FileOutputStream(arquivo, true); 
//				PrintStream gravador = new PrintStream(arquivoOutput);
//				gravador.println(vo.getNome() +"::"+vo.getProfissao());
//				gravador.close();
//			}
//            System.out.println( "Nome: " + vo.getNome() + " - Profissao: " + vo.getProfissao() + " - id: "+id);  
//        }
//        
//        for(ProfissionalImportacao vo : listaDadosProfissionalImportacao) {  
//			File arquivo = new File("/home/desenvolvimento/Imhotep/profissionais.txt");
//			FileOutputStream arquivoOutput = new FileOutputStream(arquivo, true); 
//			PrintStream gravador = new PrintStream(arquivoOutput);
//			gravador.println(vo.getNome() +"::"+vo.getProfissao());
//			gravador.close();
//        }
	}  
}  