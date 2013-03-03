package br.com.Imhotep.consulta.relatorio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.auxiliar.Utilities;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.TipoMovimento;
import br.com.Imhotep.entidade.Unidade;
import br.com.Imhotep.entidade.extra.MovimentacaoEstoqueFarmacia;
import br.com.remendo.ConsultaGeral;

public class ConsultaRelatorioMovimentacaoEstoqueFarmacia extends ConsultaGeral<Object[]> {
	
	public ArrayList<MovimentacaoEstoqueFarmacia> consultarResultados(Material material, Date dataIni, Date dataFim, Unidade unidade, TipoMovimento tipoMovimento){
		Calendar df = Utilities.ajustarUltimaHoraDia(dataFim);
		return modeladorResultado(consultaMovimentoPeriodo(material, dataIni, df.getTime(), unidade, tipoMovimento));
	}

	private ArrayList<MovimentacaoEstoqueFarmacia> modeladorResultado(List<Object[]> list){
		List<MovimentacaoEstoqueFarmacia> meul = new ArrayList<MovimentacaoEstoqueFarmacia>();
		for(Object[] meu : list){		
			MovimentacaoEstoqueFarmacia obj = new MovimentacaoEstoqueFarmacia();
			obj.setTipoMovimento((TipoMovimento) meu[0]);
			obj.setNomeUnidade((String) meu[1]);
			obj.setLote((String) meu[2]);
			obj.setQuantidade((Integer) meu[3]);
			obj.setDataMovimento((Date) meu[4]);
			obj.setUsuario((String) meu[5]);
			obj.setNomeMaterial((String) meu[6]);
			meul.add(obj);
		}
		
		if(meul == null || meul.isEmpty()){
			meul.add(new MovimentacaoEstoqueFarmacia(Constantes.MENSAGEM_RELATORIO_VAZIO));
		}
		
		return new ArrayList<MovimentacaoEstoqueFarmacia>(meul);
	}
	
	private List<Object[]> consultaMovimentoPeriodo(Material material, Date dataIni, Date dataFim, Unidade unidade, TipoMovimento tipoMovimento) {
		ConsultaGeral<Object[]> cg = new ConsultaGeral<Object[]>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		String sql = "select o.tipoMovimento, o.unidadeReceptora.nome, o.estoque.lote, o.quantidadeMovimentacao, o.dataMovimento, o.usuarioMovimentacao.login, o.estoque.material.descricao from MovimentoLivro o where o.dataMovimento >= :dataIni and o.dataMovimento <= :dataFim";
		
		if(unidade != null){
			sql += " and o.unidadeReceptora.idUnidade = :idUnidade ";
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
		
		return new ArrayList<Object[]>(cg.consulta(new StringBuilder(sql), map));
	}
	
}
