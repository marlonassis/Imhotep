package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.LaboratorioExameAutorizaProfissional;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class ControleAcessoFaseExameConsulta extends PadraoConsulta<LaboratorioExameAutorizaProfissional> {
	public ControleAcessoFaseExameConsulta(){
		getCamposConsulta().put("o.profissional", INCLUINDO_TUDO);
		getCamposConsulta().put("o.fase", IGUAL);
		setOrderBy("to_ascii(o.profissional.nome), to_ascii(o.fase)");
	}
	
	@Override
	public void carregarResultado() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<LaboratorioExameAutorizaProfissional>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from LaboratorioExameAutorizaProfissional o where 1=1"));
		super.carregarResultado();
	}
}
