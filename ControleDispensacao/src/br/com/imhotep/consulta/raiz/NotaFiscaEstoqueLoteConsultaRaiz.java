package br.com.imhotep.consulta.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.NotaFiscalEstoque;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="notaFiscaEstoqueLoteConsultaRaiz")
@RequestScoped
public class NotaFiscaEstoqueLoteConsultaRaiz extends ConsultaGeral<NotaFiscalEstoque> {
	
	public NotaFiscalEstoque consultar(String string){
		String sql = "select o from NotaFiscalEstoque o where lower(to_ascii(o.estoque.lote)) = lower(to_ascii('"+string+"'))";
		StringBuilder stringB = new StringBuilder(sql);
		return super.consultaUnica(stringB, null);
	}
	
}
