package br.com.Imhotep.consulta.relatorio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import br.com.Imhotep.entidade.ItensMovimentoGeral;
import br.com.imhotep.entidade.relatorio.CustoEstoque;
import br.com.remendo.ConsultaGeral;

public class ConsultaRelatorioConsumoEstoque extends ConsultaGeral<ItensMovimentoGeral> {
	
	public ArrayList<CustoEstoque> pegarResultados(Date ini, Date fim){
		return modeladorResultado(consultaMovimentoPeriodo(ini, fim));
	}

	private ArrayList<CustoEstoque> modeladorResultado(Collection<ItensMovimentoGeral> itensMovimentoGeralCollection){
		ArrayList<CustoEstoque> listaCusto = new ArrayList<CustoEstoque>();
		CustoEstoque ce = new CustoEstoque();
		for(ItensMovimentoGeral obj : itensMovimentoGeralCollection){
			if(!obj.getEstoque().getLote().equals(obj.getEstoque().getLote())){
				listaCusto.add(ce);
				ce = new CustoEstoque();
			}
			ce.setMaterial(obj.getEstoque().getMaterial());
			ce.setLote(obj.getEstoque().getLote());
			ce.setQuantidadeEntrada(obj.getQuantidade());
			ce.setTotal(ce.getQuantidadeEntrada() * obj.getEstoque().getValorUnitario());
		}
		listaCusto.add(ce);
		return listaCusto;
	}
	
	private Collection<ItensMovimentoGeral> consultaMovimentoPeriodo(Date ini, Date fim) {
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("dataIni", ini);
		hm.put("dataFim", fim);
		StringBuilder stringB = new StringBuilder("select o from ItensMovimentoGeral o where o.dataCriacao >= :dataIni and o.dataCriacao <= :dataFim and o.estoque.bloqueado = false and o.movimentoGeral.tipoMovimento.TipoOperacaoEnum = 'Entrada'");
		return super.consulta(stringB, hm);
	}
	
}
