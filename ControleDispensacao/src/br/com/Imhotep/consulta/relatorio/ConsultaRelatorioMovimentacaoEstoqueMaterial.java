package br.com.Imhotep.consulta.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.TipoMovimento;
import br.com.Imhotep.entidade.Unidade;
import br.com.imhotep.entidade.relatorio.MovimentacaoEstoqueMaterial;
import br.com.remendo.ConsultaGeral;

public class ConsultaRelatorioMovimentacaoEstoqueMaterial extends ConsultaGeral<Object[]> {
	
	public List<MovimentacaoEstoqueMaterial> consultarResultados(Material material, Date dataIni, Date dataFim, Unidade unidade, TipoMovimento tipoMovimento){
		return consultaMovimentoPeriodo(material, dataIni, dataFim, unidade, tipoMovimento);
	}
	
	private List<MovimentacaoEstoqueMaterial> consultaMovimentoPeriodo(Material material, Date dataIni, Date dataFim, Unidade unidade, TipoMovimento tipoMovimento) {
		ConsultaGeral<MovimentacaoEstoqueMaterial> cg = new ConsultaGeral<MovimentacaoEstoqueMaterial>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		String sql = "select new br.com.imhotep.entidade.relatorio.MovimentacaoEstoqueMaterial(o.tipoMovimento, " +
				"a.nome, o.estoque.lote, o.quantidadeMovimentacao, o.dataMovimento, " +
				"o.usuarioMovimentacao.login, o.estoque.material.descricao) from MovimentoLivro o " +
				"LEFT OUTER JOIN o.dispensacaoSimples.unidadeDispensada a "+
				"where o.dataMovimento >= :dataIni and o.dataMovimento <= :dataFim";
		
		if(unidade != null){
			sql += " and a.idUnidade = :idUnidade ";
			map.put("idUnidade", unidade.getIdUnidade());
		}
		
		if(material != null){
			sql += " and o.estoque.material.idMaterial = :idMaterial ";
			map.put("idMaterial", material.getIdMaterial());
		}
		
		if(tipoMovimento != null && tipoMovimento.getIdTipoMovimento() != 0){
			sql += " and o.tipoMovimento.idTipoMovimento = :idTipoMovimento";
			map.put("idTipoMovimento", tipoMovimento.getIdTipoMovimento());
		}
		
		sql += " order by o.dataMovimento desc";
		
		ArrayList<MovimentacaoEstoqueMaterial> list = new ArrayList<MovimentacaoEstoqueMaterial>(cg.consulta(new StringBuilder(sql), map));
		
		if(list == null || list.isEmpty()){
			list.add(new MovimentacaoEstoqueMaterial(Constantes.MENSAGEM_RELATORIO_VAZIO));
		}
		
		return list;
	}
	
}
