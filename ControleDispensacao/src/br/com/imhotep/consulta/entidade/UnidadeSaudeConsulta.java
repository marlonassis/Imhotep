package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.UnidadeSaude;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class UnidadeSaudeConsulta extends PadraoConsulta<UnidadeSaude> {
	public UnidadeSaudeConsulta(){
		getCamposConsulta().put("o.estabelecimento", INCLUINDO_TUDO);
		getCamposConsulta().put("o.tipoUnidade", IGUAL);
		getCamposConsulta().put("o.cidade", IGUAL);
		setOrderBy("to_ascii(o.estabelecimento)");
	}
	
	@Override
	public List<UnidadeSaude> getList() {
		setConsultaGeral(new ConsultaGeral<UnidadeSaude>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from UnidadeSaude o where 1=1"));
		return super.getList();
	}
}
