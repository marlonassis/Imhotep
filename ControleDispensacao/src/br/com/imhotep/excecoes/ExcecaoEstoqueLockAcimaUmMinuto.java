/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueLockAcimaUmMinuto extends ExcecaoPadrao {

	private static final long serialVersionUID = 7270945906986262841L;
	

	public ExcecaoEstoqueLockAcimaUmMinuto(String lote){
		if(lote != null && !lote.isEmpty())
			super.mensagem("O lote "+lote+" est‡ em lock a mais de um minuto", "");
		else
			super.mensagem("O lote est‡ em lock a mais de um minuto", "");
	}
	
}
