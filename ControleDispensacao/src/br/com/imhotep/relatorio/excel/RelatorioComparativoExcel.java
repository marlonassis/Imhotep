package br.com.imhotep.relatorio.excel;

import java.util.Iterator;
import java.util.List;

import br.com.imhotep.entidade.extra.EstoqueApoio;
import br.com.imhotep.entidade.extra.MaterialSaldoComparativo;

//Requisito Funcional #31
public class RelatorioComparativoExcel extends RelatorioExcel{
	List<MaterialSaldoComparativo> list;
	
	public RelatorioComparativoExcel( List<MaterialSaldoComparativo> list, String setor, String periodo, int qtdColunas ){
		super("Materiais por unidade", setor, periodo, qtdColunas);
		this.list = list;
	}

	@Override
	public void gerarPlanilha() {
		criarCabecalho();
		
		int[] qtdCaracteres = {85, 10, 13, 13, 20, 20};
		criarLinha("", (short)7);
		String[] header = { "Material", "Medlynx", "Saldo", "Invetariado", "Diferença"};
		criarColunasComNome(header, qtdCaracteres);
		
		for(Iterator<MaterialSaldoComparativo> itcomp = list.iterator(); itcomp.hasNext();){
			MaterialSaldoComparativo sal = itcomp.next();
			
			addCelulasLn(sal.getMaterial(), sal.getSaldoTransportado().toString(), sal.getSaldoDomingo().toString(),
					sal.getQtdInventario().toString(), new Integer(sal.getSaldoDomingo()-sal.getQtdInventario()).toString());
			
			List<EstoqueApoio> listaEstoqueInventariado = sal.getListaEstoqueInventariado();
			if((listaEstoqueInventariado!=null) && (listaEstoqueInventariado.size()>0)){
				addCelulasLn(new StringBuilder("cell_bb"), "Invertariado", "Lote", "Validade", "Saldo Atual");
				for(Iterator<EstoqueApoio> itEst = listaEstoqueInventariado.iterator(); itEst.hasNext();){
					EstoqueApoio est = itEst.next();
					addCelulasLn("", est.getLote(), est.getValidade(), est.getQuantidadeAtual().toString());
				}
			}
			
			List<EstoqueApoio> listaEstoqueSistema = sal.getListaEstoqueSistema();
			if((listaEstoqueSistema!=null) && (listaEstoqueSistema.size()>0)){
				addCelulasLn(new StringBuilder("cell_bb"), "Sistema", "Lote", "Validade", "Saldo Atual");
				for(Iterator<EstoqueApoio> itEst = listaEstoqueSistema.iterator(); itEst.hasNext();){
					EstoqueApoio est = itEst.next();
					addCelulasLn("", est.getLote(), est.getValidade(), est.getQuantidadeAtual().toString());
				}
			}
		}
	}
}
