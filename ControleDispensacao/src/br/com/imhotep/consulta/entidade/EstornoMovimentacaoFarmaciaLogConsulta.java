package br.com.imhotep.consulta.entidade;

	import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EstornoMovimentoFarmacia;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class EstornoMovimentacaoFarmaciaLogConsulta extends PadraoConsulta<EstornoMovimentoFarmacia> {
	
	public EstornoMovimentacaoFarmaciaLogConsulta(){
		setOrderBy("o.dataEstorno desc");
	}
	
	@Override
	public List<EstornoMovimentoFarmacia> getList() {
		setConsultaGeral(new ConsultaGeral<EstornoMovimentoFarmacia>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from EstornoMovimentoFarmacia o where 1=1"));
		return super.getList();
	}
	
}
	
