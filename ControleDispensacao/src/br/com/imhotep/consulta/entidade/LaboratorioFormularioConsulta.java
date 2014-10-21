package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.LaboratorioExameAnaliseFormulario;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;


@ManagedBean
@SessionScoped
public class LaboratorioFormularioConsulta extends PadraoConsulta<LaboratorioExameAnaliseFormulario> {
	public LaboratorioFormularioConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public void carregarResultado() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<LaboratorioExameAnaliseFormulario>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from LaboratorioExameAnaliseFormulario o where 1=1"));
		super.carregarResultado();
	}
}
