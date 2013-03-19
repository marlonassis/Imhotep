/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoApagarLoteExisteMovimentoSaida extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoApagarLoteExisteMovimentoSaida(){
		super.mensagem("Não foi possível apagar o lote.", "Existe(m) movimento(s) de saída associado(s) ao lote.");
	}
	
}
