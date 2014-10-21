/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSolicitacaoEmLock extends ExcecaoPadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8365215825547336740L;

	public ExcecaoSolicitacaoEmLock(){
	}
	
	public ExcecaoSolicitacaoEmLock(String pagina){
		super.mensagem("Solicitação em lock.", null);
	}
	
}
