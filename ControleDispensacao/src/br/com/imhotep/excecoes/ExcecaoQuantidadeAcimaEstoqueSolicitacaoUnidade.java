/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoQuantidadeAcimaEstoqueSolicitacaoUnidade extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoQuantidadeAcimaEstoqueSolicitacaoUnidade(){
		super.mensagem("N‹o Ž poss’vel dispensar uma quantidade acima do total do lote", null);
	}
	
}
