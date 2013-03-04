package br.com.imhotep.consulta.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Estoque;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="loteExistenteConsultaRaiz")
@RequestScoped
public class EstoqueLoteConsultaRaiz extends ConsultaGeral<Estoque> {
	
	public Estoque consultar(String string){
		String sql = "select o from Estoque o where lower(to_ascii(o.lote)) = lower(to_ascii('"+string+"'))";
		StringBuilder stringB = new StringBuilder(sql);
		return super.consultaUnica(stringB, null);
	}
	
}