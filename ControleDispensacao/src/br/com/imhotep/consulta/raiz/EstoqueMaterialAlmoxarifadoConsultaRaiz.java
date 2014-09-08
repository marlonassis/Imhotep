package br.com.imhotep.consulta.raiz;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@SessionScoped
public class EstoqueMaterialAlmoxarifadoConsultaRaiz extends ConsultaGeral<EstoqueAlmoxarifado> implements Serializable{

	private static final long serialVersionUID = 1L;

	public EstoqueAlmoxarifado consultarEstoqueMaterialLoteCodigoBarras(String codigo) {
		String dataS = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		StringBuilder stringB = new StringBuilder("select o from EstoqueAlmoxarifado o where ");
		stringB.append("o.bloqueado = false and (o.dataValidade >= cast('");
		stringB.append(dataS);
		stringB.append("' as date)  or o.dataValidade is null) and (lower(o.lote) = lower('");
		stringB.append(codigo);
		stringB.append("') or o.codigoBarras = '");
		stringB.append(codigo);
		stringB.append("' or o.idEstoqueAlmoxarifado = ");
		stringB.append(codigo);
		stringB.append(") order by o.dataValidade asc, to_ascii(lower(o.lote))");
		EstoqueAlmoxarifado estoque = new ConsultaGeral<EstoqueAlmoxarifado>().consultaUnica(stringB, null);
		return estoque;
	}
	
}
