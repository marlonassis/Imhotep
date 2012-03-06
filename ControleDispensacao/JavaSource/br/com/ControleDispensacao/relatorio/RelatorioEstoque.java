package br.com.ControleDispensacao.relatorio;
import java.awt.Point;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.primefaces.model.StreamedContent;

import br.com.ControleDispensacao.entidade.Estoque;
import br.com.nucleo.ConsultaGeral;

import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import java.io.InputStream;  

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;  
import javax.servlet.ServletContext;  
import javax.servlet.http.HttpServletResponse;
  
import org.primefaces.model.DefaultStreamedContent;  

@ManagedBean(name="relatorioEstoque")
@RequestScoped
public class RelatorioEstoque extends ConsultaGeral<Estoque> {
	
	private static final String RELATORIO_GERAL_ESTOQUE_PDF = "relatorioGeralEstoque.pdf";
	
    public void relatorioEstoqueGeral(){
        System.out.println("\nCriando um arquivo PDF para estoque geral.");
        // criando um objeto da classe Document
        Document documento = new Document();
        
        StreamedContent fileRelatorioGeralEstoque = null;
        
        try {
        	FacesContext context = FacesContext.getCurrentInstance();
        	HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            // Configura o response para suportar o relatório
            response.setContentType("application/pdf");
            // response.addHeader("Content-disposition", "inline; filename=\"arquivo.pdf\"");
            response.addHeader("Content-disposition", "attachment; filename=\"arquivo.pdf\"");
 
            // Preenche o relatório com os parametros e o data source
//            impressao = JasperFillManager.fillReport(caminhoRelatorio, parametroMap, HibernateUtil.recuperaConexao());
            // Exporta o relatório
//            JasperExportManager.exportReportToPdfStream(impressao, response.getOutputStream());
            // Salva o estado da aplicação no contexto do JSF
            context.getApplication().getStateManager().saveView(context);
            // Fecha o stream do response
            context.responseComplete();
        }
        catch(Exception ex) {
              System.err.println(ex.getMessage());
       }

        //fechando o documento
        documento.close();
        
//        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("exemplo_tabela.pdf");  
//        fileRelatorioGeralEstoque = new DefaultStreamedContent(stream, "application/pdf", RELATORIO_GERAL_ESTOQUE_PDF);
//        
//        return fileRelatorioGeralEstoque;
   }

}
