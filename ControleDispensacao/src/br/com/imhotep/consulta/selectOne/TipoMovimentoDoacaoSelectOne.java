package br.com.imhotep.consulta.selectOne;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.TipoMovimento;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="tipoMovimentoDoacaoSelectOne")
@RequestScoped
public class TipoMovimentoDoacaoSelectOne extends ConsultaGeral<TipoMovimento> {
	
	public List<TipoMovimento> getItens(){
		StringBuilder stringB = new StringBuilder("select o from TipoMovimento o where lower(to_ascii(o.descricao)) like lower(to_ascii('%Doação%'))");
		return new ArrayList<TipoMovimento>(super.consulta(stringB, null));
	}
	
}
