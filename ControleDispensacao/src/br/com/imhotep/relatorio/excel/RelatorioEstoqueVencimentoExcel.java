package br.com.imhotep.relatorio.excel;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import br.com.imhotep.entidade.relatorio.EstoqueVencimento;

//Requisito Funcional #23
public class RelatorioEstoqueVencimentoExcel extends RelatorioExcel{
	List<EstoqueVencimento> list;
	
	public RelatorioEstoqueVencimentoExcel( List<EstoqueVencimento> list, String setor, String periodo, int qtdColunas ){
		super("Estoque por Data de Movimento", setor, periodo, qtdColunas);
		this.list = list;
	}

	@Override
	public void gerarPlanilha() {
		criarCabecalho();
		
		criarLinha("", (short)7);
		int[] qtdCaracteres = {10, 35, 15, 10, 12, 15, 20};		
		String[] header = { "Código", "Material", "Lote", "Validade", "Quantidade", "Bloqueado", "Usuário"};
		criarColunasComNome(header, qtdCaracteres);
		
		for( Iterator<EstoqueVencimento> itEst = list.iterator(); itEst.hasNext(); ){
			EstoqueVencimento est = itEst.next();
			
			addCelulasLn( est.getCodigoMaterial().toString(), est.getNomeMaterial(), est.getLote(), new SimpleDateFormat("MM/yyyy").format(est.getDataValidade()), 
					est.getQuantidade().toString(), (est.isBloqueado()?"Sim":"Não") ,est.getUsuario() );
		}
		
	}
}
