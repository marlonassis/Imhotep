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
import br.com.imhotep.relatorio.excel.RelatorioEstoqueVencimentoExcel;
import br.com.imhotep.relatorio.excel.RelatorioMaterialAbaixoQuantidadeMinimaExcel;

@ManagedBean
@ViewScoped
public class RelatorioMaterialAbaixoQuantidadeMinima extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	private boolean excel;
	
	public void gerarRalatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String nomeRelatorio; 
		List<MaterialFaltaEstoque> materiaisAbaixoQuantidadeMinima = new MaterialConsultaRaiz().consultarMateriaisAbaixoQuantidadeMinima();
		
		//Requisito Funcional #25
		if(excel==false){
			String caminho = Constantes.DIR_RELATORIO + "RelatorioMaterialAbaixoQuantidadeMinima.jasper";
			nomeRelatorio = "RelatorioMaterialAbaixoQuantidadeMinima-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("data", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
			super.geraRelatorio(caminho, nomeRelatorio, materiaisAbaixoQuantidadeMinima, map);
		}
		else{
			nomeRelatorio = "RelatorioMaterialAbaixoQuantidadeMinima-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
			RelatorioMaterialAbaixoQuantidadeMinimaExcel exc;
	        exc = new RelatorioMaterialAbaixoQuantidadeMinimaExcel( materiaisAbaixoQuantidadeMinima, "Farmácia", null,4);
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
