/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoApagarLoteExisteDoacao extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoApagarLoteExisteDoacao(){
		super.mensagem("Não foi possível apagar o lote.", "Existem doações associadas ao lote.");
	}
	
}
