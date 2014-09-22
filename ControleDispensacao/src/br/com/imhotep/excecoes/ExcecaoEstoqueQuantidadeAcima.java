/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueQuantidadeAcima extends ExcecaoPadrao {

	private static final long serialVersionUID = 1L;

	public ExcecaoEstoqueQuantidadeAcima(){
		super.mensagem("Quantidade acima do m‡ximo", null);
	}
	
}
