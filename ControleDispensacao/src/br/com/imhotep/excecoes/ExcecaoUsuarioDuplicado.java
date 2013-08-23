/**
 * Criado por Márlon Assis
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
		super.mensagem("Este usuário já foi escolhido", "Informe outro usuário");
	}
	
}
