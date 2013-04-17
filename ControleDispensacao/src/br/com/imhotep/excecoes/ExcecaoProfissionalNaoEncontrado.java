/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoProfissionalNaoEncontrado extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoProfissionalNaoEncontrado(){
		super.mensagem("Não foi possível encontrar o profissional logado.", null);
	}
	
}
