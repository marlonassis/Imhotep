package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.temp.PadraoConsulta;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@SessionScoped
public class MaterialAlmoxarifadoConsulta extends PadraoConsulta<MaterialAlmoxarifado> {
	public MaterialAlmoxarifadoConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public List<MaterialAlmoxarifado> getList() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<MaterialAlmoxarifado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from MaterialAlmoxarifado o where 1=1 "));
		return super.getList();
	}
}
