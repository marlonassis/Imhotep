package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.TipoExame;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="tipoExameConsulta")
@SessionScoped
public class TipoExameConsulta extends PadraoConsulta<TipoExame> {
	public TipoExameConsulta(){
		getCamposConsulta().put("o.nome", IGUAL);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<TipoExame> getList() {
		setConsultaGeral(new ConsultaGeral<TipoExame>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from TipoExame o where 1=1"));
		return super.getList();
	}
}
