/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSolicitacaoNaoFechada extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoSolicitacaoNaoFechada(){
		super.mensagem("Falha ao processo sua solicita��o", "");
	}
	
}
