package br.com.imhotep.consulta.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.NotaFiscal;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class NotaFiscalConsultaRaiz  extends ConsultaGeral<NotaFiscal>{

	public NotaFiscal consultar(int idNotaFiscal) {
		StringBuilder sb = new StringBuilder("select o from NotaFiscal o where o.idNotaFiscal = "+idNotaFiscal);
		return new ConsultaGeral<NotaFiscal>().consultaUnica(sb);
	}
	
}
