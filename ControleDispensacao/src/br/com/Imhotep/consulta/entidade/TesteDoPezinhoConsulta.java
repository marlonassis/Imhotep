package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.TesteDoPezinho;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="testeDoPezinhoConsulta")
@SessionScoped
public class TesteDoPezinhoConsulta extends PadraoConsulta<TesteDoPezinho> {
	public TesteDoPezinhoConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.nome), o.lote");
	}
	
	@Override
	public List<TesteDoPezinho> getList() {
		setConsultaGeral(new ConsultaGeral<TesteDoPezinho>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from TesteDoPezinho o where 1=1"));
		return super.getList();
	}
}
