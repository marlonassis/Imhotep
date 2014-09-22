package br.com.imhotep.linhaMecanica.migrador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class MigradorUsuarios {

	public static void main(String[] args) {
		
		String caminho = "/Users/marlonassis/Desktop/UsuáriosImportados.xls";
		String modelo = "/Users/marlonassis/Desktop/Usuários.xls";
		gerarArquivo(caminho, modelo);
		
        try {
        	
        	String sql = "select a.cv_nome, a.in_matricula, b.cv_login, a.dt_data_nascimento from tb_profissional a "+ 
						 "inner join tb_usuario b on a.id_usuario = b.id_usuario "+
						 "where b.cv_login != '' and b.cv_login is not null and a.tp_status = 'A' and a.dt_data_nascimento is not null "
						 + "order by to_ascii(lower(a.cv_nome))"; 
        	
        	//Cria um Arquivo Excel
            Workbook wb = new HSSFWorkbook(new FileInputStream(caminho));  
            
            //Cria uma planilha Excel
            Sheet sheet = wb.getSheet("Usuários");   
            int i = 1;
            LinhaMecanica lm = new LinhaMecanica();
    		lm.setNomeBanco("db_imhotep");
    		lm.setIp("200.133.41.8");
            ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
			try {
				while (rs.next()) {
					Row dados = sheet.createRow((short)i);
					//Nas células a seguir vc substitui pelas getText dos seus JTextFields e JLabels
					String nome = rs.getString("cv_nome");
					String matricula = rs.getString("in_matricula");
					String login = rs.getString("cv_login");
					Date dataNascimento = rs.getDate("dt_data_nascimento");
					
					dados.createCell(0).setCellValue(nome);
					dados.createCell(1).setCellValue(matricula);
					dados.createCell(2).setCellValue(login);
					dados.createCell(3).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(dataNascimento));
					
					System.out.println(nome+" - "+matricula+" - "+login+" - "+dataNascimento);
					
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
