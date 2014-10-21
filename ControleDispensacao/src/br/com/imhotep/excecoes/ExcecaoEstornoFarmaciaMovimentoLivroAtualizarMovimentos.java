/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoFarmaciaMovimentoLivroAtualizarMovimentos extends ExcecaoPadrao {
	private static final long serialVersionUID = 6880172498810577058L;

	public ExcecaoEstornoFarmaciaMovimentoLivroAtualizarMovimentos(){
		super.mensagem("N‹o foi poss’vel reordenar o movimento", "");
	}
	
}
