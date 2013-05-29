package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class SolicitacaoMedicamentoUnidadeConsulta extends PadraoConsulta<SolicitacaoMedicamentoUnidade> {
	public SolicitacaoMedicamentoUnidadeConsulta(){
		getCamposConsulta().put("o.material", IGUAL);
		getCamposConsulta().put("o.unidade", IGUAL);
		getCamposConsulta().put("o.profissionalInsercao", IGUAL);
		setOrderBy("to_ascii(o.unidade.nome), to_ascii(o.material.descricao)");
	}
	
	@Override
	public List<SolicitacaoMedicamentoUnidade> getList() {
		setConsultaGeral(new ConsultaGeral<SolicitacaoMedicamentoUnidade>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where 1=1"));
		return super.getList();
	}
}
