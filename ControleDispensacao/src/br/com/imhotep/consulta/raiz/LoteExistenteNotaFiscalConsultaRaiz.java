package br.com.imhotep.consulta.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.NotaFiscal;
import br.com.remendo.ConsultaGeral;

/**
 * Classe que retorna o estoque que está na nota fiscal da sessão atual do usuário
 * @author marlonassis
 *
 */

@ManagedBean(name="loteExistenteNotaFiscalConsultaRaiz")
@RequestScoped
public class LoteExistenteNotaFiscalConsultaRaiz extends ConsultaGeral<Estoque> {
	
	public Estoque consultar(String lote, NotaFiscal notaFiscal){
		StringBuilder stringB = new StringBuilder("select b.estoque from NotaFiscalEstoque b where b.notaFiscal.idNotaFiscal = "+notaFiscal.getIdNotaFiscal()+" and b.estoque.lote = '"+lote+"'");
		return super.consultaUnica(stringB, null);
	}
	
}
