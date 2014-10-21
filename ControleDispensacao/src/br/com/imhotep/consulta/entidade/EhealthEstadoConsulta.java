package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EhealthEstado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;


@ManagedBean
@SessionScoped
public class EhealthEstadoConsulta extends PadraoConsulta<EhealthEstado> {
	public EhealthEstadoConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.pais", IGUAL);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<EhealthEstado> getList() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<EhealthEstado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from EhealthEstado o where 1=1"));
		return super.getList();
	}
}
