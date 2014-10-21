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
import br.com.imhotep.consulta.raiz.MaterialCompletoConsultaRaiz;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.relatorio.excel.RelatorioEstoqueVencimentoExcel;
import br.com.imhotep.relatorio.excel.RelatorioMaterialCompletoExcel;

@ManagedBean
@ViewScoped
public class RelatorioMaterialCompleto extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	private boolean excel;
	
	public void gerarRalatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String nomeRelatorio;
		List<Material> listaEstoqueRelatorioGeral = new MaterialCompletoConsultaRaiz().consultar();
		
		//Requisito Funcional #28
		if(excel==false){
			String caminho = Constantes.DIR_RELATORIO + "RelatorioMaterialCompleto.jasper";
			nomeRelatorio = "RelatorioMaterial-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("data", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
			map.put("nomeRelatorio", "Materiais Cadastrados");
			super.geraRelatorio(caminho, nomeRelatorio, listaEstoqueRelatorioGeral, map);
		}
		else{
			nomeRelatorio = "RelatorioMaterial-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
			RelatorioMaterialCompletoExcel exc;
	        exc = new RelatorioMaterialCompletoExcel( listaEstoqueRelatorioGeral, "Farmácia", null,8);
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
