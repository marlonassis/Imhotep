package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.imhotep.entidade.DispensacaoSimples;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.remendo.ConsultaGeral;

public class DispensacaoSimplesConsultaRaiz extends ConsultaGeral<DispensacaoSimples> {
	
	public List<DispensacaoSimples> dispensacoesSolicitacaoItem(SolicitacaoMedicamentoUnidadeItem item){
		String sql = "select o from DispensacaoSimples o where o.solicitacaoMedicamentoUnidadeItem.idSolicitacaoMedicamentoUnidadeItem = "+item.getIdSolicitacaoMedicamentoUnidadeItem()+" order by o.movimentoLivro.estoque.dataValidade asc";
		Collection<DispensacaoSimples> dispensacoes = new ConsultaGeral<DispensacaoSimples>().consulta(new StringBuilder(sql), null);
		return new ArrayList<DispensacaoSimples>(dispensacoes);
	}
	
	public Long totalLiberadoItem(SolicitacaoMedicamentoUnidadeItem item){
		String sql = "select sum(o.movimentoLivro.quantidadeMovimentacao) from DispensacaoSimples o where o.solicitacaoMedicamentoUnidadeItem.idSolicitacaoMedicamentoUnidadeItem = "+item.getIdSolicitacaoMedicamentoUnidadeItem();
		return new ConsultaGeral<Long>().consultaUnica(new StringBuilder(sql), null);
	}
	
}
