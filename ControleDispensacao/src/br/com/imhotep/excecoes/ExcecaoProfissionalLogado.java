/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoProfissionalLogado extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoProfissionalLogado(){
		super.mensagem("N‹o foi poss’vel pegar o profissional logado", null);
	}
	
}
