package br.com.Imhotep.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.MovimentoGeral;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="movimentoGeralConsulta")
@SessionScoped
public class MovimentoGeralConsulta extends PadraoConsulta<MovimentoGeral> {
	public MovimentoGeralConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<MovimentoGeral> getList() {
		setConsultaGeral(new ConsultaGeral<MovimentoGeral>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from MovimentoGeral o where 1=1"));
		return super.getList();
	}
}
