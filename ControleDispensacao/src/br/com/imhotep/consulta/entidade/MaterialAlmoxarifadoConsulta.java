package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class MaterialAlmoxarifadoConsulta extends PadraoConsulta<MaterialAlmoxarifado> {
	public MaterialAlmoxarifadoConsulta(){
		getCamposConsulta().put("o.idMaterialAlmoxarifado", IGUAL);
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("to_ascii(o.descricao)");
	}
	
	@Override
	public void carregarResultado() {
		setConsultaGeral(new ConsultaGeral<MaterialAlmoxarifado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from MaterialAlmoxarifado o where 1=1 "));
		super.carregarResultado();
	}
	
}
