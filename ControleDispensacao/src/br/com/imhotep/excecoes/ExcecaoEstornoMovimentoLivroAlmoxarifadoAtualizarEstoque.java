/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarEstoque extends ExcecaoPadrao {

	private static final long serialVersionUID = 3407273854044613169L;

	public ExcecaoEstornoMovimentoLivroAlmoxarifadoAtualizarEstoque(){
		super.mensagem("N‹o foi poss’vel atualizar o estoque", "");
	}
	
}
