package br.com.imhotep.consulta.raiz;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.DispensacaoSimplesAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.Unidade;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class DispensacaoSimplesAlmoxarifadoConsultaRaiz  extends ConsultaGeral<DispensacaoSimplesAlmoxarifado>{

	public Double saldoTotalLiberadoMes(MaterialAlmoxarifado ma, Unidade un) {
		String sql = "select sum(o.movimentoLivroAlmoxarifado.quantidadeMovimentacao) from DispensacaoSimplesAlmoxarifado o where "
				+ "o.movimentoLivroAlmoxarifado.estoqueAlmoxarifado.materialAlmoxarifado.idMaterialAlmoxarifado = "+ma.getIdMaterialAlmoxarifado()
				+" and o.unidadeDispensada.idUnidade = " + un.getIdUnidade()
				+" and to_char(o.movimentoLivroAlmoxarifado.dataMovimento, 'YYYY-MM') = '"+new SimpleDateFormat("yyyy-MM").format(new Date())+"'";
		StringBuilder sb = new StringBuilder(sql);
		ConsultaGeral<Double> cg = new ConsultaGeral<Double>();
		Double total = cg.consultaUnica(sb, null);
		return total == null ? 0d : total.doubleValue();
	}
	
}