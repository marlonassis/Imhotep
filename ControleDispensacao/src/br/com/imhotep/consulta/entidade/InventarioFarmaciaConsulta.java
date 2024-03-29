package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.InventarioFarmacia;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class InventarioFarmaciaConsulta extends PadraoConsulta<InventarioFarmacia> {
	public InventarioFarmaciaConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		getCamposConsulta().put("o.dataInicio", MAIOR_IGUAL);
		setOrderBy("o.dataInicio desc");
	}
	
	@Override
	public List<InventarioFarmacia> getList() {
		setPesquisaGuiada(false);
		setConsultaGeral(new ConsultaGeral<InventarioFarmacia>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from InventarioFarmacia o where 1=1"));
		return super.getList();
	}
	
}
