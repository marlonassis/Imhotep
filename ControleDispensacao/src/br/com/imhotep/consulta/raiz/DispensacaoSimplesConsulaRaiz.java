package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.DispensacaoSimples;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class DispensacaoSimplesConsulaRaiz  extends ConsultaGeral<DispensacaoSimples>{
	
	public List<DispensacaoSimples> consultarDispensacoesSolicitacaoItem(SolicitacaoMedicamentoUnidadeItem item) {
		String hql = "select o from DispensacaoSimples o where o.solicitacaoMedicamentoUnidadeItem.idSolicitacaoMedicamentoUnidadeItem = "+item.getIdSolicitacaoMedicamentoUnidadeItem();
		List<DispensacaoSimples> list = new ArrayList<DispensacaoSimples>(new ConsultaGeral<DispensacaoSimples>().consulta(new StringBuilder(hql), null));
		return list;
	}
	
}
