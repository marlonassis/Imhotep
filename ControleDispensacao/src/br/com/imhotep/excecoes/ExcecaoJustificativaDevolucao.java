/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoJustificativaDevolucao extends ExcecaoPadrao {

	private static final long serialVersionUID = 1L;
	
	public ExcecaoJustificativaDevolucao(){
		super.mensagem("Informe a justificativa", "");
	}
	
}
