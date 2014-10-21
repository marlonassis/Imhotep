package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.UnidadeMaterialAlmoxarifado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class UnidadeMaterialAlmoxarifadoConsulta extends PadraoConsulta<UnidadeMaterialAlmoxarifado> {
	public UnidadeMaterialAlmoxarifadoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.sigla", IGUAL);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<UnidadeMaterialAlmoxarifado> getList() {
		setConsultaGeral(new ConsultaGeral<UnidadeMaterialAlmoxarifado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from UnidadeMaterialAlmoxarifado o where 1=1"));
		return super.getList();
	}
}
