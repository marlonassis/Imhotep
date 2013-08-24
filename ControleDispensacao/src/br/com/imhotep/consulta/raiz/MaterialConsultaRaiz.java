package br.com.imhotep.consulta.raiz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.extra.MaterialFaltaEstoque;
import br.com.imhotep.raiz.MaterialRaiz;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class MaterialConsultaRaiz  extends ConsultaGeral<Material>{

	public void atualizaMateriaisQuantidadeAbaixoPermitido() {
		String dataS = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
		StringBuilder stringB = new StringBuilder("select new br.com.imhotep.entidade.extra.MaterialFaltaEstoque(o.descricao, o.quantidadeMinima, sum(a.quantidadeAtual)) ");
		stringB.append("from Material o ");
		stringB.append("join o.estoques a  ");
		stringB.append("where o.quantidadeMinima is not null and o.quantidadeMinima != 0 and ");
		stringB.append("o.quantidadeMinima >=  ");
		stringB.append("(select sum(b.quantidadeAtual) from Estoque b where b.material.idMaterial = o.idMaterial ");
		stringB.append("and b.bloqueado = false and to_char(b.dataValidade, 'yyyy-MM') >= '"+dataS+"') ");
		stringB.append("and a.bloqueado = false and to_char(a.dataValidade, 'yyyy-MM') >= '"+dataS+"' ");
		stringB.append("group by o.descricao, o.quantidadeMinima ");
		stringB.append("order by to_ascii(o.descricao) ");
		
		List<MaterialFaltaEstoque> list = new ArrayList<MaterialFaltaEstoque>(new ConsultaGeral<MaterialFaltaEstoque>().consulta(stringB, null));
		MaterialRaiz.getInstanciaAtual().setMateriaisAbaixoQuantidadeMinima(list);
	}
}
