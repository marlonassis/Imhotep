package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Especialidade;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="especialidadeConsulta")
@SessionScoped
public class EspecialidadeConsulta extends PadraoConsulta<Especialidade> {
	public EspecialidadeConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("o.tipoConselho, to_ascii(o.descricao)");
	}
	
	@Override
	public List<Especialidade> getList() {
		setConsultaGeral(new ConsultaGeral<Especialidade>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Especialidade o where 1=1"));
		return super.getList();
	}
}
