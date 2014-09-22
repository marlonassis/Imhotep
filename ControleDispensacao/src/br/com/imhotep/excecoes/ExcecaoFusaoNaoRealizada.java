/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoFusaoNaoRealizada extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoFusaoNaoRealizada(){
		super.mensagem("N‹o foi poss’vel realizar a fus‹o", "Tente novamente");
	}
	
}
