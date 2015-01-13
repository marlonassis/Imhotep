package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.InventarioAlmoxarifado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class InventarioAlmoxarifadoConsulta extends PadraoConsulta<InventarioAlmoxarifado> {
	public InventarioAlmoxarifadoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.dataInicio", MAIOR_IGUAL);
		setOrderBy("o.dataInicio desc");
	}
	
	@Override
	public List<InventarioAlmoxarifado> getList() {
		setPesquisaGuiada(false);
		setConsultaGeral(new ConsultaGeral<InventarioAlmoxarifado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from InventarioAlmoxarifado o where 1=1"));
		return super.getList();
	}
	
}
