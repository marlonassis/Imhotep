/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueBloqueado extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoEstoqueBloqueado(){
		super.mensagem("Este lote est‡ bloqueado", "");
	}
	
}
