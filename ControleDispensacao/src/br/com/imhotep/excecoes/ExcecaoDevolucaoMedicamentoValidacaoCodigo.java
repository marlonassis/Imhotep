/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDevolucaoMedicamentoValidacaoCodigo extends ExcecaoPadrao {

	private static final long serialVersionUID = 1L;
	
	public ExcecaoDevolucaoMedicamentoValidacaoCodigo(){
		super.mensagem("Erro na valida��o", null);
	}
	
}
