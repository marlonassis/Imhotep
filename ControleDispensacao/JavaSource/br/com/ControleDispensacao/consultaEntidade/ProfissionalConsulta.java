package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Profissional;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="profissionalConsulta")
@SessionScoped
public class ProfissionalConsulta extends PadraoConsulta<Profissional> {
	public ProfissionalConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.especialidade", IGUAL);
		getCamposConsulta().put("o.inscricao", INCLUINDO_TUDO);
		setOrderBy("o.especialidade.descricao, o.nome");
	}
	
	@Override
	public List<Profissional> getList() {
		setConsultaGeral(new ConsultaGeral<Profissional>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Profissional o where 1=1"));
		return super.getList();
	}
}
