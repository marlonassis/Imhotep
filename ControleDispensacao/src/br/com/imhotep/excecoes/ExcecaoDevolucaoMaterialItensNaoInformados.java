/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDevolucaoMaterialItensNaoInformados extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoDevolucaoMaterialItensNaoInformados(){
		super.mensagem("Adicione algum item � sua devolu��o", "");
	}
	
}
