/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoApagarLoteExisteNotaFiscal extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoApagarLoteExisteNotaFiscal(){
		super.mensagem("Não foi possível apagar o lote.", "Existem notas fiscais associadas ao lote.");
	}
	
}
