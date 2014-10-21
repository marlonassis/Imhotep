/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoUnidadeAtual extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoUnidadeAtual(){
		super.mensagem("N‹o foi poss’vel pegar a unidade atual", null);
	}
	
}
