package br.com.Imhotep.consulta.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.entidade.TipoMovimento;
import br.com.Imhotep.entidade.Unidade;
import br.com.imhotep.entidade.relatorio.MovimentacaoEstoqueUnidade;
import br.com.remendo.ConsultaGeral;

public class ConsultaRelatorioMovimentacaoEstoqueUnidade extends ConsultaGeral<Object[]> {
	
	public ArrayList<MovimentacaoEstoqueUnidade> consultarResultados(Date dataIni, Date dataFim, Unidade unidade, TipoMovimento tipoMovimento){
		return modeladorResultado(consultaMovimentoPeriodo(dataIni, dataFim, unidade, tipoMovimento));
	}

	private ArrayList<MovimentacaoEstoqueUnidade> modeladorResultado(List<Object[]> list){
		List<MovimentacaoEstoqueUnidade> meul = new ArrayList<MovimentacaoEstoqueUnidade>();
		for(Object[] meu : list){		
			MovimentacaoEstoqueUnidade obj = new MovimentacaoEstoqueUnidade();
			obj.setTipoMovimento((TipoMovimento) meu[0]);
			obj.setNomeMaterial((String) meu[1]);
			obj.setLote((String) meu[2]);
			obj.setQuantidade((Integer) meu[3]);
			obj.setDataMovimento((Date) meu[4]);
			obj.setUsuario((String) meu[5]);
			meul.add(obj);
		}
		
		if(meul == null || meul.isEmpty()){
			meul.add(new MovimentacaoEstoqueUnidade(Constantes.MENSAGEM_RELATORIO_VAZIO));
		}
		
		return new ArrayList<MovimentacaoEstoqueUnidade>(meul);
	}
	
	private List<Object[]> consultaMovimentoPeriodo(Date dataIni, Date dataFim, Unidade unidade, TipoMovimento tipoMovimento) {
		ConsultaGeral<Object[]> cg = new ConsultaGeral<Object[]>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		String sql = "select o.tipoMovimento, o.estoque.material.descricao, o.estoque.lote, o.estoque.quantidade, o.dataMovimento, o.usuarioMovimentacao.login from MovimentoLivro o where o.dataMovimento >= :dataIni and o.dataMovimento <= :dataFim";
		
		if(unidade != null){
			sql += " and o.unidadeReceptora.idUnidade = :idUnidade ";
			map.put("idUnidade", unidade.getIdUnidade());
		}
		
		if(tipoMovimento != null && tipoMovimento.getIdTipoMovimento() != 0){
			sql += " and o.tipoMovimento.idTipoMovimento = :idTipoMovimento";
			map.put("idTipoMovimento", tipoMovimento.getIdTipoMovimento());
		}
		
		sql += " order by o.dataMovimento desc";
		
		return new ArrayList<Object[]>(cg.consulta(new StringBuilder(sql), map));
	}
	
}
