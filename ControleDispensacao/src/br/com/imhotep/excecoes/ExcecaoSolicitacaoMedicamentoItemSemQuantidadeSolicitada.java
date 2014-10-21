/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSolicitacaoMedicamentoItemSemQuantidadeSolicitada extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoSolicitacaoMedicamentoItemSemQuantidadeSolicitada(){
		super.mensagem("ƒ necess‡rio informar a quantidade", "");
	}
	
}
