/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoNaoAutorizado extends ExcecaoPadrao {

	private static final long serialVersionUID = 8835018298919268035L;

	public ExcecaoEstornoNaoAutorizado(){
		super.mensagem("Estorno n�o autorizado", "Este movimento n�o se enquadra no crit�rio de estorno");
	}
	
}
