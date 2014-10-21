package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidadeItem;

public class MaterialGrupoSolicitacaoAlmoxarifadoItemComparador implements Comparator<SolicitacaoMaterialAlmoxarifadoUnidadeItem>  {  
	@Override
	public int compare(SolicitacaoMaterialAlmoxarifadoUnidadeItem arg0, SolicitacaoMaterialAlmoxarifadoUnidadeItem arg1) {
		if(arg0.getMaterialAlmoxarifado() != null && arg1.getMaterialAlmoxarifado() != null){
			String descricao0 = arg0.getMaterialAlmoxarifado().getDescricao();
			String descricao0Grupo = arg0.getMaterialAlmoxarifado().getGrupoAlmoxarifado().getDescricao();
			String descricao1 = arg1.getMaterialAlmoxarifado().getDescricao();
			String descricao1Grupo = arg1.getMaterialAlmoxarifado().getGrupoAlmoxarifado().getDescricao();
			return (descricao0Grupo+descricao0).compareTo(descricao1Grupo+descricao1);
		}
		return 0;
	}
}  
