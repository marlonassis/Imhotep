/**
 * Criado por Márlon Assis
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
		super.mensagem("Não é permitido realizar esta operação fora do HU", "");
	}
	
}
