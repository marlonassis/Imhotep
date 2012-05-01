package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="dispensacaoConsulta")
@SessionScoped
public class DispensacaoConsulta extends PadraoConsulta<Prescricao> {
	public DispensacaoConsulta(){
		getCamposConsulta().put("o.paciente", IGUAL);
		getCamposConsulta().put("o.profissional", IGUAL);
		getCamposConsulta().put("o.dataInclusao", MENOR_IGUAL);
		getCamposConsulta().put("o.numero", IGUAL);
		setOrderBy("o.dataInclusao desc");
	}
	
	@Override
	public List<Prescricao> getList() {
		setConsultaGeral(new ConsultaGeral<Prescricao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Prescricao o where o.dispensavel = 'S' "));
		return super.getList();
	}
}
