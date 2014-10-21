package br.com.imhotep.relatorio.excel;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;

import br.com.imhotep.entidade.extra.RelMaterialPorUnidade;
import br.com.imhotep.entidade.extra.RelMaterialPorUnidade.Itens;

public class MaterialPorUnidadeFarmaciaExcel  extends RelatorioExcel {

	List<RelMaterialPorUnidade> list;
	
	public MaterialPorUnidadeFarmaciaExcel( List<RelMaterialPorUnidade> list, String setor, String periodo, int qtdColunas ){
		super("Materiais por unidade", setor, periodo, qtdColunas);
		this.list = list;
	}

	@Override
	public void gerarPlanilha() {
criarCabecalho();
				
		int[] qtdCaracteres = {85, 10, 13, 13, 20, 20};
		criarLinha("", (short)7);
		String[] header = { "Medicamento", "Código", "Solicitada", "Atendida", "Grupo", "Subgrupo"};
		
		for (RelMaterialPorUnidade mat : list) {
			
			criarLinha(mat.getUnidade(), "cell_blue", (short)6);
			
			criarColunasComNome(header, qtdCaracteres);
			List<Itens> itens = mat.getItens();
			for( Itens it : itens ){
				HSSFRow row2 = sheet.createRow(nCell);
				row2.createCell(0).setCellValue(it.getMaterial());
				row2.createCell(1).setCellValue(it.getCodigo());
				row2.createCell(2).setCellValue(it.getQtd_solicitada());
				row2.createCell(3).setCellValue(it.getQtd_consumida());
				row2.createCell(4).setCellValue(it.getGrupo());
				row2.createCell(5).setCellValue(it.getSubgrupo());
				nCell++;	
			}
				
			criarLinha("", (short)7);
			//nCell++; 
		} 
		
	}

}
