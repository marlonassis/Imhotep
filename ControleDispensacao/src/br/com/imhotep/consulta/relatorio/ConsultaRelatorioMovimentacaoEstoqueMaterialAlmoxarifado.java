package br.com.imhotep.consulta.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.entidade.relatorio.MovimentacaoEstoqueMaterialAlmoxarifado;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.remendo.ConsultaGeral;

public class ConsultaRelatorioMovimentacaoEstoqueMaterialAlmoxarifado extends ConsultaGeral<Object[]> {
	
	public List<MovimentacaoEstoqueMaterialAlmoxarifado> consultarResultados(MaterialAlmoxarifado materialAlmoxarifado, Date dataIni, Date dataFim, Unidade unidade, TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado, TipoOperacaoEnum tipoOperacao, boolean agruparPorLote){
		return consultaMovimentoPeriodo(materialAlmoxarifado, dataIni, dataFim, unidade, tipoMovimentoAlmoxarifado, tipoOperacao, agruparPorLote);
	}
	
	private List<MovimentacaoEstoqueMaterialAlmoxarifado> consultaMovimentoPeriodo(MaterialAlmoxarifado materialAlmoxarifado, Date dataIni, Date dataFim, Unidade unidade, TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado, TipoOperacaoEnum tipoOperacao, boolean agruparPorLote) {
		ConsultaGeral<MovimentacaoEstoqueMaterialAlmoxarifado> cg = new ConsultaGeral<MovimentacaoEstoqueMaterialAlmoxarifado>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		String sql = "select new br.com.imhotep.entidade.relatorio.MovimentacaoEstoqueMaterialAlmoxarifado(o.tipoMovimentoAlmoxarifado, " +
				"a.nome, o.estoqueAlmoxarifado.lote, o.estoqueAlmoxarifado.idEstoqueAlmoxarifado, o.quantidadeMovimentacao, o.quantidadeAtual, o.dataMovimento, " +
				"b, o.estoqueAlmoxarifado.materialAlmoxarifado.descricao, o.justificativa) from MovimentoLivroAlmoxarifado o " +
				"join o.profissionalInsercao b "+
				"LEFT OUTER JOIN o.dispensacaoSimplesAlmoxarifado.unidadeDispensada a "+
				"where o.dataMovimento >= :dataIni and o.dataMovimento <= :dataFim";
		
		if(unidade != null){
			sql += " and a.idUnidade = :idUnidade ";
			map.put("idUnidade", unidade.getIdUnidade());
		}
		
		if(materialAlmoxarifado != null){
			sql += " and o.estoqueAlmoxarifado.materialAlmoxarifado.idMaterialAlmoxarifado = :idMaterialAlmoxarifado ";
			map.put("idMaterialAlmoxarifado", materialAlmoxarifado.getIdMaterialAlmoxarifado());
		}
		
		if(tipoMovimentoAlmoxarifado != null && tipoMovimentoAlmoxarifado.getIdTipoMovimentoAlmoxarifado() != 0){
			sql += " and o.tipoMovimentoAlmoxarifado.idTipoMovimentoAlmoxarifado = :idTipoMovimentoAlmoxarifado";
			map.put("idTipoMovimentoAlmoxarifado", tipoMovimentoAlmoxarifado.getIdTipoMovimentoAlmoxarifado());
		}
		
		if(tipoOperacao != null){
			sql += " and o.tipoMovimentoAlmoxarifado.tipoOperacao = :tipoOperacao";
			map.put("tipoOperacao", tipoOperacao);
		}
		
		if(agruparPorLote){
			sql += " order by o.estoqueAlmoxarifado.lote, o.dataMovimento asc";
		}else{
			sql += " order by o.dataMovimento asc";
		}
		
		ArrayList<MovimentacaoEstoqueMaterialAlmoxarifado> list = new ArrayList<MovimentacaoEstoqueMaterialAlmoxarifado>(cg.consulta(new StringBuilder(sql), map));
		
		if(list == null || list.isEmpty()){
			list.add(new MovimentacaoEstoqueMaterialAlmoxarifado(Constantes.MENSAGEM_RELATORIO_VAZIO));
		}
		
		return list;
	}
	
}
