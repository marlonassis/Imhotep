/**
 * Criado por Márlon Assis
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
		super.mensagem("É necessário que você recuse ou adicione lotes aos itens", "");
	}
	
}
