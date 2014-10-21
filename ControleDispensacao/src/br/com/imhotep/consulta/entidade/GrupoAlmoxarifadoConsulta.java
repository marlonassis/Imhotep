package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.GrupoAlmoxarifado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class GrupoAlmoxarifadoConsulta extends PadraoConsulta<GrupoAlmoxarifado> {
	public GrupoAlmoxarifadoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<GrupoAlmoxarifado> getList() {
		setConsultaGeral(new ConsultaGeral<GrupoAlmoxarifado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from GrupoAlmoxarifado o where 1=1"));
		return super.getList();
	}
}
