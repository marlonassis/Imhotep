package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Doacao;
import br.com.imhotep.entidade.Estoque;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class DoacaoEstoqueConsultaRaiz  extends ConsultaGeral<Doacao>{

	public boolean existeDoacao(Estoque estoque){
		List<Doacao> list = consultaEstoque(estoque);
		return list != null && !list.isEmpty();
	}
	
	private List<Doacao> consultaEstoque(Estoque estoque) {
		StringBuilder sb = new StringBuilder("select o from Doacao o where o.movimentoLivro.estoque.lote = :lote");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("lote", estoque.getLote());
		
		ConsultaGeral<Doacao> cg = new ConsultaGeral<Doacao>();
		return new ArrayList<Doacao>(cg.consulta(sb, map));
	}
	
	
}
