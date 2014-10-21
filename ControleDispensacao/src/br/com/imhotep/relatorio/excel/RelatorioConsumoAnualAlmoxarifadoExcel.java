package br.com.imhotep.relatorio.excel;

import java.util.List;

import br.com.imhotep.entidade.extra.ConsumoAnualAlmoxarifado;

//Solicitação de Mudança #12
public class RelatorioConsumoAnualAlmoxarifadoExcel extends RelatorioExcel {

	List<ConsumoAnualAlmoxarifado> list;
	
	public RelatorioConsumoAnualAlmoxarifadoExcel( List<ConsumoAnualAlmoxarifado> list, String setor, String periodo, int qtdColunas ){
		super("Entrada e Consumo Anual", setor, periodo, qtdColunas);
		this.list = list;
	}

	@Override
	public void gerarPlanilha() {
		criarCabecalho();
		
		int[] qtdCaracteres = {60, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
		criarLinha("", (short)7);
		String[] header = { "Material", "Janeiro", "Fevereiro", "Março", "Abril",
				"Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
		criarColunasComNome(header, qtdCaracteres);
		

		for(ConsumoAnualAlmoxarifado material : this.list) { 
			Object[] cons =  material.getTotalEntradaConsumoList().toArray();
			addCelulasLn(material.getDescricaoCompleta(), cons[0].toString(), cons[1].toString(), cons[2].toString(), cons[3].toString(), 
					cons[4].toString(), cons[5].toString(), cons[6].toString(), cons[7].toString(), cons[8].toString(), cons[9].toString(),
					cons[10].toString(), cons[11].toString() );
		}
		
	}
}
