package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.extra.MateriaisAlmoxarifadoGrupoSubGrupo;

public class MateriaisAlmoxarifadoGrupoSubGrupoComparador implements Comparator<MateriaisAlmoxarifadoGrupoSubGrupo>  {  
	@Override
	public int compare(MateriaisAlmoxarifadoGrupoSubGrupo arg0, MateriaisAlmoxarifadoGrupoSubGrupo arg1) {
		String descricao = arg0.getSubGrupoAlmoxarifado() == null ? "" : arg0.getSubGrupoAlmoxarifado().getDescricao();
		String descricao2 = arg1.getSubGrupoAlmoxarifado() == null ? "" : arg1.getSubGrupoAlmoxarifado().getDescricao();
		return descricao.compareTo(descricao2);
	}
}  
