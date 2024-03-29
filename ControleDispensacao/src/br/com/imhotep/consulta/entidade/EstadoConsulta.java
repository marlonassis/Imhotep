package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Estado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="estadoConsulta")
@SessionScoped
public class EstadoConsulta extends PadraoConsulta<Estado> {
	public EstadoConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.unidadeFederativa", INCLUINDO_TUDO);
		setOrderBy("to_ascii(lower(o.nome))");
	}
	
	@Override
	public List<Estado> getList() {
		setConsultaGeral(new ConsultaGeral<Estado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Estado o where 1=1"));
		return super.getList();
	}
}
