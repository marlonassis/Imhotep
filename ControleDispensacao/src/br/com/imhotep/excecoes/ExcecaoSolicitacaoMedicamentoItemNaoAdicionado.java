/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSolicitacaoMedicamentoItemNaoAdicionado extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoSolicitacaoMedicamentoItemNaoAdicionado(){
		super.mensagem("O item n‹o foi adicionado", "");
	}
	
}
