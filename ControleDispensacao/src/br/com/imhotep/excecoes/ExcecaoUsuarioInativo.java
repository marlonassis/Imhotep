/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoUsuarioInativo extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoUsuarioInativo(){
		super.mensagem("Usu‡rio inativo", null);
	}
	
}
