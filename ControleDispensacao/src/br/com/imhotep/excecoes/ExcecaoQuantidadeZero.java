/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoQuantidadeZero extends ExcecaoPadrao {

	private static final long serialVersionUID = -3756857984944489496L;

	public ExcecaoQuantidadeZero(){
		super.mensagem("Informe a quantidade", "");
	}
	
}
