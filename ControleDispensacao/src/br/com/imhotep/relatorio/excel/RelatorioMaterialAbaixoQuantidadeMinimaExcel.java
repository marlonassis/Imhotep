package br.com.imhotep.relatorio.excel;

import java.util.Iterator;
import java.util.List;

import br.com.imhotep.entidade.extra.MaterialFaltaEstoque;

//Requisito Funcional #25
public class RelatorioMaterialAbaixoQuantidadeMinimaExcel extends RelatorioExcel{
	List<MaterialFaltaEstoque> list;
	
	public RelatorioMaterialAbaixoQuantidadeMinimaExcel( List<MaterialFaltaEstoque> list, String setor, String periodo, int qtdColunas ){
		super("Material Abaixo da Quantidade", setor, periodo, qtdColunas);
		this.list = list;
	}

	@Override
	public void gerarPlanilha() {		
		criarCabecalho();
		
		criarLinha("", (short)4);
		int[] qtdCaracteres = {10, 35, 15, 10, 12, 15, 20};		
		String[] header = { "Código", "Material", "Qtd Mín.", "Qtd Atual"};
		criarColunasComNome(header, qtdCaracteres);
		
		for(Iterator<MaterialFaltaEstoque> itMat = list.iterator(); itMat.hasNext();){
			MaterialFaltaEstoque mat = itMat.next();
			addCelulasLn(mat.getCodigoMaterial().toString(), mat.getMaterial(), 
					mat.getQuantidadeMinima().toString(), mat.getQuantidadeAtual().toString());
		}
	}
}
