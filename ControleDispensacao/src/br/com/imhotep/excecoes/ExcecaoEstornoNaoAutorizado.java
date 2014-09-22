/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoNaoAutorizado extends ExcecaoPadrao {

	private static final long serialVersionUID = 8835018298919268035L;

	public ExcecaoEstornoNaoAutorizado(){
		super.mensagem("Estorno não autorizado", "Este movimento não se enquadra no critério de estorno");
	}
	
}
