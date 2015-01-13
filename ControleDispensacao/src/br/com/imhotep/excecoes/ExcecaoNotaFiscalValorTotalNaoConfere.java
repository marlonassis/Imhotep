/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoNotaFiscalValorTotalNaoConfere extends ExcecaoPadrao {
	private static final long serialVersionUID = 9127254721750805957L;

	public ExcecaoNotaFiscalValorTotalNaoConfere(){
		super.mensagem("O valor total da Nota-Fiscal não confere com o valor total dos itens", null);
	}
	
}
