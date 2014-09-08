package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.extra.MateriaisAlmoxarifadoGrupo;

public class MateriaisAlmoxarifadoGrupoComparador implements Comparator<MateriaisAlmoxarifadoGrupo>  {  
	@Override
	public int compare(MateriaisAlmoxarifadoGrupo arg0, MateriaisAlmoxarifadoGrupo arg1) {
		return arg0.getGrupoAlmoxarifado().getDescricao().compareTo(arg1.getGrupoAlmoxarifado().getDescricao());
	}
}  
