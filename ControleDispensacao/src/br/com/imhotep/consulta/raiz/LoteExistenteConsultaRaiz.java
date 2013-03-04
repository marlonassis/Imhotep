package br.com.imhotep.consulta.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.remendo.ConsultaGeral;

@ManagedBean(name="loteExistenteConsultaRaiz")
@RequestScoped
public class LoteExistenteConsultaRaiz extends ConsultaGeral<String> {
	
	public boolean consultar(String string){
		StringBuilder stringB = new StringBuilder("select o.lote from Estoque o where lower(to_ascii(o.lote)) = lower(to_ascii('"+string+"'))");
		String lote = super.consultaUnica(stringB, null);
		return lote != null && !lote.isEmpty();
	}
	
}
