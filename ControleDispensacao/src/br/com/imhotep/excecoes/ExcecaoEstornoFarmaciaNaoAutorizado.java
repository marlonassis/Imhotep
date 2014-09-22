/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoFarmaciaNaoAutorizado extends ExcecaoPadrao {

	private static final long serialVersionUID = -2851590635926894380L;

	public ExcecaoEstornoFarmaciaNaoAutorizado(){
		super.mensagem("Estorno não autorizado", "Este movimento não se enquadra no critério de estorno");
	}
	
}
