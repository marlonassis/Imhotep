package br.com.imhotep.relatorio.excel;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;

import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.MaterialAlmoxarifado;

//Requisito Funcional #26
public class MaterialSemConsumoFarmaciaExcel extends RelatorioExcel {

	List<Material> list;
	
	public MaterialSemConsumoFarmaciaExcel( List<Material> list, String periodo, int qtdColunas) {
		super("Medicamentos sem solicitações", "Farmácia", periodo, qtdColunas);
		
		this.list = list;
	}

	@Override
	public void gerarPlanilha() {
criarCabecalho();
		
		// header
		String[] header = { "Material", "Código", "Grupo", "Subgrupo"};
		int[] qtdCaracteres = {100, 7, 20, 20};
		criarColunasComNome(header, qtdCaracteres);
	
		for (Material mat : list) {
			HSSFRow row = sheet.createRow(nCell);
				 
			row.createCell(0).setCellValue(mat.getDescricao());
			row.createCell(1).setCellValue(mat.getIdMaterial());
			row.createCell(2).setCellValue(mat.getFamilia().getSubGrupo().getGrupo().getDescricao());
			row.createCell(3).setCellValue(mat.getFamilia().getSubGrupo().getDescricao());
				 
			nCell++; 
		} 
	}

}
