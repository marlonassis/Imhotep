package br.com.ControleDispensacao.relatorio;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;

@ManagedBean(name="relatorioEstoque")
@ViewScoped
public class RelatorioEstoque extends PadraoRelatorio{
	
	public void relatorioGeralEstoque() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = "/WEB-INF/classes/br/com/ControleDispensacao/relatorio/EstoqueRelatorio.jasper";
		super.geraRelatorio(caminho, "RelatorioDoEstoque-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf");
	}
	
}
