package br.com.Imhotep.relatorio;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.raiz.EstoqueRaiz;

@ManagedBean(name="relatorioEstoque")
@ViewScoped
public class RelatorioEstoque extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	public void relatorioGeralEstoque() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = "/WEB-INF/classes/br/com/Imhotep/relatorio/EstoqueRelatorio.jasper";
		String nomeRelatorio = "RelatorioDoEstoque-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<Estoque> listaEstoqueRelatorioGeral = new EstoqueRaiz().listaEstoqueRelatorioGeral();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("data", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
		super.geraRelatorio(caminho, nomeRelatorio, listaEstoqueRelatorioGeral, map);
	}
	
}
