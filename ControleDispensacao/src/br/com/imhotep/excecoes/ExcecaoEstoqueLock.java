/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueLock extends ExcecaoPadrao {

	private static final long serialVersionUID = -5212427663942843352L;

	public ExcecaoEstoqueLock(){
		super.mensagem("N‹o foi poss’vel colocar o estoque em lock", "Tente novamente");
	}
	
}
