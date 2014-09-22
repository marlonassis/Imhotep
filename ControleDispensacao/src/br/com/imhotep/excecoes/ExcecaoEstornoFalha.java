/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoFalha extends ExcecaoPadrao {

	private static final long serialVersionUID = -2636527316681987302L;

	public ExcecaoEstornoFalha(){
		super.mensagem("Falha ao estornar", "");
	}
	
}
