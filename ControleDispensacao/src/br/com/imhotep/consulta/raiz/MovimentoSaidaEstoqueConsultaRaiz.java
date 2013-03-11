package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Estoque;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="movimentoSaidaEstoqueConsultaRaiz")
@RequestScoped
public class MovimentoSaidaEstoqueConsultaRaiz  extends ConsultaGeral<Estoque>{

	public boolean existeMovimentoSaida(Estoque estoque){
		List<Estoque> list = consultaEstoque(estoque);
		return list != null && !list.isEmpty();
	}
	
	private List<Estoque> consultaEstoque(Estoque estoque) {
		StringBuilder sb = new StringBuilder("select o.estoque from MovimentoLivro o where o.estoque.lote = :lote and o.tipoMovimento.tipoOperacao != 'E'");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("lote", estoque.getLote());
		
		ConsultaGeral<Estoque> cg = new ConsultaGeral<Estoque>();
		return new ArrayList<Estoque>(cg.consulta(sb, map));
	}
	
	
}
