/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoControlePrescricaoItemDose extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoControlePrescricaoItemDose(){
		super.mensagem("Ocorreu um erro ao tentar salvara a dose.", "");
	}
	
}
