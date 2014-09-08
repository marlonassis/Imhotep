package br.com.imhotep.consulta.entidade;

	import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EstornoMovimentoAlmoxarifado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class EstornoMovimentacaoAlmoxarifadoLogConsulta extends PadraoConsulta<EstornoMovimentoAlmoxarifado> {
	
	public EstornoMovimentacaoAlmoxarifadoLogConsulta(){
		setOrderBy("o.dataEstorno desc");
	}
	
	@Override
	public List<EstornoMovimentoAlmoxarifado> getList() {
		setConsultaGeral(new ConsultaGeral<EstornoMovimentoAlmoxarifado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from EstornoMovimentoAlmoxarifado o where 1=1"));
		return super.getList();
	}
	
}
	
