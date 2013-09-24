package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.DevolucaoMedicamento;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class DevolucaoMedicamentoConsulta extends PadraoConsulta<DevolucaoMedicamento> {
	public DevolucaoMedicamentoConsulta(){
		getCamposConsulta().put("o.unidadeDevolucao", IGUAL);
		getCamposConsulta().put("o.profissionalInsercao", IGUAL);
		getCamposConsulta().put("o.dataInsercao", IGUAL);
		setOrderBy("to_ascii(o.unidadeDevolucao.nome)");
	}
	
	@Override
	public void carregarResultado() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<DevolucaoMedicamento>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where 1=1"));
		super.carregarResultado();
	}
	
}