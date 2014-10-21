/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoMovimentoLivroAlmoxarifadoNegativo extends ExcecaoPadrao {

	private static final long serialVersionUID = -4383315600375658002L;

	public ExcecaoEstornoMovimentoLivroAlmoxarifadoNegativo(){
		super.mensagem("N‹o foi poss’vel estornar o movimento", "Caso seja apagado este movimento, o estoque ficar‡ negativo");
	}
	
}
