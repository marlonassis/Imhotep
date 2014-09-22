/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoReservaVazia extends ExcecaoPadrao {

	private static final long serialVersionUID = 1L;
	
	public ExcecaoReservaVazia(){
		super.mensagem("N‹o Ž poss’vel reservar quantidade 0", "Informe a quantidade");
	}
	
}
