/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoUsuarioLogin extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoUsuarioLogin(){
		super.mensagem("Usu‡rio e/ou senha n‹o confere", null);
	}
	
}
