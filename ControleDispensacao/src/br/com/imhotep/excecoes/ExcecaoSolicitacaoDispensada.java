/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSolicitacaoDispensada extends ExcecaoPadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7828175050865969177L;

	public ExcecaoSolicitacaoDispensada(){
	}
	
	public ExcecaoSolicitacaoDispensada(String pagina){
		super.mensagem("Solicita��o em dispensa��o.", null);
	}
	
}
