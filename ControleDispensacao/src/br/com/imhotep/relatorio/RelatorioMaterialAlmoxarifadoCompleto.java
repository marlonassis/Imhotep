package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.MateriaisAlmoxarifadoGrupoConsultaRaiz;
import br.com.imhotep.entidade.extra.MateriaisAlmoxarifadoGrupo;
import br.com.imhotep.relatorio.excel.EstoqueAlmoxarifadoCompletoExcel;
import br.com.imhotep.relatorio.excel.RelatorioMaterialAlmoxarifadoCompletoExcel;

@ManagedBean
@ViewScoped
public class RelatorioMaterialAlmoxarifadoCompleto extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private boolean excel;
	
	public void gerarRalatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String nomeRelatorio;
		List<MateriaisAlmoxarifadoGrupo> lista = new MateriaisAlmoxarifadoGrupoConsultaRaiz().consultaGeralMateriais();
		
		//Requisito Funcional #19
		if(excel==false){
			String caminho = Constantes.DIR_RELATORIO + "RelatorioMaterialAlmoxarifado.jasper";
			nomeRelatorio = "RelatorioMaterialAlmoxarifado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";			
			HashMap<String, Object> map = new HashMap<String, Object>();
	
			InputStream subInputStream = this.getClass().getResourceAsStream("RelatorioMaterialAlmoxarifadoMateriais.jasper");
			map.put("SUBREPORT_INPUT_STREAM_MATERIAIS", subInputStream);
			
			InputStream subInputStream2 = this.getClass().getResourceAsStream("RelatorioMaterialAlmoxarifadoSubGrupo.jasper");
			map.put("SUBREPORT_INPUT_STREAM_SUBGRUPO", subInputStream2);
			
			super.geraRelatorio(caminho, nomeRelatorio, lista, map);
		}
		else{
			nomeRelatorio = "RelatorioMaterialAlmoxarifado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
			RelatorioMaterialAlmoxarifadoCompletoExcel exc;
	        exc = new RelatorioMaterialAlmoxarifadoCompletoExcel(lista, "Almoxarifado", null,4);
	        exc.gerarPlanilha();
			super.geraRelatorioExcel(nomeRelatorio, exc.getWorkbook());
		}
	}

	public boolean isExcel() {
		return excel;
	}

	public void setExcel(boolean excel) {
		this.excel = excel;
	}
	
}
