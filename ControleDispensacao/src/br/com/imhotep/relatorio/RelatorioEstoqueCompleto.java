package br.com.imhotep.relatorio;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.EstoqueCompletoConsultaRaiz;
import br.com.imhotep.entidade.Estoque;

@ManagedBean
@ViewScoped
public class RelatorioEstoqueCompleto extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	public void relatorioGeralEstoque() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioEstoqueCompleto.jasper";
		String nomeRelatorio = "RelatorioDoEstoque-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<Estoque> listaEstoqueRelatorioGeral = new EstoqueCompletoConsultaRaiz().consultar();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("data", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
		super.geraRelatorio(caminho, nomeRelatorio, listaEstoqueRelatorioGeral, map);
	}
	
}
