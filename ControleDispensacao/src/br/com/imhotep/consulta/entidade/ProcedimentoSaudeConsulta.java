package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.ProcedimentoSaude;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class ProcedimentoSaudeConsulta extends PadraoConsulta<ProcedimentoSaude> {
	public ProcedimentoSaudeConsulta(){
		getCamposConsulta().put("o.nome", IGUAL);
		setOrderBy("o.nome");
	}
	
	@Override
	public List<ProcedimentoSaude> getList() {
		setConsultaGeral(new ConsultaGeral<ProcedimentoSaude>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from ProcedimentoSaude o where 1=1"));
		return super.getList();
	}
}
