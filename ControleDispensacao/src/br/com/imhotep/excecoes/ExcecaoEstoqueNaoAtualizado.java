/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueNaoAtualizado extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoEstoqueNaoAtualizado(){
		super.mensagem("Não foi possível atualizar este estoque", "Tente novamente");
	}
	
}
