package br.com.Imhotep.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Prescricao;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="dispensacaoRealizadaConsulta")
@SessionScoped
public class DispensacaoRealizadaConsulta extends PadraoConsulta<Prescricao> {
	public DispensacaoRealizadaConsulta(){
		getCamposConsulta().put("o.paciente", IGUAL);
		getCamposConsulta().put("o.profissional", IGUAL);
		getCamposConsulta().put("o.dataInclusao", MENOR_IGUAL);
		getCamposConsulta().put("o.numero", IGUAL);
		setOrderBy("o.dataInclusao desc");
	}
	
	@Override
	public List<Prescricao> getList() {
		setConsultaGeral(new ConsultaGeral<Prescricao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Prescricao o where o.dispensado = 'S' "));
		return super.getList();
	}
}
