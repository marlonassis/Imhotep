/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueRepetidoNotaFiscal extends ExcecaoPadrao {
	private static final long serialVersionUID = 4121469474851472764L;

	public ExcecaoEstoqueRepetidoNotaFiscal(){
		super.mensagem("Este item j‡ foi adicionado nesta nota fiscal", "");
	}
	
}
