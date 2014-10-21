/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSolicitacaoMedicamentoItemSemSaldo extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoSolicitacaoMedicamentoItemSemSaldo(){
		super.mensagem("Item sem saldo", "");
	}
	
}
