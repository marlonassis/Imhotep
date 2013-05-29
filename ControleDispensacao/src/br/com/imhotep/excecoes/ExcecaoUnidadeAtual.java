/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoUnidadeAtual extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoUnidadeAtual(){
		super.mensagem("Não foi possível pegar a unidade atual", null);
	}
	
}
