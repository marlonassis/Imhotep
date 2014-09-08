package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;

public class MaterialGrupoSolicitacaoMedicamentoItemComparador implements Comparator<SolicitacaoMedicamentoUnidadeItem>  {  
	@Override
	public int compare(SolicitacaoMedicamentoUnidadeItem arg0, SolicitacaoMedicamentoUnidadeItem arg1) {
		if(arg0.getMaterial() != null && arg1.getMaterial() != null){
			String descricao0 = arg0.getMaterial().getDescricao();
			String descricao0Grupo = arg0.getMaterial().getFamilia().getSubGrupo().getGrupo().getDescricao();
			String descricao1 = arg1.getMaterial().getDescricao();
			String descricao1Grupo = arg1.getMaterial().getFamilia().getSubGrupo().getGrupo().getDescricao();
			return (descricao0Grupo+descricao0).compareTo(descricao1Grupo+descricao1);
		}
		return 0;
	}
}  
