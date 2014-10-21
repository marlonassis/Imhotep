/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoUsuarioDuplicado extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoUsuarioDuplicado(){
		super.mensagem("Este usu‡rio j‡ foi escolhido", "Informe outro usu‡rio");
	}
	
}
