package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.raiz.SolicitacaoMedicamentoUnidadeRaiz;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class SolicitacaoMedicamentoUnidadeConsultaRaiz  extends ConsultaGeral<SolicitacaoMedicamentoUnidade>{

	public void consultarSolicitacoesPendentes() {
		StringBuilder sb = new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where o.statusDispensacao = 'P'");
		ConsultaGeral<SolicitacaoMedicamentoUnidade> cg = new ConsultaGeral<SolicitacaoMedicamentoUnidade>();
		SolicitacaoMedicamentoUnidadeRaiz.getInstanciaAtual().setSolicitacoesPendentes(new ArrayList<SolicitacaoMedicamentoUnidade>(cg.consulta(sb, null)));
	}
	
	public void quantidadeSolicitacoesPendentes() {
		StringBuilder sb = new StringBuilder("select coalesce(count(o), 0) from SolicitacaoMedicamentoUnidade o where o.statusDispensacao = 'P'");
		ConsultaGeral<Long> cg = new ConsultaGeral<Long>();
		SolicitacaoMedicamentoUnidadeRaiz.getInstanciaAtual().setQtdSolicitacoesPendentes(cg.consultaUnica(sb, null));
	}
	
	public SolicitacaoMedicamentoUnidade solicitacaoId(int id){
		return new ConsultaGeral<SolicitacaoMedicamentoUnidade>().consultaUnica(new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where o.idSolicitacaoMedicamentoUnidade = "+id));
	}

}