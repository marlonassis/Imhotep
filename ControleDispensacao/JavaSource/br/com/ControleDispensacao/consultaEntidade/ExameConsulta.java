package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Exame;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="exameConsulta")
@SessionScoped
public class ExameConsulta extends PadraoConsulta<Exame> {
	public ExameConsulta(){
		getCamposConsulta().put("o.nome", IGUAL);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<Exame> getList() {
		setConsultaGeral(new ConsultaGeral<Exame>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Exame o where 1=1"));
		return super.getList();
	}
}
