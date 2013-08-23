/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoReservaVazia extends ExcecaoPadrao {

	private static final long serialVersionUID = 1L;
	
	public ExcecaoReservaVazia(){
		super.mensagem("Não é possível reservar quantidade 0", "Informe a quantidade");
	}
	
}
