/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDataContabil extends ExcecaoPadrao {
	private static final long serialVersionUID = -476477719195628188L;

	public ExcecaoDataContabil(){
		super.mensagem("A data cont�bil n�o pode ser anterior ao m�s atual", "");
	}
	
}
