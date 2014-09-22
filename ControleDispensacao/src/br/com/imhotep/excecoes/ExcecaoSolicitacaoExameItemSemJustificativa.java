/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSolicitacaoExameItemSemJustificativa extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3114739907470314277L;

	public ExcecaoSolicitacaoExameItemSemJustificativa(){
		super();
		super.mensagem("Existe um ou mais itens solicitados sem justificativa", null);
	}
	
}
