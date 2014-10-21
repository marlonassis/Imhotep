/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoFarmaciaMovimentoLivroDelecaoEstoqueItem extends ExcecaoPadrao {
	private static final long serialVersionUID = -2135852977728271125L;

	public ExcecaoEstornoFarmaciaMovimentoLivroDelecaoEstoqueItem(){
		super.mensagem("N‹o foi poss’vel estornar o movimento", "Erro ao tentar remover o item da nota fiscal");
	}
	
}
