package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Especialidade;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="especialidadeConsulta")
@SessionScoped
public class EspecialidadeConsulta extends PadraoConsulta<Especialidade> {
	public EspecialidadeConsulta(){
		getCamposConsulta().put("o.descricao", IGUAL);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<Especialidade> getList() {
		setConsultaGeral(new ConsultaGeral<Especialidade>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Especialidade o where 1=1"));
		return super.getList();
	}
}
