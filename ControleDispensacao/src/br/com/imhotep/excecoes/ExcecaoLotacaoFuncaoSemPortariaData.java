/**
 * Criado por M�rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoLotacaoFuncaoSemPortariaData extends ExcecaoPadrao {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcecaoLotacaoFuncaoSemPortariaData(){
		super.mensagem("Informe a data de posse e a portaria", "");
	}
	
}
