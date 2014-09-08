package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EhealthPais;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;


@ManagedBean
@SessionScoped
public class EhealthPaisConsulta extends PadraoConsulta<EhealthPais> {
	public EhealthPaisConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.sigla", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public List<EhealthPais> getList() {
		setPesquisaGuiada(false);
		setConsultaGeral(new ConsultaGeral<EhealthPais>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from EhealthPais o where 1=1"));
		return super.getList();
	}
}
