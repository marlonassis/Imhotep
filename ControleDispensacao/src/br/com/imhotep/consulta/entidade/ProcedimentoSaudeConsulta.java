package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.ProcedimentoSaude;
import br.com.imhotep.temp.PadraoConsulta;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@SessionScoped
public class ProcedimentoSaudeConsulta extends PadraoConsulta<ProcedimentoSaude> {
	public ProcedimentoSaudeConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.codigoProcedimento", INCLUINDO_TUDO);
		setOrderBy("o.nome");
	}
	
	@Override
	public List<ProcedimentoSaude> getList() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<ProcedimentoSaude>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from ProcedimentoSaude o where 1=1"));
		return super.getList();
	}
}
