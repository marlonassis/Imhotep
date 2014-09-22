/**
 * Criado por Márlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoControlePrescricaoItem extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoControlePrescricaoItem(){
		super.mensagem("Ocorreu um erro ao tentar salvar o item da prescrição.", "");
	}
	
}
