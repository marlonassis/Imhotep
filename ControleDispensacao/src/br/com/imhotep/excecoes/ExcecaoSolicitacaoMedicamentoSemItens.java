/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoSolicitacaoMedicamentoSemItens extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoSolicitacaoMedicamentoSemItens(){
		super.mensagem("� necess�rio adicionar itens para fechar a solicita��o", "");
	}
	
}
