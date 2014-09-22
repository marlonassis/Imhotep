/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoApagarLoteExisteNotaFiscal extends ExcecaoPadrao {
	
	private static final long serialVersionUID = -8613649925406324742L;

	public ExcecaoApagarLoteExisteNotaFiscal(){
		super.mensagem("N‹o foi poss’vel apagar o lote.", "Existem notas fiscais associadas ao lote.");
	}
	
}
