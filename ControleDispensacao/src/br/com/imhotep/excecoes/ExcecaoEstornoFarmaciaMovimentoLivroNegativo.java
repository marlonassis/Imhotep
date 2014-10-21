/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstornoFarmaciaMovimentoLivroNegativo extends ExcecaoPadrao {

	private static final long serialVersionUID = -5539147516811536512L;

	public ExcecaoEstornoFarmaciaMovimentoLivroNegativo(){
		super.mensagem("N‹o foi poss’vel estornar o movimento", "Caso seja apagado este movimento, o estoque ficar‡ negativo");
	}
	
}
