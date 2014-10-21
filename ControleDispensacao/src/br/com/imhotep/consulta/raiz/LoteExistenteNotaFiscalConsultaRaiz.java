package br.com.imhotep.consulta.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.NotaFiscal;
import br.com.remendo.ConsultaGeral;

/**
 * Classe que retorna o estoque que est‡ na nota fiscal da sess‹o atual do usu‡rio
 * @author marlonassis
 *
 */

@ManagedBean(name="loteExistenteNotaFiscalConsultaRaiz")
@RequestScoped
public class LoteExistenteNotaFiscalConsultaRaiz extends ConsultaGeral<Estoque> {
	
	public Estoque consultar(String lote, NotaFiscal notaFiscal){
		if(lote != null)
			lote = lote.trim();
		StringBuilder stringB = new StringBuilder("select b.estoque from NotaFiscalEstoque b where b.notaFiscal.idNotaFiscal = "+notaFiscal.getIdNotaFiscal()+" and b.estoque.lote = '"+lote+"'");
		return super.consultaUnica(stringB, null);
	}
	
}
