package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.NotaFiscalAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalEstoqueAlmoxarifado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class NotaFiscalAlmoxarifadoConsultaRaiz  extends ConsultaGeral<NotaFiscalAlmoxarifado>{

	public NotaFiscalAlmoxarifado consultarNotaFiscal(int idNotaFiscalAlmoxarifado) {
		StringBuilder sb = new StringBuilder("select o from NotaFiscalAlmoxarifado o where o.idNotaFiscalAlmoxarifado = "+idNotaFiscalAlmoxarifado);
		return new ConsultaGeral<NotaFiscalAlmoxarifado>().consultaUnica(sb);
	}
	
	public List<NotaFiscalEstoqueAlmoxarifado> getItensNotaFiscal(NotaFiscalAlmoxarifado notaFiscal){
		String hql = "select o from NotaFiscalEstoqueAlmoxarifado o where o.notaFiscalAlmoxarifado.idNotaFiscalAlmoxarifado="
						+notaFiscal.getIdNotaFiscalAlmoxarifado();
		Collection<NotaFiscalEstoqueAlmoxarifado> consulta = new ConsultaGeral<NotaFiscalEstoqueAlmoxarifado>().consulta(new StringBuilder(hql), null);
		return new ArrayList<NotaFiscalEstoqueAlmoxarifado>(consulta);
	}
}
