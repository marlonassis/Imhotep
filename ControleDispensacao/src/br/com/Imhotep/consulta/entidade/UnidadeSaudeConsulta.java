package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.UnidadeSaude;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="unidadeSaudeConsulta")
@SessionScoped
public class UnidadeSaudeConsulta extends PadraoConsulta<UnidadeSaude> {
	public UnidadeSaudeConsulta(){
		getCamposConsulta().put("o.estabelecimento", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.estabelecimento)");
	}
	
	@Override
	public List<UnidadeSaude> getList() {
		setConsultaGeral(new ConsultaGeral<UnidadeSaude>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from UnidadeSaude o where 1=1"));
		return super.getList();
	}
}
