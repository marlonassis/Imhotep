/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSolicitacaoItemInseridaDuasVezes extends ExcecaoPadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExcecaoSolicitacaoItemInseridaDuasVezes(){
		super.mensagem("Item j‡ inserido", null);
	}
	
	
}
