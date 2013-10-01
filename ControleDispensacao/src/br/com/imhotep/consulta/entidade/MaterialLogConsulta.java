package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.MaterialLog;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class MaterialLogConsulta extends PadraoConsulta<MaterialLog> {
	public MaterialLogConsulta(){
		getCamposConsulta().put("o.material", IGUAL);
		getCamposConsulta().put("o.dataLog", IGUAL);
		getCamposConsulta().put("o.profissionalAlteracao", IGUAL);
		getCamposConsulta().put("o.tipoLog", IGUAL);
		setOrderBy("o.dataLog, o.tipoLog");
	}
	
	@Override
	public void carregarResultado() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<MaterialLog>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from MaterialLog o where 1=1 "));
		super.carregarResultado();
	}
	
}
