/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDevolucaoMaterialSemItens extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoDevolucaoMaterialSemItens(){
		super.mensagem("� necess�rio que voc� recuse ou adicione lotes aos itens", "");
	}
	
}
