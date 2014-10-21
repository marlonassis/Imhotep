package br.com.imhotep.relatorio.excel;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import br.com.imhotep.entidade.relatorio.MovimentacaoEstoqueMaterialAlmoxarifado;

public class RelatorioMovimentacaoEstoqueMaterialAlmoxarifadoExcel extends RelatorioExcel{
	List<MovimentacaoEstoqueMaterialAlmoxarifado> list;
	String nomeMaterial;
	
	public RelatorioMovimentacaoEstoqueMaterialAlmoxarifadoExcel( List<MovimentacaoEstoqueMaterialAlmoxarifado> list, 
			String setor, String periodo, int qtdColunas, String nomeMaterial ){
		super("Movimentação do Material", setor, periodo, qtdColunas);
		this.list = list;	
		this.nomeMaterial = nomeMaterial;
	}

	@Override
	public void gerarPlanilha() {
		criarCabecalho();
		
		criarLinha("", (short)9);
		
		int[] qtdCaracteresMov = {15,30, 30, 7, 15, 25, 15, 20, 30};		
		String[] headerMov = { "Operação", "Tipo Movimento","Unidade", "Lote", "Saldo Anterior", "Quantidade Movimentada", "Saldo Restante", "Data", "Usuário"};
		
		criarLinha( nomeMaterial, "cell_blue", (short)9);
		criarColunasComNome(headerMov, qtdCaracteresMov);
		for(Iterator<MovimentacaoEstoqueMaterialAlmoxarifado>itMov = list.iterator(); itMov.hasNext();){
			MovimentacaoEstoqueMaterialAlmoxarifado mov = itMov.next();
			
			addCelulasLn(mov.getTipoMovimentoAlmoxarifado().getTipoOperacao().toString(), mov.getTipoMovimentoAlmoxarifado().getDescricao(),
					mov.getNomeUnidade(), mov.getLote(), mov.getQuantidadeAtual().toString(),
					mov.getQuantidade().toString(), mov.getSaldoRestante().toString(), 
					new SimpleDateFormat("dd/MM/yyyy").format(mov.getDataMovimento()), mov.getUsuario());
		}
	}
}
