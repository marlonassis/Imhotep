/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoForaRedeHU extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoForaRedeHU(){
		super.mensagem("N�o � permitido realizar esta opera��o fora do HU", "");
	}
	
}
