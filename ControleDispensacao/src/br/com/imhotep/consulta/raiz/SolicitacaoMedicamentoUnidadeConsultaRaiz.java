package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.raiz.SolicitacaoMedicamentoUnidadeRaiz;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class SolicitacaoMedicamentoUnidadeConsultaRaiz  extends ConsultaGeral<SolicitacaoMedicamentoUnidade>{

	public void listaSolicitaçõesSemReceptor(){
		StringBuilder sb = new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where o.statusDispensacao = 'D' and o.profissionalReceptor is null");
		ConsultaGeral<SolicitacaoMedicamentoUnidade> cg = new ConsultaGeral<SolicitacaoMedicamentoUnidade>();
		SolicitacaoMedicamentoUnidadeRaiz.getInstanciaAtual().setSolicitacoesPendentes(new ArrayList<SolicitacaoMedicamentoUnidade>(cg.consulta(sb, null)));
	}
 	
	public void consultarSolicitacoesPendentes() {
		StringBuilder sb = new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where o.statusDispensacao = 'P'");
		ConsultaGeral<SolicitacaoMedicamentoUnidade> cg = new ConsultaGeral<SolicitacaoMedicamentoUnidade>();
		SolicitacaoMedicamentoUnidadeRaiz.getInstanciaAtual().setSolicitacoesPendentes(new ArrayList<SolicitacaoMedicamentoUnidade>(cg.consulta(sb, null)));
	}
	
	public SolicitacaoMedicamentoUnidade solicitacaoId(int id){
		return new ConsultaGeral<SolicitacaoMedicamentoUnidade>().consultaUnica(new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where o.idSolicitacaoMedicamentoUnidade = "+id));
	}

	public Long quantidadeItens(SolicitacaoMedicamentoUnidade smu){ 
		String sql = "select count(o.idSolicitacaoMedicamentoUnidadeItem) from SolicitacaoMedicamentoUnidadeItem o "+ 
					 "where o.solicitacaoMedicamentoUnidade.idSolicitacaoMedicamentoUnidade = "+smu.getIdSolicitacaoMedicamentoUnidade();
		return new ConsultaGeral<Long>(new StringBuilder(sql)).consultaUnica();
	}
	
	/**
	 * Este método retorna apenas os últimos 10 pedidos do profissional
	 */
	public void consultarSolicitacoesProfissional(){ 
		String sql;
		try {
			sql = "select o from SolicitacaoMedicamentoUnidade o "+ 
						 "where o.profissionalInsercao.idProfissional = "+Autenticador.getProfissionalLogado().getIdProfissional() +
						 " order by o.idSolicitacaoMedicamentoUnidade desc, o.statusDispensacao, o.dataFechamento, o.dataInsercao";
			ConsultaGeral<SolicitacaoMedicamentoUnidade> consultaGeral = new ConsultaGeral<SolicitacaoMedicamentoUnidade>(new StringBuilder(sql));
			consultaGeral.setMaximoResultados(10);
			Collection<SolicitacaoMedicamentoUnidade> consulta = consultaGeral.consulta();
			List<SolicitacaoMedicamentoUnidade> solicitacoesPendentesProfissional =  new ArrayList<SolicitacaoMedicamentoUnidade>(consulta);
			SolicitacaoMedicamentoUnidadeRaiz.getInstanciaAtual().setSolicitacoesProfissional(solicitacoesPendentesProfissional);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
}