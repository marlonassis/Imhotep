/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos extends ExcecaoPadrao {
	private static final long serialVersionUID = 5257983742915090342L;

	public ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarMovimentos(){
		super.mensagem("N‹o foi poss’vel reordenar o movimento", "");
	}
	
}
