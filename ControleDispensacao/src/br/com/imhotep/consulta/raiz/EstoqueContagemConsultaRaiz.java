package br.com.imhotep.consulta.raiz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.EstoqueContagem;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EstoqueContagemConsultaRaiz  extends ConsultaGeral<EstoqueContagem>{
	
	public List<EstoqueContagem> consultarEstoquesContagem() {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		String hql = "select o from EstoqueContagem o where o.estoque.quantidadeAtual > 0 and "
				+ "o.estoque.bloqueado = false and to_char(o.estoque.dataValidade, 'yyyy-MM') >= '"+dataS+"' and "
				+"o.dataCadastro = (select max(a.dataCadastro) from EstoqueContagem a where a.estoque.idEstoque = "
				+ "o.estoque.idEstoque) "
						+ " order by to_ascii(o.estoque.material.descricao)";
		List<EstoqueContagem> list = new ArrayList<EstoqueContagem>(new ConsultaGeral<EstoqueContagem>().
				consulta(new StringBuilder(hql), null));
		return list;
	}
	
}
