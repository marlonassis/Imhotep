package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.DevolucaoMedicamentoItem;

public class MaterialGrupoDevolucaoMedicamentoItemComparador implements Comparator<DevolucaoMedicamentoItem>  {  
	@Override
	public int compare(DevolucaoMedicamentoItem arg0, DevolucaoMedicamentoItem arg1) {
		
		String descricao0 = arg0.getMaterial().getDescricao();
		String descricao0Grupo = arg0.getMaterial().getFamilia().getSubGrupo().getGrupo().getDescricao();
		String descricao1 = arg1.getMaterial().getDescricao();
		String descricao1Grupo = arg1.getMaterial().getFamilia().getSubGrupo().getGrupo().getDescricao();
		return (descricao0Grupo+descricao0).compareTo(descricao1Grupo+descricao1);  
	}
}  
