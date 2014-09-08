package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.relatorio.MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes;

public class MovimentacoesMaterialAlmoxarifadoGrupoMaterialMovimentosComparador implements Comparator<MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes>  {  
	@Override
	public int compare(MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes arg0, MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes arg1) {
		if(arg0 != null && arg1 != null){
			return arg0.getDataMovimento().compareTo(arg1.getDataMovimento());
		}
		return 0;
	}
}  
