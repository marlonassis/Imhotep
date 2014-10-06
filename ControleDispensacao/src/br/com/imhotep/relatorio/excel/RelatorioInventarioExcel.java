package br.com.imhotep.relatorio.excel;

import java.util.Iterator;
import java.util.List;

import br.com.imhotep.entidade.extra.EstoqueInventario;

//Requisito Funcional #24
public class RelatorioInventarioExcel  extends RelatorioExcel{
	List<EstoqueInventario> listaInventariada;
	List<EstoqueInventario> listaNaoInventariada;
	
	public RelatorioInventarioExcel( List<EstoqueInventario> lIvent, List<EstoqueInventario> lNIvent,String setor,
			String periodo, int qtdColunas ){
		super("Iventário", setor, periodo, qtdColunas);
		this.listaInventariada = lIvent;
		this.listaNaoInventariada = lNIvent;
	}

	@Override
	public void gerarPlanilha() {
		criarCabecalho();		
		criarLinha("", (short)5);
		
		int[] qtdCaracteres = {35, 15, 10, 12, 15};		
		String[] header = {"Material", "Lote", "Validade", "Contagem", "Saldo Atual"};
		criarColunasComNome(header, qtdCaracteres);
		
		if((listaInventariada!=null) && (listaInventariada.size()>0)){
			criarLinha("Estoque Inventariado", "cell_blue", (short)5 );
		}		
		for( Iterator<EstoqueInventario>itIvent = listaInventariada.iterator(); itIvent.hasNext(); ){
			EstoqueInventario ivent = itIvent.next();
			addCelulasLn( ivent.getMaterial(), ivent.getLote(), ivent.getValidade(), 
					ivent.getQuantidadeContada().toString(), ivent.getQuantidadeAtual().toString() );
		}
		
		
		
		if((listaNaoInventariada!=null) && (listaNaoInventariada.size()>0)){
			criarLinha("Estoque Não Inventariado", "cell_blue", (short)5 );
		}
		for( Iterator<EstoqueInventario>itIvent = listaNaoInventariada.iterator(); itIvent.hasNext(); ){
			EstoqueInventario ivent = itIvent.next();
			addCelulasLn( ivent.getMaterial(), ivent.getLote(), ivent.getValidade(), 
					(ivent.getQuantidadeContada()!=null?ivent.getQuantidadeContada().toString():"0"), 
					(ivent.getQuantidadeAtual()!=null?ivent.getQuantidadeAtual().toString():"0") );
		}
		
	}
}
