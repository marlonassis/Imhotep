/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoDevolucaoMaterialValidacaoCodigo extends ExcecaoPadrao {

	private static final long serialVersionUID = 1L;
	
	public ExcecaoDevolucaoMaterialValidacaoCodigo(){
		super.mensagem("Erro na validação", null);
	}
	
}
