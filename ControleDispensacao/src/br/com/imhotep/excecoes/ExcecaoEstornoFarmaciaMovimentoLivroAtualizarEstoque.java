/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoFarmaciaMovimentoLivroAtualizarEstoque extends ExcecaoPadrao {

	private static final long serialVersionUID = 4885072611104845793L;

	public ExcecaoEstornoFarmaciaMovimentoLivroAtualizarEstoque(){
		super.mensagem("N‹o foi poss’vel atualizar o estoque", "");
	}
	
}
