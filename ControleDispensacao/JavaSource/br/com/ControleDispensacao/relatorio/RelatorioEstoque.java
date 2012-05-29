package br.com.ControleDispensacao.relatorio;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.ControleDispensacao.entidade.Estoque;
import br.com.ControleDispensacao.negocio.EstoqueHome;

@ManagedBean(name="relatorioEstoque")
@ViewScoped
public class RelatorioEstoque extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	public void relatorioGeralEstoque() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = "/WEB-INF/classes/br/com/ControleDispensacao/relatorio/EstoqueRelatorio.jasper";
		String nomeRelatorio = "RelatorioDoEstoque-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<Estoque> listaEstoqueRelatorioGeral = new EstoqueHome().listaEstoqueRelatorioGeral();
		super.geraRelatorio(caminho, nomeRelatorio, listaEstoqueRelatorioGeral, new HashMap<String, Object>());
	}
	
}
