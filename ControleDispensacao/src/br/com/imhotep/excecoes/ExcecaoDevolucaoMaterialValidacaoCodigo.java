/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDevolucaoMaterialValidacaoCodigo extends ExcecaoPadrao {

	private static final long serialVersionUID = 1L;
	
	public ExcecaoDevolucaoMaterialValidacaoCodigo(){
		super.mensagem("Erro na valida��o", null);
	}
	
}
