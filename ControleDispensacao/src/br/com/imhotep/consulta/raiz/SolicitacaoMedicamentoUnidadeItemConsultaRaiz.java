package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.imhotep.raiz.SolicitacaoMedicamentoUnidadeRaiz;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class SolicitacaoMedicamentoUnidadeItemConsultaRaiz  extends ConsultaGeral<SolicitacaoMedicamentoUnidadeItem>{

	public List<SolicitacaoMedicamentoUnidadeItem> getItens() {
		String hql = "select o from SolicitacaoMedicamentoUnidadeItem o where o.solicitacaoMedicamentoUnidade.idSolicitacaoMedicamentoUnidade = "
						+SolicitacaoMedicamentoUnidadeRaiz.getInstanciaAtual().getInstancia().getIdSolicitacaoMedicamentoUnidade()+" order by o.material.descricao";
		return new ArrayList<SolicitacaoMedicamentoUnidadeItem>(new ConsultaGeral<SolicitacaoMedicamentoUnidadeItem>().consulta(new StringBuilder(hql), null));
	}
	
}