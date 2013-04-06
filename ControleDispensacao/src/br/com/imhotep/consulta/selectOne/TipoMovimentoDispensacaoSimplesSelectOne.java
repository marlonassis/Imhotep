package br.com.imhotep.consulta.selectOne;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.TipoMovimento;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="tipoMovimentoDispensacaoSimplesSelectOne")
@RequestScoped
public class TipoMovimentoDispensacaoSimplesSelectOne extends ConsultaGeral<TipoMovimento> {
	
	public List<TipoMovimento> getItens(){
		StringBuilder stringB = new StringBuilder("select o from TipoMovimento o where o.idTipoMovimento = 21 or o.idTipoMovimento = 22");
		return new ArrayList<TipoMovimento>(super.consulta(stringB, null));
	}
	
}
