package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.DevolucaoMaterialItem;

public class DevolucaoMaterialItemComparador implements Comparator<DevolucaoMaterialItem>  {  
	@Override
	public int compare(DevolucaoMaterialItem arg0, DevolucaoMaterialItem arg1) {
		
		String descricao0 = arg0.getMaterialAlmoxarifado().getDescricao();
		String descricao1 = arg1.getMaterialAlmoxarifado().getDescricao();
		return (descricao0).compareTo(descricao1);  
	}
}  
