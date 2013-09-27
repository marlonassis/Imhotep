package br.com.imhotep.consulta.selectOne;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class TipoMovimentoAlmoxarifadoSelectOne extends ConsultaGeral<TipoMovimentoAlmoxarifado> {
	
	public List<TipoMovimentoAlmoxarifado> getItens(){
		StringBuilder stringB = new StringBuilder("select o from TipoMovimentoAlmoxarifado o where o.idTipoMovimentoAlmoxarifado = 3 or o.idTipoMovimentoAlmoxarifado = 4 or o.idTipoMovimentoAlmoxarifado = 5");
		return new ArrayList<TipoMovimentoAlmoxarifado>(super.consulta(stringB, null));
	}
	
}
