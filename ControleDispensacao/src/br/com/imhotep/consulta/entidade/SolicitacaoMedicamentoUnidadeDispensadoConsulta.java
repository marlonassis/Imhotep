package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.temp.PadraoConsulta;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@SessionScoped
public class SolicitacaoMedicamentoUnidadeDispensadoConsulta extends PadraoConsulta<SolicitacaoMedicamentoUnidade> {
	public SolicitacaoMedicamentoUnidadeDispensadoConsulta(){
		getCamposConsulta().put("o.unidadeDestino", IGUAL);
		getCamposConsulta().put("o.profissionalInsercao", IGUAL);
		getCamposConsulta().put("o.idSolicitacaoMedicamentoUnidade", IGUAL);
		setOrderBy("o.dataDispensacao desc, o.unidadeDestino.nome, o.profissionalInsercao.nome");
	}
	
	@Override
	public void carregarResultado() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<SolicitacaoMedicamentoUnidade>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where (o.statusDispensacao = 'D' or o.statusDispensacao = 'DP') "));
		super.carregarResultado();
	}
	

}