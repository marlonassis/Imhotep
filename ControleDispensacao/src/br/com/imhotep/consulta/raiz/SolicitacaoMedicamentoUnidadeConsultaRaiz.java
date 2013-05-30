package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class SolicitacaoMedicamentoUnidadeConsultaRaiz  extends ConsultaGeral<SolicitacaoMedicamentoUnidade>{

	public List<SolicitacaoMedicamentoUnidade> consultarSolicitacoesPendentes() {
		StringBuilder sb = new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where o.statusDispensacao = 'P'");
		ConsultaGeral<SolicitacaoMedicamentoUnidade> cg = new ConsultaGeral<SolicitacaoMedicamentoUnidade>();
		return new ArrayList<SolicitacaoMedicamentoUnidade>(cg.consulta(sb, null));
	}
	
	public Long quantidadeSolicitacoesPendentes() {
		StringBuilder sb = new StringBuilder("select coalesce(count(o), 0) from SolicitacaoMedicamentoUnidade o where o.statusDispensacao = 'P'");
		ConsultaGeral<Long> cg = new ConsultaGeral<Long>();
		return cg.consultaUnica(sb, null);
	}
	
}
