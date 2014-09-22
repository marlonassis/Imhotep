/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoItemNotaFiscalNaoCadastrada extends ExcecaoPadrao {
	private static final long serialVersionUID = -1541648225416009243L;

	public ExcecaoItemNotaFiscalNaoCadastrada(){
		super.mensagem("N‹o foi poss’vel cadastrar este item", null);
	}
	
}
