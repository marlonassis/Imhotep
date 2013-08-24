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
import br.com.imhotep.consulta.raiz.MaterialConsultaRaiz;
import br.com.imhotep.entidade.extra.MaterialFaltaEstoque;

@ManagedBean
@ViewScoped
public class RelatorioMaterialAbaixoQuantidadeMinima extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	public void gerarRalatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioMaterialAbaixoQuantidadeMinima.jasper";
		String nomeRelatorio = "RelatorioMaterialAbaixoQuantidadeMinima-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<MaterialFaltaEstoque> materiaisAbaixoQuantidadeMinima = new MaterialConsultaRaiz().consultarMateriaisAbaixoQuantidadeMinima();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("data", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
		super.geraRelatorio(caminho, nomeRelatorio, materiaisAbaixoQuantidadeMinima, map);
	}
	
}
