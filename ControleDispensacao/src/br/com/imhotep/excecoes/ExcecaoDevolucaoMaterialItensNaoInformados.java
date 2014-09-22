/**
 * Criado por Márlon Assis
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
		super.mensagem("Adicione algum item à sua devolução", "");
	}
	
}
