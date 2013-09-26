/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueLockAcimaUmMinuto extends ExcecaoPadrao {

	private static final long serialVersionUID = 7270945906986262841L;
	

	public ExcecaoEstoqueLockAcimaUmMinuto(){
		super.mensagem("Este lote está em lock a mais de um minuto", "");
	}
	
}
