package br.com.imhotep.relatorio.excel;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;






import br.com.imhotep.entidade.extra.EstoqueAlmoxarifadoMaterial;
import br.com.imhotep.entidade.extra.EstoqueAlmoxarifadoMaterialGrupo;
import br.com.imhotep.entidade.extra.EstoqueAlmoxarifadoMaterialLote;
import br.com.imhotep.entidade.extra.EstoqueAlmoxarifadoMaterialSubGrupo;

//Solicitação de Mudança #14
public class EstoqueAlmoxarifadoCompletoExcel  extends RelatorioExcel {

	List<EstoqueAlmoxarifadoMaterialGrupo> list;
	
	public EstoqueAlmoxarifadoCompletoExcel( List<EstoqueAlmoxarifadoMaterialGrupo> list, String setor, String periodo, int qtdColunas ){
		super("Estoque completo", setor, periodo, qtdColunas);
		this.list = list;
	}

	@Override
	public void gerarPlanilha() {
		criarCabecalho();
				
		int[] qtdCaracteres = {10, 20, 13, 13, 20};
		criarLinha("", (short)7);
		String[] header = { "Lote", "Fabricante", "Validade", "Saldo Atual", "Responsável"};
		
		for (EstoqueAlmoxarifadoMaterialGrupo mat : list) {			
			criarLinha(mat.getGrupo(), "cell_blue", (short)5);			
	
			Map<String, EstoqueAlmoxarifadoMaterial> mapMateria =  mat.getMapMaterial();
			Map<String, EstoqueAlmoxarifadoMaterialSubGrupo> mapSubGrupo = mat.getMapSubGrupo();
			if( (mapMateria!=null) && (mapMateria.size()>0) ){
				Set<String> chaves = mapMateria.keySet();
				for (Iterator<String> iterator = chaves.iterator(); iterator.hasNext();)  
		        {  
		            String chave = iterator.next();  
		            criarLinha(chave, "cell_b", (short)5);
		            criarColunasComNome(header, qtdCaracteres);
		            
		            EstoqueAlmoxarifadoMaterial estoque =  mapMateria.get(chave);
		            List<EstoqueAlmoxarifadoMaterialLote> lotes = estoque.getLotes();
		            for (Iterator<EstoqueAlmoxarifadoMaterialLote> iterator2 = lotes.iterator(); iterator2.hasNext();) {
						EstoqueAlmoxarifadoMaterialLote it = iterator2.next();					
						addCelulasLn(it.getLote(), it.getFabricante(), (it.getDataValidade()==null?"":it.getDataValidade().toString()), it.getQuantidadeAtual()+"", it.getResponsavel());
					}
		            criarLinha("", (short)5);
		        } 	
			}
			else if(mapSubGrupo != null){
				Set<String> chaves = mapSubGrupo.keySet();
				for (Iterator<String> iterator = chaves.iterator(); iterator.hasNext();)  
		        {  
		            String chave = iterator.next();  
		            criarLinha(chave, "cell_bb", (short)5);
	
		            EstoqueAlmoxarifadoMaterialSubGrupo estoque =  mapSubGrupo.get(chave);
		            List<EstoqueAlmoxarifadoMaterial> materiais = estoque.getMateriais();
		            for (Iterator<EstoqueAlmoxarifadoMaterial> iterator2 = materiais.iterator(); iterator2.hasNext();) {
		            	EstoqueAlmoxarifadoMaterial it = iterator2.next();
		            	criarLinha(it.getMaterial(), "cell_b", (short)5);
		            	criarColunasComNome(header, qtdCaracteres);
		            	
		            	List<EstoqueAlmoxarifadoMaterialLote> lotes = it.getLotes();
		            	for (Iterator<EstoqueAlmoxarifadoMaterialLote> iterator3 = lotes
								.iterator(); iterator3.hasNext();) {
							EstoqueAlmoxarifadoMaterialLote lote = iterator3.next();
							addCelulasLn(lote.getLote(), lote.getFabricante(), (lote.getDataValidade()==null?"":lote.getDataValidade().toString()), lote.getQuantidadeAtual()+"", lote.getResponsavel());
						}
		            	criarLinha("", (short)5);
		            	
					}
		            criarLinha("", (short)5);
		        } 
			}
			criarLinha("", (short)5);
		} 
		
	}

}
