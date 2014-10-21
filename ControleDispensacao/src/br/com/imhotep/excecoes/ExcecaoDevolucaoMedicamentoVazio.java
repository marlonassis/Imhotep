/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDevolucaoMedicamentoVazio extends ExcecaoPadrao {

	private static final long serialVersionUID = 1L;
	
	public ExcecaoDevolucaoMedicamentoVazio(){
		super.mensagem("Informe a quantidade", null);
	}
	
}
