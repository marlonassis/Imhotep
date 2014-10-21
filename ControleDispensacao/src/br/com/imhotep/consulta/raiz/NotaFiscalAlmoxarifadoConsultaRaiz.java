package br.com.imhotep.consulta.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.NotaFiscalAlmoxarifado;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class NotaFiscalAlmoxarifadoConsultaRaiz  extends ConsultaGeral<NotaFiscalAlmoxarifado>{

	public NotaFiscalAlmoxarifado consultar(int idNotaFiscalAlmoxarifado) {
		StringBuilder sb = new StringBuilder("select o from NotaFiscalAlmoxarifado o where o.idNotaFiscalAlmoxarifado = "+idNotaFiscalAlmoxarifado);
		return new ConsultaGeral<NotaFiscalAlmoxarifado>().consultaUnica(sb);
	}
	
}
