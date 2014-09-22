/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoApagarLoteExisteMovimentoSaida extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoApagarLoteExisteMovimentoSaida(){
		super.mensagem("N‹o foi poss’vel apagar o lote.", "Existe(m) movimento(s) de sa’da associado(s) ao lote.");
	}
	
}
