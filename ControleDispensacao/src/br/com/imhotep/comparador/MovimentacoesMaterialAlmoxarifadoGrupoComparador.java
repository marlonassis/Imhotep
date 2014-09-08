package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.relatorio.MovimentacoesGrupoAlmoxarifadoGrupo;

public class MovimentacoesMaterialAlmoxarifadoGrupoComparador implements Comparator<MovimentacoesGrupoAlmoxarifadoGrupo>  {  
	@Override
	public int compare(MovimentacoesGrupoAlmoxarifadoGrupo arg0, MovimentacoesGrupoAlmoxarifadoGrupo arg1) {
		return arg0.getGrupo().compareTo(arg1.getGrupo());
	}
}  
