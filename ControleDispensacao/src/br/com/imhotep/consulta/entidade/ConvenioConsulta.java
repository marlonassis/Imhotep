package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Convenio;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class ConvenioConsulta extends PadraoConsulta<Convenio> {
	public ConvenioConsulta(){
		getCamposConsulta().put("o.sigla", INCLUINDO_TUDO);
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<Convenio> getList() {
		setConsultaGeral(new ConsultaGeral<Convenio>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Convenio o where 1=1"));
		return super.getList();
	}
}
