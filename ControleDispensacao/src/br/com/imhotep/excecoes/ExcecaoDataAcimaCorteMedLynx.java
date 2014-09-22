/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDataAcimaCorteMedLynx extends ExcecaoPadrao {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoDataAcimaCorteMedLynx(){
		super.mensagem("Informe uma data posterior a maio de 2013", "");
	}
	
}
