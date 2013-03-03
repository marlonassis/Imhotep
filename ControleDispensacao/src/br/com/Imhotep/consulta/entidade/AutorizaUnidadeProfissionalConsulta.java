package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.AutorizaUnidadeProfissional;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="autorizaUnidadeProfissionalConsulta")
@SessionScoped
public class AutorizaUnidadeProfissionalConsulta extends PadraoConsulta<AutorizaUnidadeProfissional> {
	public AutorizaUnidadeProfissionalConsulta(){
		getCamposConsulta().put("o.unidade", IGUAL);
		getCamposConsulta().put("o.profissional", IGUAL);
		setOrderBy("to_ascii(o.unidade.nome), to_ascii(o.profissional.nome)");
	}
	
	@Override
	public List<AutorizaUnidadeProfissional> getList() {
		setConsultaGeral(new ConsultaGeral<AutorizaUnidadeProfissional>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from AutorizaUnidadeProfissional o where 1=1"));
		return super.getList();
	}
}
