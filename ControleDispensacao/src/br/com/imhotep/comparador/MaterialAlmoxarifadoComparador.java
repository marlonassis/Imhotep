package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.MaterialAlmoxarifado;

public class MaterialAlmoxarifadoComparador implements Comparator<MaterialAlmoxarifado>  {  
	@Override
	public int compare(MaterialAlmoxarifado arg0, MaterialAlmoxarifado arg1) {
		return arg0.getDescricao().compareTo(arg1.getDescricao());
	}
}  
