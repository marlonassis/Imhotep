package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.NotaFiscalEstoque;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="notaFiscalEstoqueConsultaRaiz")
@RequestScoped
public class NotaFiscalEstoqueConsultaRaiz  extends ConsultaGeral<NotaFiscalEstoque>{

	public boolean existeNotaFiscao(Estoque estoque){
		List<NotaFiscalEstoque> list = consultaEstoque(estoque);
		return list != null && !list.isEmpty();
	}
	
	private List<NotaFiscalEstoque> consultaEstoque(Estoque estoque) {
		StringBuilder sb = new StringBuilder("select o from NotaFiscalEstoque o where o.movimentoLivro.estoque.lote = :lote");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("lote", estoque.getLote());
		
		ConsultaGeral<NotaFiscalEstoque> cg = new ConsultaGeral<NotaFiscalEstoque>();
		return new ArrayList<NotaFiscalEstoque>(cg.consulta(sb, map));
	}
	
	
}
