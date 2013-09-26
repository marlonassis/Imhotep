/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoItemNotaFiscalNaoCadastrada extends ExcecaoPadrao {
	private static final long serialVersionUID = -1541648225416009243L;

	public ExcecaoItemNotaFiscalNaoCadastrada(){
		super.mensagem("Não foi possível cadastrar este item", null);
	}
	
}
