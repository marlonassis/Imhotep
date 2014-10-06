package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class SolicitacaoMedicamentoUnidadeDispensadoConsulta extends PadraoConsulta<SolicitacaoMedicamentoUnidade> {
	
	public SolicitacaoMedicamentoUnidadeDispensadoConsulta(){
		getCamposConsulta().put("o.unidadeDestino", IGUAL);
		getCamposConsulta().put("o.profissionalInsercao", IGUAL);
		getCamposConsulta().put("o.idSolicitacaoMedicamentoUnidade", IGUAL);
		getCamposConsulta().put("o.dataDispensacao", IGUAL_DATA);
		setOrderBy("o.dataDispensacao desc, o.unidadeDestino.nome, o.profissionalInsercao.nome");
	}
	
	@Override
	public void carregarResultado() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<SolicitacaoMedicamentoUnidade>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where 1=1 "));
		super.carregarResultado();
	}

}