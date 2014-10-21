package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.SubGrupoAlmoxarifado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class SubGrupoAlmoxarifadoConsulta extends PadraoConsulta<SubGrupoAlmoxarifado> {
	public SubGrupoAlmoxarifadoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.grupoAlmoxarifado", IGUAL);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<SubGrupoAlmoxarifado> getList() {
		setConsultaGeral(new ConsultaGeral<SubGrupoAlmoxarifado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SubGrupoAlmoxarifado o where 1=1"));
		return super.getList();
	}
}
