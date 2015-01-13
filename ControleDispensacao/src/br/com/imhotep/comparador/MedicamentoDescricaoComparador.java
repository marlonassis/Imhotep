package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.Material;

public class MedicamentoDescricaoComparador implements Comparator<Material>  {  
	@Override
	public int compare(Material arg0, Material arg1) {
		return arg0.getDescricao().compareTo(arg1.getDescricao());
	}
}  
