/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueUnLock extends ExcecaoPadrao {
	private static final long serialVersionUID = -1003870404724407844L;

	public ExcecaoEstoqueUnLock(){
		super.mensagem("Não foi possível retirar o lote de unlock", null);
	}
	
}
