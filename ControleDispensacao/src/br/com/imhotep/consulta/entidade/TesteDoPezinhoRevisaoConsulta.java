package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.TesteDoPezinho;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="testeDoPezinhoRevisaoConsulta")
@SessionScoped
public class TesteDoPezinhoRevisaoConsulta extends PadraoConsulta<TesteDoPezinho> {
	public TesteDoPezinhoRevisaoConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.nome), o.lote");
	}
	
	@Override
	public List<TesteDoPezinho> getList() {
		setConsultaGeral(new ConsultaGeral<TesteDoPezinho>());
		String hql = "select o from TesteDoPezinho o where o.lote != null and (o.amostraValida = 'N' or "+ 
				 " exists (select a from TesteDoPezinhoResultado a where a.testeDoPezinho.idTesteDoPezinho = o.idTesteDoPezinho and "+ 
				 " 			((a.tipoExame.sigla = 'FN' and cast(a.resultado, float) > a.tipoExame.valor) or "+
				 " 			 (a.tipoExame.sigla = 'TN' and cast(a.resultado, float) > a.tipoExame.valor) or "+
				 " 			 (a.tipoExame.sigla = 'EH' and a.resultado != 'FA'))))";
		getConsultaGeral().setSqlConsultaSB(new StringBuilder(hql));
		return super.getList();
	}
}
