/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoItemSemQuantidade extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoItemSemQuantidade(){
		super.mensagem("Informe a quantidade", "");
	}
	
}
