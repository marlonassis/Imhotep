package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.InventarioFarmacia;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class InventarioFarmaciaConsulta extends PadraoConsulta<InventarioFarmacia> {
	public InventarioFarmaciaConsulta(){
		getCamposConsulta().put("o.lote", INCLUINDO_TUDO);
		getCamposConsulta().put("o.material", IGUAL);
		setOrderBy("to_ascii(lower(o.material.descricao))");
	}
	
	@Override
	public void carregarResultado() {
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<InventarioFarmacia>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from InventarioFarmacia o where 1=1"));
		super.carregarResultado();
	}
	
}
