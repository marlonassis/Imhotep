/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoApagarLoteExisteDoacao extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoApagarLoteExisteDoacao(){
		super.mensagem("N�o foi poss�vel apagar o lote.", "Existem doa��es associadas ao lote.");
	}
	
}
