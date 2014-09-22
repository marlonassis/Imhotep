/**
 * Criado por M‡rlon Assis
 */
package br.com.imhotep.excecoes;


/**
 * @author marlonassis
 *
 */
public class ExcecaoEstoqueNaoCadastrado extends ExcecaoPadrao {

	private static final long serialVersionUID = 1783207019548721738L;
	
	public ExcecaoEstoqueNaoCadastrado(){
		super.mensagem("N‹o foi poss’vel cadastrar este estoque", "Tente novamente ou verifique se ele n‹o est‡ cadastrado");
	}
	
}
