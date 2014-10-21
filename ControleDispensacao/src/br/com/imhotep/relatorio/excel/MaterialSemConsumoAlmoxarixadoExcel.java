package br.com.imhotep.relatorio.excel;

import java.util.List;



import org.apache.poi.hssf.usermodel.HSSFRow;

import br.com.imhotep.entidade.MaterialAlmoxarifado;

//Requisito Funcional #20
public class MaterialSemConsumoAlmoxarixadoExcel extends RelatorioExcel{
	
	List<MaterialAlmoxarifado> list;
	
	public MaterialSemConsumoAlmoxarixadoExcel( List<MaterialAlmoxarifado> list, String setor, String periodo, int qtdColunas ){
		super("Material sem consumo", setor, periodo, 4);
		this.list = list;
	}
	
	@Override
	public void gerarPlanilha(){
		criarCabecalho();
		
		// header
		String[] header = { "Grupo", "Subgrupo", "Material", "Código"};
		int[] qtdCaracteres = {30, 40, 100, 7};
		criarColunasComNome(header, qtdCaracteres);
	
		for (MaterialAlmoxarifado mat : list) {
			HSSFRow row = sheet.createRow(nCell);
				 
			row.createCell(0).setCellValue(mat.getGrupoAlmoxarifado().getDescricao());
			row.createCell(1).setCellValue(mat.getSubGrupoAlmoxarifado().getDescricao());
			row.createCell(2).setCellValue(mat.getDescricao());
			row.createCell(3).setCellValue(mat.getIdMaterialAlmoxarifado());
				 
			nCell++; 
		} 
	} 
	
}
