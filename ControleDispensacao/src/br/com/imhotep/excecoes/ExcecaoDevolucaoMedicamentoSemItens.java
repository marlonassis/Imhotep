/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDevolucaoMedicamentoSemItens extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoDevolucaoMedicamentoSemItens(){
		super.mensagem("� necess�rio que voc� recuse ou adicione lotes aos itens", "");
	}
	
}
