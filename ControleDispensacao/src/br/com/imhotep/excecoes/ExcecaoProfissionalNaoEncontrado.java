/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoProfissionalNaoEncontrado extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoProfissionalNaoEncontrado(){
		super.mensagem("N‹o foi poss’vel encontrar o profissional logado.", null);
	}
	
}
