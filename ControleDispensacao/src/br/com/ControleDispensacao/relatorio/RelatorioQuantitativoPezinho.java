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
import br.com.ControleDispensacao.entidade.TesteDoPezinho;
import br.com.ControleDispensacao.negocio.TesteDoPezinhoRaiz;

@ManagedBean(name="relatorioQuantitativoPezinho")
@ViewScoped
public class RelatorioQuantitativoPezinho extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	private Date dataFim;
	
	public void relatorioQuantitativoPezinho() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = "/WEB-INF/classes/br/com/ControleDispensacao/relatorio/RelatorioQuantitativoPezinho.jasper";
		String nomeRelatorio = "RelatorioQuantitativoPezinho-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<TesteDoPezinho> listaEstoqueRelatorioGeral = new TesteDoPezinhoRaiz().listaTestesDoPezinhoColeta(dataIni, dataFim);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataIni", new SimpleDateFormat("dd/MM/yyyy").format(dataIni) );
		map.put("dataFim", new SimpleDateFormat("dd/MM/yyyy").format(dataFim) );
		super.geraRelatorio(caminho, nomeRelatorio, listaEstoqueRelatorioGeral, map);
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
}