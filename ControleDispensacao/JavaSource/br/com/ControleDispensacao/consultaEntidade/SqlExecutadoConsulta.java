package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.SqlExecutado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="sqlExecutadoConsulta")
@SessionScoped
public class SqlExecutadoConsulta extends PadraoConsulta<SqlExecutado> {
	public SqlExecutadoConsulta(){
		setOrderBy("o.dataCriacao desc");
	}
	
	@Override
	public List<SqlExecutado> getList() {
		setConsultaGeral(new ConsultaGeral<SqlExecutado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SqlExecutado o where 1=1"));
		return super.getList();
	}
}
