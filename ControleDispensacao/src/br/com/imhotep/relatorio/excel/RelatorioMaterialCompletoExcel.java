package br.com.imhotep.relatorio.excel;

import java.util.Iterator;
import java.util.List;

import br.com.imhotep.entidade.Material;

//Requisito Funcional #28
public class RelatorioMaterialCompletoExcel extends RelatorioExcel{
	List<Material> list;
	
	public RelatorioMaterialCompletoExcel( List<Material> list, String setor, String periodo, int qtdColunas ){
		super("Estoque por Data de Movimento", setor, periodo, qtdColunas);
		this.list = list;
	}

	@Override
	public void gerarPlanilha() {
		criarCabecalho();
		
		criarLinha("", (short)7);
		int[] qtdCaracteres = {10, 45, 15, 20, 12, 15, 20};		
		String[] header = { "Código", "Material", "Unidade", "Tipo", "Lista", "Padronizado", "Bloqueado", "Qtd Mín"};
		criarColunasComNome(header, qtdCaracteres);
				
		for(Iterator<Material> itMat =list.iterator(); itMat.hasNext();){
			Material mat= itMat.next();
			addCelulasLn(mat.getCodigoMaterial().toString(), mat.getDescricao(), mat.getUnidadeMaterial().getDescricao(),
					(mat.getTipoMaterial()==null?"":mat.getTipoMaterial().getDescricao()), (mat.getListaEspecial()==null?"":mat.getListaEspecial().getDescricao()),
					(mat.getPadronizado()?"S":"N"),(mat.getBloqueado()?"S":"N"), (mat.getQuantidadeMinima()==null?"":mat.getQuantidadeMinima().toString()) );
		}
	}
}
