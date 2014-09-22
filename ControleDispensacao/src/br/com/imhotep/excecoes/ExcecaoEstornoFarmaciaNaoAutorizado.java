/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoFarmaciaNaoAutorizado extends ExcecaoPadrao {

	private static final long serialVersionUID = -2851590635926894380L;

	public ExcecaoEstornoFarmaciaNaoAutorizado(){
		super.mensagem("Estorno n�o autorizado", "Este movimento n�o se enquadra no crit�rio de estorno");
	}
	
}
