/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSemJustificativa extends ExcecaoPadrao {

	private static final long serialVersionUID = 1L;

	public ExcecaoSemJustificativa(){
		super.mensagem("Informe o motivo da recusa", null);
	}
	
}
