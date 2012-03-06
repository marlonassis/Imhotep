package br.com.ControleDispensacao.relatorio;
import java.awt.Point;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class PrimeiroPdf {
	    
	    public static void main(String[] args) {
	        
	        System.out.println("\nCriando um arquivo PDF com tabela.");
	        // criando um objeto da classe Document
	        Document documento = new Document();
	        try {
	            //direcionando para um arquivo PDF
	            PdfWriter.getInstance(documento, new FileOutputStream("exemplo_tabela.pdf"));

	           //abrindo o documento
	            documento.open();
	  
	           //criando a tabela a inserindo-a no documento
	            
	            Table tabela = new Table(3,3);    
	            // 3 linhas e 3 colunas

	            tabela.setAutoFillEmptyCells(true);
	            tabela.addCell("NOME", new Point(0,0));
	            tabela.addCell("ENDERECO", new Point(0,1));
	            tabela.addCell("TELEFONE", new Point(0,2));
	            tabela.addCell("Jose dos Santos", new Point(1,0));
	            tabela.addCell("Rua Farancisca Rocha", new Point(1,1));
	            tabela.addCell("8859-5555", new Point(1,2));
	            
	            documento.add(tabela);
	            System.out.println("\nArquivo criado com sucesso!");                    
	        }
	        catch(Exception ex) {
	              System.err.println(ex.getMessage());
	       }

	        //fechando o documento
	        documento.close();
	   }

}
