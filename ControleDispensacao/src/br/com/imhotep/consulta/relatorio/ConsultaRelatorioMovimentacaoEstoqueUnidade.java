package br.com.imhotep.consulta.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.entidade.relatorio.MovimentacaoEstoqueUnidade;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.remendo.ConsultaGeral;

public class ConsultaRelatorioMovimentacaoEstoqueUnidade extends ConsultaGeral<Object[]> {
	
	public List<MovimentacaoEstoqueUnidade> consultarResultados(Date dataIni, Date dataFim, Unidade unidade, TipoMovimento tipoMovimento, TipoOperacaoEnum tipoOperacao){
		return consultaMovimentoPeriodo(dataIni, dataFim, unidade, tipoMovimento, tipoOperacao);
	}

	private List<MovimentacaoEstoqueUnidade> consultaMovimentoPeriodo(Date dataIni, Date dataFim, Unidade unidade, TipoMovimento tipoMovimento, TipoOperacaoEnum tipoOperacao) {
		ConsultaGeral<MovimentacaoEstoqueUnidade> cg = new ConsultaGeral<MovimentacaoEstoqueUnidade>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		String sql = "select new br.com.imhotep.entidade.relatorio.MovimentacaoEstoqueUnidade(o.movimentoLivro.tipoMovimento, o.movimentoLivro.estoque.material.descricao, o.movimentoLivro.estoque.lote, o.movimentoLivro.quantidadeMovimentacao, o.movimentoLivro.quantidadeAtual, o.movimentoLivro.dataMovimento, o.movimentoLivro.usuarioMovimentacao.login) from DispensacaoSimples o where o.movimentoLivro.dataMovimento >= :dataIni and o.movimentoLivro.dataMovimento <= :dataFim";
		
		if(unidade != null){
			sql += " and o.unidadeDispensada.idUnidade = :idUnidade ";
			map.put("idUnidade", unidade.getIdUnidade());
		}
		
		if(tipoMovimento != null && tipoMovimento.getIdTipoMovimento() != 0){
			sql += " and o.movimentoLivro.tipoMovimento.idTipoMovimento = :idTipoMovimento";
			map.put("idTipoMovimento", tipoMovimento.getIdTipoMovimento());
		}
		
		if(tipoOperacao != null){
			sql += " and o.movimentoLivro.tipoMovimento.tipoOperacao = :tipoOperacao";
			map.put("tipoOperacao", tipoOperacao);
		}
		
		sql += " order by o.movimentoLivro.dataMovimento desc";
		
		ArrayList<MovimentacaoEstoqueUnidade> arrayList = new ArrayList<MovimentacaoEstoqueUnidade>(cg.consulta(new StringBuilder(sql), map));
		
		if(arrayList == null || arrayList.isEmpty()){
			arrayList.add(new MovimentacaoEstoqueUnidade(Constantes.MENSAGEM_RELATORIO_VAZIO));
		}	
		
		return arrayList;
	}
	
}
