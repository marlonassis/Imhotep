package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidade;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.raiz.SolicitacaoMaterialAlmoxarifadoUnidadeRaiz;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class SolicitacaoMaterialAlmoxarifadoUnidadeConsultaRaiz  extends ConsultaGeral<SolicitacaoMaterialAlmoxarifadoUnidade>{

	public void consultarSolicitacoesPendentes() {
		StringBuilder sb = new StringBuilder("select o from SolicitacaoMaterialAlmoxarifadoUnidade o where o.statusDispensacao = 'P' order by o.dataFechamento asc ");
		ConsultaGeral<SolicitacaoMaterialAlmoxarifadoUnidade> cg = new ConsultaGeral<SolicitacaoMaterialAlmoxarifadoUnidade>();
		SolicitacaoMaterialAlmoxarifadoUnidadeRaiz.getInstanciaAtual().setListaSolicitacoesPendentes(new ArrayList<SolicitacaoMaterialAlmoxarifadoUnidade>(cg.consulta(sb, null)));
	}
	
	public SolicitacaoMaterialAlmoxarifadoUnidade consultarSolicitacao(Integer id){
		if(id != null){
			StringBuilder sb = new StringBuilder("select o from SolicitacaoMaterialAlmoxarifadoUnidade o where o.idSolicitacaoMaterialAlmoxarifadoUnidade = "+id);
			return new ConsultaGeral<SolicitacaoMaterialAlmoxarifadoUnidade>().consultaUnica(sb);
		}
		return null;
	}
	
	/**
	 * Este método retorna apenas os últimos 10 pedidos do profissional
	 */
	public void consultarSolicitacoesProfissional(){ 
		String sql;
		try {
			sql = "select o from SolicitacaoMaterialAlmoxarifadoUnidade o "+ 
						 "where o.profissionalInsercao.idProfissional = "+Autenticador.getProfissionalLogado().getIdProfissional() +
						 " order by o.idSolicitacaoMaterialAlmoxarifadoUnidade desc, o.statusDispensacao, o.dataFechamento, o.dataInsercao";
			ConsultaGeral<SolicitacaoMaterialAlmoxarifadoUnidade> consultaGeral = new ConsultaGeral<SolicitacaoMaterialAlmoxarifadoUnidade>(new StringBuilder(sql));
			consultaGeral.setMaximoResultados(10);
			Collection<SolicitacaoMaterialAlmoxarifadoUnidade> consulta = consultaGeral.consulta();
			List<SolicitacaoMaterialAlmoxarifadoUnidade> solicitacoesPendentesProfissional =  new ArrayList<SolicitacaoMaterialAlmoxarifadoUnidade>(consulta);
			SolicitacaoMaterialAlmoxarifadoUnidadeRaiz.getInstanciaAtual().setSolicitacoesProfissional(solicitacoesPendentesProfissional);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
}