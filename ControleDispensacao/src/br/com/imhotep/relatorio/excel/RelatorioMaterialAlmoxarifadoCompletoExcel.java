package br.com.imhotep.relatorio.excel;

import java.util.Iterator;
import java.util.List;

import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.extra.MateriaisAlmoxarifadoGrupo;
import br.com.imhotep.entidade.extra.MateriaisAlmoxarifadoGrupoSubGrupo;

//Requisito Funcional #19
public class RelatorioMaterialAlmoxarifadoCompletoExcel  extends RelatorioExcel{
	
	List<MateriaisAlmoxarifadoGrupo> list;
	short qtdColunas;
	
	public RelatorioMaterialAlmoxarifadoCompletoExcel( List<MateriaisAlmoxarifadoGrupo> list, String setor, String periodo, int qtdColunas ){
		super("Estoque completo", setor, periodo, qtdColunas);
		this.list = list;
		this.qtdColunas = (short)qtdColunas;
	}
	
	@Override
	public void gerarPlanilha() {
		criarCabecalho();
		
		int[] qtdCaracteres = {10, 70, 13, 20};
		criarLinha("", (short)7);
		String[] header = { "Código", "Descrição", "Bloqueado", "Quantidade Mínima"};
		
		for (Iterator<MateriaisAlmoxarifadoGrupo> iterator = list.iterator(); iterator.hasNext();) {
			MateriaisAlmoxarifadoGrupo grupo = iterator.next();
			criarLinha(grupo.getGrupoAlmoxarifado().getDescricao(), "cell_blue", qtdColunas);
			
			List<MateriaisAlmoxarifadoGrupoSubGrupo> subGrupos = grupo.getSubGrupos();
			List<MaterialAlmoxarifado> materiais = grupo.getMateriais();
			if( (subGrupos!=null) && (subGrupos.size()>0) ){
				
				//subgrupo
				for (Iterator<MateriaisAlmoxarifadoGrupoSubGrupo> iterator2 = subGrupos.iterator(); iterator2.hasNext();) {
					MateriaisAlmoxarifadoGrupoSubGrupo sub = iterator2.next();
					
					criarLinha(sub.getSubGrupoAlmoxarifado().getDescricao(), "cell_bb", qtdColunas);
					criarColunasComNome(header, qtdCaracteres);
					for (Iterator<MaterialAlmoxarifado> iterator3 = sub.getMateriais().iterator(); iterator3.hasNext();) {
						MaterialAlmoxarifado mat = iterator3.next();
						addCelulasLn(mat.getIdMaterialAlmoxarifado()+"", mat.getDescricao(), mat.getBloqueado().toString(), mat.getQuantidadeMinima().toString());
					}
					criarLinha("", qtdColunas);
				}
			}
			else if( (materiais!=null) && (materiais.size()>0) ){
				criarColunasComNome(header, qtdCaracteres);
				for (Iterator<MaterialAlmoxarifado> iterator3 = materiais.iterator(); iterator3.hasNext();) {
					MaterialAlmoxarifado mat = iterator3.next();
					addCelulasLn(mat.getIdMaterialAlmoxarifado()+"", mat.getDescricao(), mat.getBloqueado().toString(), mat.getQuantidadeMinima().toString());
				}
			}
			criarLinha("", qtdColunas);
		}
	}

}
