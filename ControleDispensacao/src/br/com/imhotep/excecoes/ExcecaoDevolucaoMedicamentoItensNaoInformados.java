/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDevolucaoMedicamentoItensNaoInformados extends ExcecaoPadrao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoDevolucaoMedicamentoItensNaoInformados(){
		super.mensagem("Adicione algum item � sua devolu��o", "");
	}
	
}
