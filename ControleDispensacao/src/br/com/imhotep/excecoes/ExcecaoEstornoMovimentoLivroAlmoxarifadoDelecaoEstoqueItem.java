/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoMovimentoLivroAlmoxarifadoDelecaoEstoqueItem extends ExcecaoPadrao {
	private static final long serialVersionUID = 1L;

	public ExcecaoEstornoMovimentoLivroAlmoxarifadoDelecaoEstoqueItem(){
		super.mensagem("N‹o foi poss’vel estornar o movimento", "Erro ao tentar remover o item da nota fiscal");
	}
	
}
