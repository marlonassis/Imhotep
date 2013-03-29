package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Fabricante;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="fabricanteConsulta")
@SessionScoped
public class FabricanteConsulta extends PadraoConsulta<Fabricante> {
	public FabricanteConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(lower(o.descricao))");
	}
	
	@Override
	public List<Fabricante> getList() {
		setConsultaGeral(new ConsultaGeral<Fabricante>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Fabricante o where 1=1"));
		return super.getList();
	}
}
