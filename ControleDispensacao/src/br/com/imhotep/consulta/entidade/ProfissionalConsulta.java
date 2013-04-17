package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Profissional;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="profissionalConsulta")
@SessionScoped
public class ProfissionalConsulta extends PadraoConsulta<Profissional> {
	public ProfissionalConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.especialidade", IGUAL);
		getCamposConsulta().put("o.especialidade.especialidadePai", IGUAL);
		getCamposConsulta().put("o.matricula", IGUAL);
		setOrderBy("to_ascii(o.nome), to_ascii(o.especialidade.descricao)");
	}
	
	@Override
	public List<Profissional> getList() {
		setConsultaGeral(new ConsultaGeral<Profissional>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Profissional o where 1=1"));
		return super.getList();
	}
}
