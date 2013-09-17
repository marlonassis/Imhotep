package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.FabricanteAlmoxarifado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class FabricanteAlmoxarifadoConsulta extends PadraoConsulta<FabricanteAlmoxarifado> {
	public FabricanteAlmoxarifadoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(lower(o.descricao))");
	}
	
	@Override
	public List<FabricanteAlmoxarifado> getList() {
		setConsultaGeral(new ConsultaGeral<FabricanteAlmoxarifado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from FabricanteAlmoxarifado o where 1=1"));
		return super.getList();
	}
}
